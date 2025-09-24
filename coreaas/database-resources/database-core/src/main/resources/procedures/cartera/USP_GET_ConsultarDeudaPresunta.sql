-- =============================================
-- Author: Claudia Milena Marín Hincapié
-- Create date: 2020/09/16 
-- Description: Obtiene el calculo de la deuda presunta
-- =============================================
CREATE PROCEDURE dbo.USP_GET_ConsultarDeudaPresunta
	@tipoIdentificacion VARCHAR(20),
	@numeroIdentificacion VARCHAR(16),
	@periodoEvaluacion VARCHAR(7),
	@tipoAportante VARCHAR(13),
	@deuda NUMERIC(19,5) OUTPUT
AS
BEGIN 
	DECLARE @idPersona BIGINT
	DECLARE @idRolAfiliado BIGINT
	DECLARE @idEmpleador BIGINT
	DECLARE @idEmpresa BIGINT
	DECLARE @periodo VARCHAR(7)
	DECLARE @oportunidadPago VARCHAR(11)

	DECLARE @activaParametrizacionPersonas BIT
	DECLARE @activaParametrizacionLC2 BIT 
	DECLARE @activaParametrizacionLC3 BIT 
	
	DECLARE @fechaActual DATE
	DECLARE @fechaInicioMesActual DATE
	DECLARE @periodoEvaluacionMesActual VARCHAR(7)	
	DECLARE @periodoEvaluacionMesVencido VARCHAR(7)	
	DECLARE @fechaPeriodoEvaluacion DATE
	
	-- Fechas y periodos
	SET @fechaActual = dbo.GetLocalDate()
	SET @fechaPeriodoEvaluacion = CONVERT(DATE, @periodoEvaluacion + '-01',  121)
	SET @periodoEvaluacionMesActual = CONVERT(VARCHAR(7), DATEADD(mm, 1, @fechaPeriodoEvaluacion), 20) 

	SELECT @activaParametrizacionPersonas=p.pcrActiva FROM ParametrizacionCriterioGestionCobro p
		WHERE p.pcrLineaCobro='LC5' AND p.pcrAccion ='AUTOMATICA'
	
	IF @tipoAportante = 'PENSIONADO' AND @activaParametrizacionPersonas=1
	BEGIN
		SELECT @idPersona=afi.afiPersona, @idRolAfiliado=roa.roaId
		FROM RolAfiliado roa
		JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
		JOIN Persona per ON per.perId = afi.afiPersona
		WHERE roa.roaEstadoAfiliado = 'ACTIVO'
			AND roa.roaTipoAfiliado = 'PENSIONADO'
			AND CONVERT(VARCHAR(7), roa.roaFechaAfiliacion, 20) <> CONVERT(VARCHAR(7), @fechaActual, 20)
			AND 0 = (	
						SELECT COUNT(0) 
						FROM Cartera car 
						WHERE car.carPersona = afi.afiPersona 
							AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoEvaluacion
							AND car.carTipoSolicitante = 'PENSIONADO'
					)
			AND per.perNumeroIdentificacion = @numeroIdentificacion
			AND per.perTipoIdentificacion = @tipoIdentificacion
		
		SET @deuda = dbo.UFN_GET_ConsultarDeudaPresuntaPensionados (@idPersona, @idRolAfiliado, @periodoEvaluacion)
	END
	
	SELECT @activaParametrizacionPersonas=p.pcrActiva FROM ParametrizacionCriterioGestionCobro p
		WHERE p.pcrLineaCobro='LC4' AND p.pcrAccion ='AUTOMATICA'
	
	IF @tipoAportante = 'INDEPENDIENTE' AND @activaParametrizacionPersonas=1
	BEGIN
		SELECT @oportunidadPago = roa.roaOportunidadPago
			FROM RolAfiliado roa
			JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
			JOIN Persona per ON per.perId = afi.afiPersona
			WHERE per.perNumeroIdentificacion = @numeroIdentificacion
				AND per.perTipoIdentificacion = @tipoIdentificacion
		
		IF @oportunidadPago = 'MES_VENCIDO'
		BEGIN
			SELECT @idPersona=afi.afiPersona, @idRolAfiliado=roa.roaId
			FROM RolAfiliado roa
			JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
			JOIN Persona per ON per.perId = afi.afiPersona
			WHERE roa.roaEstadoAfiliado = 'ACTIVO'
				AND roa.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
				AND 0 = (	
							SELECT COUNT(0) 
							FROM Cartera car 
							WHERE car.carPersona = afi.afiPersona 
								AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoEvaluacion
								AND car.carTipoSolicitante = 'INDEPENDIENTE'
						)	
				AND roa.roaOportunidadPago = 'MES_VENCIDO'
				AND per.perNumeroIdentificacion = @numeroIdentificacion
				AND per.perTipoIdentificacion = @tipoIdentificacion
			
			SET @deuda = dbo.UFN_GET_ConsultarDeudaPresuntaIndependientes (@idPersona, @idRolAfiliado, @periodoEvaluacion)
		END
		ELSE 
		BEGIN 
			SELECT @idPersona=afi.afiPersona, @idRolAfiliado=roa.roaId
			FROM RolAfiliado roa
			JOIN Afiliado afi ON roa.roaAfiliado = afi.afiId
			JOIN Persona per ON per.perId = afi.afiPersona
			WHERE roa.roaEstadoAfiliado = 'ACTIVO'
				AND roa.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
				AND 0 = (	
							SELECT COUNT(0) 
							FROM Cartera car 
							WHERE car.carPersona = afi.afiPersona 
								AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoEvaluacionMesActual
								AND car.carTipoSolicitante = 'INDEPENDIENTE'
						)	
				AND (roa.roaOportunidadPago = 'MES_ACTUAL' OR roa.roaOportunidadPago IS NULL)
				AND per.perNumeroIdentificacion = @numeroIdentificacion
				AND per.perTipoIdentificacion = @tipoIdentificacion
		
			SET @deuda = dbo.UFN_GET_ConsultarDeudaPresuntaIndependientes (@idPersona, @idRolAfiliado, @periodoEvaluacionMesActual )
		END
	END

	IF @tipoAportante = 'EMPLEADOR'
	BEGIN
		SELECT @activaParametrizacionLC2=p.pcrActiva FROM ParametrizacionCriterioGestionCobro p
		WHERE p.pcrLineaCobro='LC2' AND p.pcrAccion ='AUTOMATICA'
		SELECT @activaParametrizacionLC3=p.pcrActiva FROM ParametrizacionCriterioGestionCobro p
		WHERE p.pcrLineaCobro='LC3' AND p.pcrAccion ='AUTOMATICA'
		
		-- Personas con una novedad vigente para el periodo del tipo:
		-- 		Incapacidad (IGE, IRL)
		-- 		Licencia de Maternidad (LMA)
		-- 		Suspensión Temporal del Contrato (SLN).
		DECLARE @tablaPersonasConNovedad AS TablaPersonaIdType
		
		INSERT INTO @tablaPersonasConNovedad (perId)
		SELECT per.perId
		FROM SolicitudNovedadPersona snp
		INNER JOIN SolicitudNovedad sno ON snoId = snpSolicitudNovedad
		INNER JOIN NovedadDetalle nop ON nopSolicitudNovedad = snoId
		INNER JOIN Solicitud sol ON solId = snoSolicitudGlobal
		INNER JOIN Persona per ON per.perId = snpPersona
		WHERE solTipoTransaccion IN (
			'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL',
			'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_WEB',
			'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_DEPWEB',
			'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL',
			'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_WEB',
			'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB',
			'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL',
			'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_WEB',
			'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_DEPWEB',
			'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL',
			'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB',
			'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL',
			'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_WEB'
		)
		AND	CONVERT(VARCHAR(7), nopFechaInicio, 120) <= @periodoEvaluacion
		AND CONVERT(VARCHAR(7), nopFechaFin, 120) >= @periodoEvaluacion
				
		SELECT @idPersona=per.perId, @idEmpleador=emd.empId, @idEmpresa=emp.empId
		FROM Empleador emd
		JOIN Empresa emp ON emd.empEmpresa = emp.empId
		JOIN Persona per ON emp.empPersona = per.perId
		LEFT JOIN (	
			SELECT car.carPersona
			FROM Cartera car 
			WHERE CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoEvaluacion
			AND car.carTipoSolicitante = 'EMPLEADOR'
		) AS carerasPer ON  carerasPer.carPersona = per.perId
		WHERE emd.empEstadoEmpleador = 'ACTIVO'
		AND CONVERT(VARCHAR(7), emd.empFechaCambioEstadoAfiliacion, 20) <> CONVERT(VARCHAR(7), @fechaActual, 20)
		AND carerasPer.carPersona IS NULL
		AND per.perNumeroIdentificacion = @numeroIdentificacion
		AND per.perTipoIdentificacion = @tipoIdentificacion
			
		SET @deuda = dbo.UFN_GET_ConsultarDeudaPresuntaEmpleadores (@idPersona, @idEmpleador, @idEmpresa, @periodoEvaluacion, @tablaPersonasConNovedad, @activaParametrizacionLC2, @activaParametrizacionLC3)
	END 
END;