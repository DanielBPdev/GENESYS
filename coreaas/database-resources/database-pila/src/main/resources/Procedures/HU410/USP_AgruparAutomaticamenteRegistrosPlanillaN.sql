-- =============================================
-- Author:		Juan Diego Ocampo
-- Create date: 2019-05-28
-- Description:	Procedimiento almacenado encargado de la organización
-- automática de registros de planilla de corrección sin original
-- =============================================
CREATE PROCEDURE USP_AgruparAutomaticamenteRegistrosPlanillaN
	@iIdIndicePlanilla BIGINT
AS
BEGIN
SET NOCOUNT ON;
DECLARE @ErrorMessage NVARCHAR(4000);
DECLARE @ErrorSeverity INT;
DECLARE @ErrorState INT;

BEGIN TRY

	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	
	CREATE TABLE #RegistroDetallado (
		[redId] [bigint] NULL,
		[redTipoIdentificacionCotizante] [varchar](20)  NULL,
		[redNumeroIdentificacionCotizante] [varchar](16) NULL,
		[redCorrecciones] [varchar](1) NULL,
		[redOUTGrupoAC] [int] NULL,
		[redValorIBC] [numeric](19, 5) NULL,
		[redAporteObligatorio] [numeric](19, 5) NULL,
		[redOUTValorMoraCotizante] [numeric](19, 5) NULL,
		INDEX IX_redId (redId)
	)

	CREATE TABLE #RegistroDetalladoC(
		[redId] [bigint] NULL
		INDEX IX_redId (redId)
	)

	INSERT INTO #RegistroDetallado (
		redId, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, redCorrecciones, redAporteObligatorio, redOUTValorMoraCotizante, redValorIBC
	)
	SELECT
		redId, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, redCorrecciones, redAporteObligatorio, redOUTValorMoraCotizante, redValorIBC 
	FROM staging.RegistroDetallado red WITH(NOLOCK), staging.RegistroGeneral reg WITH(NOLOCK)
	WHERE redRegistroGeneral = regId 
	AND regRegistroControl = @iIdIndicePlanilla


	DECLARE @numA INTEGER
	DECLARE @redId BIGINT
	DECLARE @totalA INTEGER
	DECLARE @totalC INTEGER
	DECLARE @redTipoIdentificacionCotizante VARCHAR(20)
	DECLARE @redNumeroIdentificacionCotizante VARCHAR(16)

	DECLARE @registrosACursor AS CURSOR
	SET @registrosACursor = CURSOR FAST_FORWARD FOR
	SELECT 
		a.num, a.redId, b.totalA, c.totalC, a.redTipoIdentificacionCotizante, a.redNumeroIdentificacionCotizante
	FROM (
		SELECT 
			ROW_NUMBER() OVER(ORDER BY redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, redAporteObligatorio, redOUTValorMoraCotizante, redValorIBC ASC) num, 
			redId, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante 
		FROM #RegistroDetallado WITH(NOLOCK)
		WHERE redCorrecciones = 'A'
	) AS a
	INNER JOIN (
		SELECT 
			COUNT(redId) totalA,redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
		FROM #RegistroDetallado WITH(NOLOCK)
		WHERE redCorrecciones = 'A'
		GROUP BY redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
	) AS b on a.redTipoIdentificacionCotizante = b.redTipoIdentificacionCotizante AND a.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante
	INNER JOIN (
		SELECT 
			COUNT(redId) totalC,redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
		FROM #RegistroDetallado WITH(NOLOCK)
		WHERE redCorrecciones = 'C'
		GROUP BY redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
	) AS c on a.redTipoIdentificacionCotizante = c.redTipoIdentificacionCotizante AND a.redNumeroIdentificacionCotizante = c.redNumeroIdentificacionCotizante

	OPEN @registrosACursor	
	FETCH NEXT FROM @registrosACursor INTO @numA, @redId, @totalA, @totalC, @redTipoIdentificacionCotizante, @redNumeroIdentificacionCotizante
	WHILE @@FETCH_STATUS = 0
		BEGIN		
			
			UPDATE red 
				SET red.redOUTGrupoAC = @numA
			FROM #RegistroDetallado red WITH(NOLOCK)
			WHERE red.redId = @redId
		
			INSERT INTO #RegistroDetalladoC (redId)
			SELECT TOP (@totalC/@totalA) redId
			FROM #RegistroDetallado WITH(NOLOCK)
			WHERE redCorrecciones = 'C'
			AND redOUTGrupoAC IS NULL
			AND redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante
			AND redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante
			ORDER BY redAporteObligatorio, redOUTValorMoraCotizante, redValorIBC ASC
		
			UPDATE reg 
				SET reg.redOUTGrupoAC = @numA
			FROM #RegistroDetallado reg WITH(NOLOCK)
			INNER JOIN #RegistroDetalladoC regC WITH(NOLOCK) ON reg.redId = regC.redId

			DELETE FROM #RegistroDetalladoC
		
			FETCH NEXT FROM @registrosACursor INTO @numA, @redId, @totalA, @totalC, @redTipoIdentificacionCotizante, @redNumeroIdentificacionCotizante
		END

	CLOSE @registrosACursor
	DEALLOCATE @registrosACursor

	UPDATE red
		SET red.redOUTGrupoAC = numA
	FROM #RegistroDetallado red WITH(NOLOCK)
	INNER JOIN (
		SELECT redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, MAX(redOUTGrupoAC) AS numA
		FROM #RegistroDetallado 
		GROUP BY redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
	) AS a ON red.redTipoIdentificacionCotizante = a.redTipoIdentificacionCotizante AND red.redNumeroIdentificacionCotizante = a.redNumeroIdentificacionCotizante
	WHERE red.redCorrecciones = 'C'
	AND red.redOUTGrupoAC IS NULL

	UPDATE red
		SET 
		redDateTimeUpdate = @redDateTimeUpdate,
		red.redOUTGrupoAC = a.redOUTGrupoAC
	FROM staging.RegistroDetallado red WITH(NOLOCK)
	INNER JOIN #RegistroDetallado a WITH(NOLOCK) ON red.redId = a.redId
END TRY
BEGIN CATCH

    SELECT @ErrorMessage = '[dbo].[USP_AgruparAutomaticamenteRegistrosPlanillaN]|@iIdIndicePlanilla=' + CAST(ISNULL(@iIdIndicePlanilla,0) AS VARCHAR(20)) + '|' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

    RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );  
    
END CATCH;
END;