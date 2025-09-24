-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/02/16
-- Description:	Procedimiento almacenado encargado aplicar la marca de aportante
-- para proceso de fiscalización
-- =============================================
CREATE PROCEDURE [dbo].[USP_AsignarMarcaProcesoFiscalizacion]
	@IdRegistroGeneral BIGINT
AS
BEGIN
SET NOCOUNT ON;	
	--print 'Inicia USP_AsignarMarcaProcesoFiscalizacion'
	DECLARE @bEnviadoAFiscalizacion BIT = 0
	DECLARE @sMotivoFiscalizacion VARCHAR(30) = NULL

	create table #condicionesDetalles (redId BIGINT, enviarAFiscalizacion BIT, motivoFiscalizacion VARCHAR(30))

	-- se consulta si el registro general tiene inconsistencias de Tipo_0 en PILA 1 (a nivel de planilla y/o empleador)
	SELECT @bEnviadoAFiscalizacion = CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END, 
		 @sMotivoFiscalizacion = 'VALIDACIONES_PILA'
	FROM staging.RegistroGeneral reg with(nolock)
	LEFT JOIN dbo.PilaErrorValidacionLog pev with(nolock) ON reg.regRegistroControl = pev.pevIndicePlanilla AND pev.pevBloqueValidacion = 'BLOQUE_4_OI'
	WHERE reg.regId = @IdRegistroGeneral
	AND pev.pevTipoError != 'ERROR_TECNICO'
	AND pev.pevId IS NOT NULL

	INSERT INTO #condicionesDetalles (redId, enviarAFiscalizacion, motivoFiscalizacion)
	SELECT red.redId, CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END, 'VALIDACIONES_PILA'
	FROM staging.RegistroGeneral reg with(nolock)
	INNER JOIN staging.RegistroDetallado red with(nolock) ON red.redRegistroGeneral = reg.regId
	LEFT JOIN dbo.PilaErrorValidacionLog pev with(nolock) ON reg.regRegistroControl = pev.pevIndicePlanilla 
		AND pev.pevIdRegistroTipo2 = red.redIdRegistro2pila
		AND pev.pevBloqueValidacion = 'BLOQUE_4_OI'
	WHERE reg.regId = @IdRegistroGeneral
		AND pev.pevTipoError != 'ERROR_TECNICO'
		AND pev.pevId IS NOT NULL
		AND red.redOUTTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
	GROUP BY red.redId 

	-- se consulta el estado del registro del aporte
	IF @bEnviadoAFiscalizacion = 0
	BEGIN
		IF EXISTS (
			SELECT TOP 1 reg.regId, red.redId, red.redOUTMarcaValRegistroAporte, red.redOUTEstadoValidacionV0, red.redOUTEstadoValidacionV1, red.redOUTEstadoValidacionV2, red.redOUTEstadoValidacionV3
			FROM staging.RegistroGeneral reg with(nolock)
			INNER JOIN staging.RegistroDetallado red with(nolock) ON red.redRegistroGeneral = reg.regId
			WHERE reg.regId = @IdRegistroGeneral
			AND (red.redOUTMarcaValRegistroAporte IS NULL
			OR red.redOUTMarcaValRegistroAporte IN ('NO_VALIDADO_BD', 'NO_VALIDADO_BD_APROBADO')
			OR (red.redOUTMarcaValRegistroAporte IN ('VALIDAR_COMO_INDEPENDIENTE', 'VALIDAR_COMO_PENSIONADO')
				AND (red.redOUTEstadoValidacionV1 IS NULL
					OR red.redOUTEstadoValidacionV1 IN ('NO_OK', 'NO_OK_APROBADO')))
			OR (red.redOUTMarcaValRegistroAporte = 'VALIDAR_COMO_DEPENDIENTE' 
				AND (red.redOUTEstadoValidacionV0 IS NULL
					OR red.redOUTEstadoValidacionV0 IN ('NO_OK', 'NO_OK_APROBADO')
					OR red.redOUTEstadoValidacionV1 IS NULL
					OR red.redOUTEstadoValidacionV1 IN ('NO_OK', 'NO_OK_APROBADO')
					OR red.redOUTEstadoValidacionV2 IS NULL
					OR red.redOUTEstadoValidacionV2 IN ('NO_OK', 'NO_OK_APROBADO')
					OR red.redOUTEstadoValidacionV3 IS NULL
					OR red.redOUTEstadoValidacionV3 IN ('NO_OK', 'NO_OK_APROBADO'))))
		)
		BEGIN
			SET @bEnviadoAFiscalizacion = 1
			SET @sMotivoFiscalizacion = 'APORTES_NO_OK'
		END
	END

	INSERT INTO #condicionesDetalles (redId, enviarAFiscalizacion, motivoFiscalizacion)
	SELECT red.redId, CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END, 'APORTES_NO_OK'
	FROM staging.RegistroGeneral reg with(nolock)
	INNER JOIN staging.RegistroDetallado red with(nolock) ON red.redRegistroGeneral = reg.regId 
	WHERE reg.regId = @IdRegistroGeneral
		AND red.redOUTTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
		AND (red.redOUTEstadoValidacionV1 IS NULL
			OR red.redOUTEstadoValidacionV1 IN ('NO_OK', 'NO_OK_APROBADO'))
		AND red.redId NOT IN (SELECT redId FROM #condicionesDetalles)
	GROUP BY red.redId 

	-- se consultan los salarios e IBC de los cotizantes respecto al período anterior
	IF @bEnviadoAFiscalizacion = 0
	BEGIN
		DECLARE @sPeriodoAnterior VARCHAR(7)
		DECLARE @sTipoIdAportante VARCHAR(20)
		DECLARE @sIdAportante VARCHAR(16)

		SELECT @sPeriodoAnterior = CONVERT(VARCHAR(7), DATEADD(MONTH, -1, CAST(reg.regPeriodoAporte+'-01' AS DATE)), 121),
			@sTipoIdAportante = reg.regTipoIdentificacionAportante, 
			@sIdAportante = reg.regNumeroIdentificacionAportante
		FROM staging.RegistroGeneral reg with(nolock)
		WHERE reg.regId = @IdRegistroGeneral

		IF EXISTS (
			SELECT TOP 1 reg.regId 
			FROM staging.RegistroGeneral reg with(nolock)
			WHERE reg.regTipoIdentificacionAportante = @sTipoIdAportante
			AND reg.regNumeroIdentificacionAportante = @sIdAportante
			AND reg.regPeriodoAporte = @sPeriodoAnterior
		)
		BEGIN
			SELECT @sMotivoFiscalizacion = CASE 
				WHEN MAX(menorSalario) = 1 THEN 'TRABAJADORES_SALARIO_MENOR'
				WHEN MAX(menorIBC) = 1 THEN 'TRABAJADORES_IBC_MENOR'
				ELSE NULL
			END
			FROM (
				SELECT CASE WHEN (redAct.redSalarioBasico < ant.redSalarioBasico) THEN 1 ELSE 0 END menorSalario,
					CASE WHEN (redAct.redValorIBC < ant.redValorIBC) THEN 1 ELSE 0 END menorIBC
				FROM staging.RegistroGeneral regAct with(nolock) 
				INNER JOIN staging.RegistroDetallado redAct with(nolock) ON redAct.redRegistroGeneral = regAct.regId
				LEFT JOIN (
					SELECT reg.regTipoIdentificacionAportante, reg.regNumeroIdentificacionAportante, 
						red.redTipoIdentificacionCotizante, red.redNumeroIdentificacionCotizante, 
						MAX(red.redSalarioBasico) redSalarioBasico, MAX(red.redValorIBC) redValorIBC
					FROM staging.RegistroGeneral reg with(nolock)
					INNER JOIN staging.RegistroDetallado red with(nolock) ON red.redRegistroGeneral = reg.regId
					WHERE reg.regTipoIdentificacionAportante = @sTipoIdAportante
					AND reg.regNumeroIdentificacionAportante = @sIdAportante
					AND reg.regPeriodoAporte = @sPeriodoAnterior
					AND red.redOUTEstadoEvaluacionAporte IS NOT NULL
					GROUP BY reg.regTipoIdentificacionAportante, reg.regNumeroIdentificacionAportante, 
						red.redTipoIdentificacionCotizante, red.redNumeroIdentificacionCotizante
				) ant ON ant.regTipoIdentificacionAportante = regAct.regTipoIdentificacionAportante
					AND ant.regNumeroIdentificacionAportante = regAct.regNumeroIdentificacionAportante
					AND ant.redTipoIdentificacionCotizante = redAct.redTipoIdentificacionCotizante
					AND ant.redNumeroIdentificacionCotizante = redAct.redNumeroIdentificacionCotizante
				WHERE regAct.regId = @IdRegistroGeneral
			) comparacionPeridos

			IF @sMotivoFiscalizacion IS NOT NULL
			BEGIN
				SET @bEnviadoAFiscalizacion = 1
			END
		END
	END

	-- se revisa el reporte de novedades de retiro en período retroactivo
	create table #retiros (redId BIGINT, tipoAfiliado VARCHAR(60), hayRetiroRetroactivo BIT)
	DECLARE @sPeriodoRegular VARCHAR(7)

	DECLARE @periodosRegulares AS TABLE (
		idRD BIGINT,
		periodoRegular VARCHAR(7)
	)

	INSERT INTO @periodosRegulares (idRD, periodoRegular)
	SELECT redId, 
		CASE 
			WHEN ISNULL(redOUTPeriodicidad, 'MES_VENCIDO') = 'MES_VENCIDO' 
			THEN CONVERT(VARCHAR(7), DATEADD(MONTH, -1, dbo.GetLocalDate()), 121)
			ELSE CONVERT(VARCHAR(7), dbo.GetLocalDate(), 121)
		END periodo
	FROM staging.RegistroDetallado with(nolock)
	WHERE redRegistroGeneral = @IdRegistroGeneral

	INSERT INTO #retiros (redId, tipoAfiliado, hayRetiroRetroactivo)
	SELECT red.redId, red.redOUTTipoAfiliado, 
		MAX(CASE WHEN reg.regPeriodoAporte != periodoRegular AND ISNULL(red.redNovRetiro, '') != '' THEN 1 ELSE 0 END) hayRetiroRetroactivo
	FROM staging.RegistroGeneral reg with(nolock)
	INNER JOIN staging.RegistroDetallado red with(nolock) ON red.redRegistroGeneral = reg.regId
	INNER JOIN @periodosRegulares pr ON red.redId = pr.idRD
	WHERE reg.regId = @IdRegistroGeneral
	GROUP BY red.redId, red.redOUTTipoAfiliado

	IF @bEnviadoAFiscalizacion = 0 AND EXISTS (
		SELECT TOP 1 1 FROM #retiros with(nolock) WHERE tipoAfiliado = 'TRABAJADOR_DEPENDIENTE' AND hayRetiroRetroactivo = 1
	)
	BEGIN
		SET @bEnviadoAFiscalizacion = 1
		SET @sMotivoFiscalizacion = 'NOVEDAD_RETIRO'
	END

	INSERT INTO #condicionesDetalles (redId, enviarAFiscalizacion, motivoFiscalizacion)
	SELECT redId, hayRetiroRetroactivo, 'NOVEDAD_RETIRO' 
	FROM #retiros with(nolock) WHERE tipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' AND hayRetiroRetroactivo = 1
		AND redId NOT IN (SELECT redId FROM #condicionesDetalles)

	-- se actualiza la marca en el registro general
	UPDATE staging.RegistroGeneral 
	SET regOUTEnviadoAFiscalizacion = @bEnviadoAFiscalizacion, 
		regOUTMotivoFiscalizacion = @sMotivoFiscalizacion,
		regDateTimeUpdate = dbo.getLocalDate()
	WHERE regId = @IdRegistroGeneral

	-- se actualizan los registros detallados de independientes
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	
	UPDATE red
	SET 
		redDateTimeUpdate = @redDateTimeUpdate,
		red.redOUTEnviadoAFiscalizacionInd = cdt.enviarAFiscalizacion, 
		red.redOUTMotivoFiscalizacionInd = cdt.motivoFiscalizacion
	FROM staging.RegistroDetallado red with(nolock)
	INNER JOIN #condicionesDetalles cdt with(nolock) ON red.redId = cdt.redId

	--print 'Finaliza USP_AsignarMarcaProcesoFiscalizacion'
END;