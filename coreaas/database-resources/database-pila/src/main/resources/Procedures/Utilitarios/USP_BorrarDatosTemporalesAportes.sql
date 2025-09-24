-- =============================================
-- Author:		Juan Diego Ocampo Q
-- Create date: 2020-05-05
-- Description:	borra los temporales dado un registro general  
-- =============================================
create PROCEDURE [dbo].[USP_BorrarDatosTemporalesAportes]
	@idRegistroGeneral Bigint
AS
BEGIN
SET NOCOUNT ON;	
BEGIN TRY
BEGIN TRAN T2	
	DECLARE @localDate DATETIME = dbo.getLocalDate()
	
	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.getLocalDate() 
		,'@idRegistroGeneral=' + CAST(@idRegistroGeneral AS VARCHAR(20)) 
		,'Inicio USP_BorrarDatosTemporalesAportes');
	
	
	
	
	DECLARE @tieneNovedad BIT = 0;
	IF EXISTS (SELECT TOP 1 1 
				FROM staging.RegistroDetalladoNovedad 
				JOIN staging.RegistroDetallado ON redId = rdnRegistroDetallado 
				WHERE redRegistroGeneral = @idRegistroGeneral)
	BEGIN
		SET @tieneNovedad = 1
	END 

	DECLARE @countRed bigint;
	
	SELECT @countRed = COUNT(1) FROM staging.RegistroDetallado WHERE redRegistroGeneral = @idRegistroGeneral;
	
	INSERT INTO TemAporteProcesado (
		tprAporteGeneral, --regGeneral
		tprAporte, -- cantidad aportess 
		tprNovedadesProcesadas, -- 0
		tprPresentaNovedades -- staging.RegistroDetalladoNovedad
	)
	VALUES (
		@idRegistroGeneral,
		@countRed,
		0,
		@tieneNovedad
	) 


	/*DECLARE @IdsRegistroDetallados AS TABLE (id BIGINT)	

	INSERT INTO @IdsRegistroDetallados
	SELECT tem.temIdTransaccion
    FROM TemAporte tem
    INNER JOIN core.AporteDetallado apd ON tem.temIdTransaccion = apd.apdRegistroDetallado 
    WHERE tem.temRegistroGeneral = @idRegistroGeneral
    ORDER BY tem.temIdTransaccion ASC
    */

    CREATE TABLE #IdsRegistroDetallados (id BIGINT, s1 varchar(200))	
	INSERT INTO #IdsRegistroDetallados (id,s1)
	EXEC sp_execute_remote CoreReferenceData, 
	N'CREATE TABLE #TemAporte (temIdTransaccion bigint, s1 varchar(200))

	INSERT INTO #TemAporte (temIdTransaccion, s1)
	EXEC sp_execute_remote PilaReferenceData, 
	N''SELECT tem.temIdTransaccion
	FROM TemAporte tem
	WHERE tem.temRegistroGeneral = @idRegistroGeneral'',
	N''@idRegistroGeneral BIGINT'',@idRegistroGeneral = @idRegistroGeneral

	SELECT tem.temIdTransaccion
	FROM #TemAporte tem
	INNER JOIN AporteDetallado apd ON tem.temIdTransaccion = apd.apdRegistroDetallado 
	UNION 
	SELECT tem.temIdTransaccion
	FROM #TemAporte tem
	INNER JOIN AporteDetallado apd ON tem.temIdTransaccion = apd.apdRegistroDetalladoUltimo
	ORDER BY tem.temIdTransaccion ASC',
	N'@idRegistroGeneral BIGINT',@idRegistroGeneral = @idRegistroGeneral

	CREATE CLUSTERED INDEX IX_IdsRegistroDetallados ON #IdsRegistroDetallados (id);
	
    DELETE tem
    FROM TemAporte tem
    INNER JOIN #IdsRegistroDetallados ids ON ids.id = tem.temIdTransaccion

	DELETE tem
	FROM TemAportante tem
	INNER JOIN #IdsRegistroDetallados ids ON ids.id = tem.tapIdTransaccion
	
	DELETE tem
	FROM TemCotizante tem
	INNER JOIN #IdsRegistroDetallados ids ON ids.id = tem.tctIdTransaccion
	
	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.getLocalDate() 
		,'USP_BorrarDatosTemporalesAportes | @idRegistroGeneral=' + CAST(@idRegistroGeneral AS VARCHAR(20)) 
		,'Finaliza USP_BorrarDatosTemporalesAportes');

COMMIT TRAN T2;
END TRY
BEGIN CATCH

	ROLLBACK TRAN T2;

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.getLocalDate()
		,'USP_BorrarDatosTemporalesAportes | @idRegistroGeneral=' + CAST(@idRegistroGeneral AS VARCHAR(20)) 
		,ERROR_MESSAGE());
	
	THROW
END CATCH;
END;