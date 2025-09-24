-- =============================================
-- Author:		Mauricio Sanchez Castro
-- Create date: 2017/05/27
-- Description:	Procedimiento almacenado encargado de calcular el estado V3 cuando BD es igual a normal,1429 o 590. 
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU396V3BDNormalBD1429BD590_T1T2T3] 
	@IdRegistroGeneral Bigint,
	@IdTransaccion bigint,
	@EsSimulado Bit,
	@idRegistro Bigint,
	@tipoIdentificacionCotizante Varchar(20),
	@numeroIdentificacionCotizante Varchar(16),
	@diasCotizados Smallint,
	@diasNovedades Smallint,
	@periodoAporte Varchar(7),
	@claseTrabajador Varchar(50),
	@tipoIdentificacionAportante Varchar(20),
	@numeroIdentificacionAportante Varchar(16),
	@EstadoValidacionNovedadesPILA Varchar(50),
	@estadoValidacionV3 Varchar(50) OUTPUT
AS

BEGIN
SET NOCOUNT ON;
	--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate();
	
	----print '@EstadoValidacionNovedadesPILA: ' + @EstadoValidacionNovedadesPILA
	----print '@diasCotizados: ' + CAST(@diasCotizados AS VARCHAR(20))

	IF(@diasCotizados = 0)
	BEGIN
		SET @estadoValidacionV3 = @EstadoValidacionNovedadesPILA;
	END
	ELSE 
	BEGIN

		IF @diasCotizados < 30
		BEGIN
			IF @claseTrabajador = 'REGULAR' OR @claseTrabajador = 'MADRE_COMUNITARIA' OR @claseTrabajador = 'SERVICIO_DOMESTICO'
			BEGIN
	
				DECLARE @totalDiasCotizados Smallint = 0
										
				SET @totalDiasCotizados = @diasCotizados + @diasNovedades
										
				IF(@totalDiasCotizados >= 30)
				BEGIN
					SET @estadoValidacionV3 = 'OK'
				END
				ELSE
				BEGIN
					SET @estadoValidacionV3 = 'NO_OK'
				END
			END
			ELSE IF @claseTrabajador = 'TRABAJADOR_POR_DIAS'
			BEGIN
				SET @estadoValidacionV3 = 'OK'
			END
			ELSE
			BEGIN
				SET @estadoValidacionV3 = 'NO_OK'
			END
		END
		ELSE
		BEGIN
			SET @estadoValidacionV3 = 'OK'
		END
	END

	
	
	--DECLARE @RegistroDetalladoTMP AS TABLE (
	CREATE TABLE #RegistroDetalladoTMP (
		redId BIGINT,
		redTipoIdentificacionCotizante VARCHAR(20), 
		redNumeroIdentificacionCotizante VARCHAR(16)
	)
	CREATE INDEX ix_redId ON #RegistroDetalladoTMP(redId);
	
	INSERT INTO #RegistroDetalladoTMP (redId)
	SELECT red.redId
	FROM staging.RegistroDetallado red WITH (NOLOCK)
	INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON red.redRegistroGeneral = reg.regId
	WHERE redRegistroGeneral = @IdRegistroGeneral
	AND redTipoIdentificacionCotizante = @tipoIdentificacionCotizante
	AND redNumeroIdentificacionCotizante = @numeroIdentificacionCotizante
	ORDER BY red.redId;
	
	
	DECLARE @AnalisisIntegral BIT = 0;
	SELECT @AnalisisIntegral = CASE WHEN @estadoValidacionV3 = 'OK' THEN 1 ELSE 0 END;
	
	UPDATE red 
	SET
		red.redDateTimeUpdate = @redDateTimeUpdate
		,red.redOUTEstadoValidacionV3 = @estadoValidacionV3
		,red.redOUTAnalisisIntegral = @AnalisisIntegral
	FROM #RegistroDetalladoTMP tmp WITH (NOLOCK)
	INNER JOIN staging.RegistroDetallado red WITH (NOLOCK) ON tmp.redId = red.redId;
	--ORDER BY red.redId
	
	DELETE FROM #RegistroDetalladoTMP;

	--print '@estadoValidacionV3'
	--print @estadoValidacionV3

	IF @estadoValidacionV3 = 'OK'
	BEGIN
		
		IF @EsSimulado = 0
		BEGIN
		BEGIN TRAN
			INSERT INTO staging.RegistroAfectacionAnalisisIntegral (raiRegistroGeneral, raiTipoIdentificacionCotizante, raiNumeroIdentificacionCotizante, raiRegistroDetalladoAfectado)
			SELECT @IdRegistroGeneral, red.redTipoIdentificacionCotizante, red.redNumeroIdentificacionCotizante, redId
			FROM staging.RegistroDetallado red WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON red.redRegistroGeneral = reg.regId
			WHERE regId <> @IdRegistroGeneral
			AND reg.regPeriodoAporte = @periodoAporte
			AND reg.regTipoIdentificacionAportante = @tipoIdentificacionAportante 
			AND reg.regNumeroIdentificacionAportante = @numeroIdentificacionAportante
			AND red.redTipoIdentificacionCotizante = @tipoIdentificacionCotizante
			AND red.redNumeroIdentificacionCotizante = @numeroIdentificacionCotizante
			AND redOUTEstadoValidacionV0 = 'CUMPLE' AND redOUTEstadoValidacionV2 IN ('OK', 'NO_APLICA')
			AND redOUTEstadoValidacionV3 = 'NO_OK'
			AND ISNULL(red.redCorrecciones,'') NOT IN ('A','C')
			AND NOT EXISTS (
				SELECT TOP 1 1 FROM staging.RegistroAfectacionAnalisisIntegral WITH (NOLOCK) WHERE raiRegistroGeneral = @IdRegistroGeneral AND raiRegistroDetalladoAfectado = red.redId
			);
		COMMIT;
		END
		ELSE --@EsSimulado = 1
		BEGIN
		BEGIN TRAN
			INSERT INTO staging.RegistroAfectacionAnalisisIntegral (raiRegistroGeneral, raiTipoIdentificacionCotizante, raiNumeroIdentificacionCotizante, raiRegistroDetalladoAfectado)
			SELECT @IdRegistroGeneral, red.redTipoIdentificacionCotizante, red.redNumeroIdentificacionCotizante, redId
			FROM staging.RegistroDetallado red WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON red.redRegistroGeneral = reg.regId
			WHERE regId <> @IdRegistroGeneral
			AND reg.regPeriodoAporte = @periodoAporte
			AND reg.regTipoIdentificacionAportante = @tipoIdentificacionAportante 
			AND reg.regNumeroIdentificacionAportante = @numeroIdentificacionAportante
			AND red.redTipoIdentificacionCotizante = @tipoIdentificacionCotizante
			AND red.redNumeroIdentificacionCotizante = @numeroIdentificacionCotizante
			AND redOUTEstadoValidacionV0 = 'CUMPLE' AND redOUTEstadoValidacionV2 IN ('OK', 'NO_APLICA')
			AND redOUTEstadoValidacionV3 = 'NO_OK'
			AND ISNULL(red.redCorrecciones,'') NOT IN ('A')
			AND NOT EXISTS (
				SELECT TOP 1 1 FROM staging.RegistroAfectacionAnalisisIntegral WITH (NOLOCK) WHERE raiRegistroGeneral = @IdRegistroGeneral AND raiRegistroDetalladoAfectado = red.redId
			);
		COMMIT;
		END;
	END;
END;