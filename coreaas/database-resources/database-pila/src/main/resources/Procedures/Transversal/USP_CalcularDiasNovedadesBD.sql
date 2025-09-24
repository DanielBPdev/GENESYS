-- =============================================
-- Author:	  	Alfonso Baquero Echeverry
-- Create date: 2018-09-04
-- Description: Procedimiento almacenado encargado de determinar la cantidad de días de novedad
-- por período en BD
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_CalcularDiasNovedadesBD]
	@IdTransaccion BIGINT
AS
BEGIN
SET NOCOUNT ON;

	--print 'Inicia USP_CalcularDiasNovedadesBD'

	-- se limpian los cálculos anteriores con el mismo número de transacción
	DELETE FROM staging.DiasNovedadCore WHERE dncTransaccion = @IdTransaccion

	DECLARE @novId BIGINT,
		@tipoIdentificacionCotizante Varchar(20),
		@numeroIdentificacionCotizante Varchar(16),
		@fechaIniNov DATE,
		@fechaFinNov DATE,
		@periodoIni VARCHAR(7),
		@periodoFin VARCHAR(7)

	DECLARE @cursorFechasNovedad AS CURSOR

	DECLARE @DiasPeriodo AS TABLE (Dia DATE, TieneNovedad BIT)
	DECLARE @Dia DATE, 
		@periodoAnterior VARCHAR(7) = '',
		@totalDiasMes SMALLINT = 0

		SELECT novId, novTipoIdentificacion, novNumeroIdentificacion, novFechaInicio, novFechaFin, novPeriodoInicio, novPeriodoFin
		INTO #NovedadTMP
		FROM staging.Novedad with (nolock)
		WHERE novTransaccion = @IdTransaccion
		
	SET @cursorFechasNovedad = CURSOR FAST_FORWARD FOR
	SELECT novId, novTipoIdentificacion, novNumeroIdentificacion, novFechaInicio, novFechaFin, novPeriodoInicio, novPeriodoFin
	FROM #NovedadTMP
		

	OPEN @cursorFechasNovedad

	FETCH NEXT FROM @cursorFechasNovedad 
	INTO @novId, @tipoIdentificacionCotizante, @numeroIdentificacionCotizante, @fechaIniNov, @fechaFinNov, @periodoIni, @periodoFin

	WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @Dia = CONVERT(DATE, @periodoIni + '-01', 120)

		WHILE @Dia BETWEEN @fechaIniNov AND @fechaFinNov
		BEGIN
			INSERT INTO @DiasPeriodo VALUES (@Dia, CASE WHEN @Dia BETWEEN @fechaIniNov AND @fechaFinNov THEN 1 ELSE 0 END)

			SET @Dia = DATEADD(DAY, 1, @Dia)
		END
	
		INSERT INTO staging.DiasNovedadCore (dncIdNovedad, dncTipoIdCotizante, dncNumeroIdCotizante, dncPeriodo, dncDiasNovedad, dncTransaccion)
		SELECT @novId dncIdNovedad, @tipoIdentificacionCotizante dncTipoIdCotizante, @numeroIdentificacionCotizante dncNumeroIdCotizante, 
			CONVERT(VARCHAR(7), dia, 120) dncPeriodo, COUNT(*) dncDiasNovedad, @IdTransaccion dncTransaccion
		FROM @DiasPeriodo 
		WHERE TieneNovedad = 1 
		GROUP BY CONVERT(VARCHAR(7), dia, 120)

		DELETE FROM @DiasPeriodo

		FETCH NEXT FROM @cursorFechasNovedad 
		INTO @novId, @tipoIdentificacionCotizante, @numeroIdentificacionCotizante, @fechaIniNov, @fechaFinNov, @periodoIni, @periodoFin
	END
	
	CLOSE @cursorFechasNovedad;
	DEALLOCATE @cursorFechasNovedad;
	DROP TABLE #NovedadTMP;

	-- se actualiza el la cantidad de días para mes completo (caso meses de 31 y 28 días)

	UPDATE staging.DiasNovedadCore
	SET dncDiasNovedad = CASE 
			WHEN dncDiasNovedad = DAY(EOMONTH(CONVERT(DATE, dncPeriodo + '-01', 120))) 
				AND dncDiasNovedad != 30 
			THEN 30 ELSE dncDiasNovedad END
	where dncTransaccion = @IdTransaccion;

	--print 'Finaliza USP_CalcularDiasNovedadesBD'
END;