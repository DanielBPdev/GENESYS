-- =============================================
-- Author:		Alfonso Baquero E.
-- Create date: 2017-12-11
-- Description:	Procedimiento almacenado que valida el estado de entradas previas de registros
-- tipo 6 de un archivo de Log Financiero duplicado
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU407Registro6Duplicado] 
	@iIdIndicePlanilla BIGINT
AS

BEGIN
SET NOCOUNT ON;
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	DECLARE @iRegistro6Actual BIGINT
	DECLARE @sIdAportante VARCHAR(16)
	DECLARE @sNumeroPlanilla VARCHAR(15)
	DECLARE @sPeriodoPago VARCHAR(6)
	DECLARE @sCodOperadorInfo VARCHAR(2)

    SELECT pf6.pf6IdAportante, pf6.pf6NumeroPlanilla, pf6.pf6PeriodoPago, pf6.pf6Id, pf6.pf6CodOperadorInformacion
    INTO #PilaArchivoFRegistro6TMP
    FROM dbo.PilaArchivoFRegistro6 pf6 WITH(NOLOCK)
    WHERE pf6.pf6IndicePlanillaOF = @iIdIndicePlanilla
    
	DECLARE @cursorRegistro6Actual CURSOR

	SET @cursorRegistro6Actual = CURSOR FAST_FORWARD FOR
	SELECT pf6IdAportante, pf6NumeroPlanilla, pf6PeriodoPago, pf6Id, pf6CodOperadorInformacion
	FROM #PilaArchivoFRegistro6TMP WITH(NOLOCK)

	OPEN @cursorRegistro6Actual

	FETCH NEXT FROM @cursorRegistro6Actual INTO @sIdAportante, @sNumeroPlanilla, @sPeriodoPago, @iRegistro6Actual, @sCodOperadorInfo

	WHILE @@fetch_status = 0 
	BEGIN
		print 'planilla ' + @sNumeroPlanilla
		IF EXISTS (
			SELECT TOP 1 pf6.pf6Id, pf6.pf6IdAportante 
			FROM dbo.PilaArchivoFRegistro6 pf6 WITH(NOLOCK)
				INNER JOIN dbo.PilaIndicePlanillaOF pio WITH(NOLOCK) ON (pf6.pf6IndicePlanillaOF = pio.pioId)
			WHERE pf6.pf6IndicePlanillaOF <> @iIdIndicePlanilla
				AND (
					ISNUMERIC(pf6.pf6IdAportante) = 1 
					AND CAST(pf6.pf6IdAportante AS BIGINT) = CASE WHEN ISNUMERIC(@sIdAportante) = 1 THEN CAST(@sIdAportante AS BIGINT) ELSE 0 END
					OR ISNUMERIC(pf6.pf6IdAportante) = 0 AND pf6.pf6IdAportante = @sIdAportante
				)
				AND pf6.pf6CodOperadorInformacion = @sCodOperadorInfo
				AND pf6.pf6NumeroPlanilla = @sNumeroPlanilla
				AND pf6.pf6PeriodoPago = @sPeriodoPago
				AND pf6.pf6EstadoConciliacion = 'REGISTRO_6_CONCILIADO'
				)
		BEGIN
			print 'se anula el registro actual'
			UPDATE pf6
			SET pf6.pf6EstadoConciliacion = 'REGISTRO_6_ANULADO'
			FROM dbo.PilaArchivoFRegistro6 pf6 WITH(NOLOCK)
			WHERE pf6.pf6Id = @iRegistro6Actual

			-- se actualiza el estado del archivo actual (sí aplica)
			UPDATE pio
			SET pio.pioEstadoArchivo = 
				(
					CASE 
						WHEN EXISTS (
							SELECT TOP 1 pf6.pf6Id 
							FROM dbo.PilaArchivoFRegistro6 pf6 WITH(NOLOCK)
							WHERE pf6.pf6IndicePlanillaOF = pio.pioId 
							AND pf6.pf6EstadoConciliacion NOT IN ('REGISTRO_6_CONCILIADO', 'REGISTRO_6_ANULADO'))
						THEN 'ARCHIVO_FINANCIERO_PENDIENTE_CONCILIACION'
						ELSE 'ARCHIVO_FINANCIERO_CONCILIADO'
					END
				)
			FROM dbo.PilaIndicePlanillaOF pio WITH(NOLOCK)
			WHERE pio.pioId = @iIdIndicePlanilla
		END
		ELSE
		BEGIN
			print 'se anulan los registros anteriores'
			UPDATE pf6
			SET pf6.pf6EstadoConciliacion = 'REGISTRO_6_ANULADO'
			FROM dbo.PilaArchivoFRegistro6 pf6 WITH(NOLOCK) 
				INNER JOIN dbo.PilaIndicePlanillaOF pio WITH(NOLOCK) ON (pf6.pf6IndicePlanillaOF = pio.pioId)
			WHERE pf6.pf6IndicePlanillaOF <> @iIdIndicePlanilla
				AND (
					ISNUMERIC(pf6.pf6IdAportante) = 1 
					AND CAST(pf6.pf6IdAportante AS BIGINT) = CASE WHEN ISNUMERIC(@sIdAportante) = 1 THEN CAST(@sIdAportante AS BIGINT) ELSE 0 END
					OR ISNUMERIC(pf6.pf6IdAportante) = 0 AND pf6.pf6IdAportante = @sIdAportante
				)
				AND pf6.pf6CodOperadorInformacion = @sCodOperadorInfo
				AND pf6.pf6NumeroPlanilla = @sNumeroPlanilla
				AND pf6.pf6PeriodoPago = @sPeriodoPago

			-- se actualiza el estado de los archivoa afectados (donde aplique)
			UPDATE pio
			SET pio.pioEstadoArchivo = 
				(
					CASE 
						WHEN EXISTS (
							SELECT TOP 1 pf6.pf6Id 
							FROM dbo.PilaArchivoFRegistro6 pf6 WITH(NOLOCK)
							WHERE pf6.pf6IndicePlanillaOF = pio.pioId 
							AND pf6.pf6EstadoConciliacion NOT IN ('REGISTRO_6_CONCILIADO', 'REGISTRO_6_ANULADO'))
						THEN 'ARCHIVO_FINANCIERO_PENDIENTE_CONCILIACION'
						ELSE 'ARCHIVO_FINANCIERO_CONCILIADO'
					END
				)
			FROM dbo.PilaIndicePlanillaOF pio WITH(NOLOCK)
			WHERE pio.pioId IN (
				SELECT pio2.pioId
				FROM dbo.PilaArchivoFRegistro6 pf6 WITH(NOLOCK) 
					INNER JOIN dbo.PilaIndicePlanillaOF pio2 WITH(NOLOCK) ON (pf6.pf6IndicePlanillaOF = pio2.pioId)
				WHERE pio2.pioId <> @iIdIndicePlanilla
					AND (
						ISNUMERIC(pf6.pf6IdAportante) = 1 
						AND CAST(pf6.pf6IdAportante AS BIGINT) = CASE WHEN ISNUMERIC(@sIdAportante) = 1 THEN CAST(@sIdAportante AS BIGINT) ELSE 0 END
						OR ISNUMERIC(pf6.pf6IdAportante) = 0 AND pf6.pf6IdAportante = @sIdAportante
					)
					AND pf6.pf6CodOperadorInformacion = @sCodOperadorInfo
					AND pf6.pf6NumeroPlanilla = @sNumeroPlanilla
					AND pf6.pf6PeriodoPago = @sPeriodoPago
			)
		END

		FETCH NEXT FROM @cursorRegistro6Actual INTO @sIdAportante, @sNumeroPlanilla, @sPeriodoPago, @iRegistro6Actual, @sCodOperadorInfo
	END
	CLOSE @cursorRegistro6Actual;
	DEALLOCATE @cursorRegistro6Actual;
	
	DROP TABLE #PilaArchivoFRegistro6TMP
END