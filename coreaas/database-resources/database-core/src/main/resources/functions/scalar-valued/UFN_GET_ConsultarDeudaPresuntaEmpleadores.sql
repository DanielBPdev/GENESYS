--liquibase formatted sql

--changeset clmarin:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[UFN_GET_ConsultarDeudaPresuntaEmpleadores] 

/****** Object:  UserDefinedFunction [dbo].[UFN_GET_ConsultarDeudaPresuntaEmpleadores]   ******/

IF (OBJECT_ID('UFN_GET_ConsultarDeudaPresuntaEmpleadores') IS NOT NULL)
	DROP FUNCTION [dbo].[UFN_GET_ConsultarDeudaPresuntaEmpleadores]
GO
-- =============================================
-- Author: Claudia Milena Marín Hincapié
-- Create date: 2020/09/16 
-- Description: Obtiene el calculo de la deuda presunta para el tipo de aportante Empleador
-- =============================================
CREATE FUNCTION UFN_GET_ConsultarDeudaPresuntaEmpleadores
(
	@idPersonaEmpleador BIGINT, 	-- Identificador único de persona
	@idEmpleador BIGINT, 			-- Identificador único de empleador
	@idEmpresa BIGINT, 				-- Identificador único de empresa
	@periodoEvaluacion VARCHAR(7), 	-- Periodo en evaluación YYYY-MM
	@tablaPersonasConNovedad TablaPersonaIdType READONLY,
	@activaParametrizacionLC2 BIT,  -- 1: Representa si se encuentra habilitada la parametrización para la linea de cobro 2
	@activaParametrizacionLC3 BIT   -- 1: Representa si se encuentra habilitada la parametrización para la linea de cobro 3 
)
RETURNS NUMERIC(19,5)
AS
BEGIN 
	DECLARE @contadorPeriodos INT
	DECLARE @sumatoriaAportes NUMERIC(19,5)
	DECLARE @deuda NUMERIC(19,5)
	DECLARE @valorAporte NUMERIC(19,5)

	DECLARE @fechaRetiro DATE
	DECLARE @fechaPeriodoEvaluacion DATE
	DECLARE @fechaActual DATETIME

	DECLARE @lineaCobro VARCHAR(3)
	DECLARE @periodo1MesAtras VARCHAR(7)
	DECLARE @periodo1AnioAtras VARCHAR(7)
	DECLARE @periodo2AniosAtras VARCHAR(7)
	DECLARE @periodoAporte VARCHAR(7)
	DECLARE @periodoCambioEstadoAfiliacion VARCHAR(7)

	DECLARE @periodosCursor AS CURSOR

	DECLARE @deudaPresunta NUMERIC(19,5)
	
	DECLARE @dFechaBeneficio DATE
    DECLARE @bDepartamentoEspecial BIT
    DECLARE @porcentajeEmpleador NUMERIC(6,5)
	
	-- Fechas y periodos
	SET @fechaPeriodoEvaluacion = CONVERT(DATE, @periodoEvaluacion + '-01',  121) 
	SET @periodo1MesAtras = CONVERT(VARCHAR(7), DATEADD(mm, -1, @fechaPeriodoEvaluacion), 20)  
	SET @periodo1AnioAtras = CONVERT(VARCHAR(7), DATEADD(yy, -1, @fechaPeriodoEvaluacion), 20)
	SET @periodo2AniosAtras = CONVERT(VARCHAR(7), DATEADD(yy, -2, @fechaPeriodoEvaluacion), 20)  
	SET @fechaActual = dbo.GetLocalDate()

	--Verifica el porcentaje del empleador
	SELECT @bDepartamentoEspecial = bemPerteneceDepartamento, @dFechaBeneficio = bemFechaVinculacion 
	FROM BeneficioEmpleador, Beneficio 
	WHERE bemBeneficioActivo=1 AND bemBeneficio = befId AND befTipoBeneficio='LEY_1429' AND bemEmpleador=@idEmpleador
	
	SET @porcentajeEmpleador = dbo.UFN_GET_Porcentaje1429(@fechaActual, @dFechaBeneficio, @bDepartamentoEspecial)
		
	-- Almacena el identificador de las personas que son trabajadores o cotizantes
	DECLARE @tablaTrabajadoresCotizantesSinNov AS TABLE (
		perId BIGINT,
		trabajadorActivo BIT,
		trabajadorActivoAlMenosUnDia BIT,
		sinArportePeriodoEvaluacion BIT
	)
	
	-- Almacena los aportes del periodos por empresa/aportante
	DECLARE @tablaAportesVigentes TABLE(
		perId BIGINT,
		aporte NUMERIC(19,5),
		deudaIndividual NUMERIC(19,5),
		tipoAporte VARCHAR(37)
	)
		
	-- Almacena los aportes de la empresa
	DECLARE @tablaAportesTemp AS TABLE (
		apdPersona BIGINT,
		apdAporteObligatorio NUMERIC(19,5),
		apdSalarioBasico NUMERIC(19,5),
		apdEstadoRegistroAporteArchivo VARCHAR(60),
		apdEstadoAporteAjuste VARCHAR(50),
		apgPeriodoAporte VARCHAR(7),
		apgEstadoAporteAportante VARCHAR(40)
	)
	
	-- En esta tabla se agregan las novedades que no se identificaron en el primer punto por no tener novedad detalle 
	-- y la fecha se restringe en este caso con roaFechaRetiro
	DECLARE @novedadRetiro AS TABLE (
		norPersona BIGINT
	)
	
	-- Se consultan los aportes de la empresa de los periodos indicados				
	INSERT INTO @tablaAportesTemp(
		apdPersona, apdAporteObligatorio, apdSalarioBasico, apdEstadoRegistroAporteArchivo, apdEstadoAporteAjuste,
		apgPeriodoAporte, apgEstadoAporteAportante
	)
	SELECT 
		apdPersona, apdAporteObligatorio, apdSalarioBasico, apdEstadoRegistroAporteArchivo, apdEstadoAporteAjuste,
		apgPeriodoAporte, apgEstadoAporteAportante 
	FROM AporteDetallado apd WITH(NOLOCK) 
	JOIN AporteGeneral apg WITH(NOLOCK) ON apd.apdAporteGeneral = apg.apgId
	WHERE apg.apgEmpresa = @idEmpresa
	AND apg.apgPeriodoAporte IN (@periodoEvaluacion, @periodo1MesAtras)
	AND apd.apdTipoCotizante = 'TRABAJADOR_DEPENDIENTE'

	-- Se consultan los aportes vigentes para las personas seleccionadas en el periodo de evaluación
	INSERT INTO @tablaAportesVigentes(perId, aporte, deudaIndividual, tipoAporte)
	SELECT 
		apdPersona, apdAporteObligatorio, 0, ISNULL(apdEstadoRegistroAporteArchivo, '') 
	FROM @tablaAportesTemp
	WHERE apgPeriodoAporte = @periodoEvaluacion
	AND apgEstadoAporteAportante = 'VIGENTE'
	AND apdEstadoAporteAjuste = 'VIGENTE'

	INSERT INTO @novedadRetiro (norPersona)
	SELECT snpPersona
	FROM SolicitudNovedadPersona
	INNER JOIN SolicitudNovedad ON snoId = snpSolicitudNovedad
	INNER JOIN Solicitud ON solId = snoSolicitudGlobal
	INNER JOIN Persona ON perId = snpPersona
	INNER JOIN Afiliado ON afiPersona = perId
	INNER JOIN RolAfiliado ON roaAfiliado = afiId
	WHERE solTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE')
	AND roaEmpleador = @idEmpleador
	AND CONVERT(VARCHAR(7), roaFechaRetiro, 120) = @periodoEvaluacion 
		
	SET @deuda = 0 
	
	-- Cálculo del monto de la deuda presunta
	IF @porcentajeEmpleador>0 -- Ejecutado desde USP_ExecuteCARTERACrearCartera 
	BEGIN 
		-- Consulta si el empleador tuvo impago el mes anterior al periodo en evaluación
		SELECT @deudaPresunta = ISNULL(car.carDeudaPresunta, 0)
		FROM Cartera car
		WHERE car.carPersona = @idPersonaEmpleador
			AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodo1MesAtras
			AND car.carTipoSolicitante = 'EMPLEADOR'
			AND car.carEstadoOperacion = 'VIGENTE'
			
		IF NOT EXISTS (SELECT 
							car.carDeudaPresunta
					   FROM Cartera car
					   WHERE car.carPersona = @idPersonaEmpleador
						AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodo1MesAtras
                        AND car.carTipoSolicitante = 'EMPLEADOR'
                        AND car.carEstadoOperacion = 'VIGENTE')
        BEGIN
            SET @deudaPresunta = 0
        END
		
		IF @deudaPresunta IS NULL OR @deudaPresunta = 0 -- El empleador no presenta impago en el periodo anterior
		BEGIN 		
			SET @contadorPeriodos = 0
			SET @sumatoriaAportes = 0
	
			-- Consulta los aportes del empleador en los últimos 12 periodos anteriores al periodo en evaluació		
			SET @periodosCursor = CURSOR FAST_FORWARD FOR	
				SELECT apg.apgPeriodoAporte, ISNULL(SUM(apg.apgValTotalApoObligatorio), 0) 
				FROM AporteGeneral apg WITH(NOLOCK) 
				WHERE apg.apgEmpresa = @idEmpresa			
					AND apg.apgPeriodoAporte BETWEEN @periodo1AnioAtras AND @periodo1MesAtras			
				GROUP BY apg.apgPeriodoAporte
				ORDER BY apg.apgPeriodoAporte DESC
			
			OPEN @periodosCursor	
			FETCH NEXT FROM @periodosCursor INTO @periodoAporte, @valorAporte
	
			-- Recorre los aportes de los últimos 12 periodos, desde el más reciente hasta el más antiguo 
			WHILE @@FETCH_STATUS = 0
			BEGIN	
				IF @periodoAporte = CONVERT(VARCHAR(7), DATEADD(mm, - (@contadorPeriodos+1), @fechaPeriodoEvaluacion), 20) -- Indica que se realizará el pago para el periodo, entonces, se agrega el valor del aporte a la suma
				BEGIN
					SET @contadorPeriodos = @contadorPeriodos + 1
					SET @sumatoriaAportes = @sumatoriaAportes + @valorAporte						
				END
				ELSE -- Indica que hubo impago, entonces se promediará hasta este periodo
				BEGIN 				
					BREAK
				END
				FETCH NEXT FROM @periodosCursor INTO @periodoAporte, @valorAporte
			END
		
			CLOSE @periodosCursor
			DEALLOCATE @periodosCursor
	
			IF @contadorPeriodos > 0 -- Indica que el empleador tiene histórico de aportes
			BEGIN
				SET @deudaPresunta = @sumatoriaAportes / @contadorPeriodos
			END	
			ELSE -- Indica que el empleador no tiene histórico de aportes
			BEGIN 
				SELECT 	@periodoCambioEstadoAfiliacion = CONVERT(VARCHAR(7), emd.empFechaCambioEstadoAfiliacion, 20), 
						@fechaRetiro = emd.empFechaRetiro,
						@deudaPresunta = emd.empValorTotalUltimaNomina * 0.04
				FROM Empleador emd
				WHERE emd.empId = @idEmpleador
			
				-- @periodoCambioEstadoAfiliacion = CONVERT(VARCHAR(7), DATEADD(mm, -1, @fechaActual), 20) AND 
				-- Se quita de la validación por si no se ha presentado pagos en varios periodos desde la afiliación
				IF @fechaRetiro IS NOT NULL -- Indica que es una nueva afiliación
				BEGIN
					SELECT @deudaPresunta = ISNULL(AVG(tablaAportes.aporte), 0)
					FROM (	
						SELECT TOP 6 SUM(apg.apgValTotalApoObligatorio) aporte
						FROM AporteGeneral apg WITH(NOLOCK) 
						WHERE apg.apgEmpresa = @idEmpresa			
						AND apg.apgValTotalApoObligatorio > 0
						AND apg.apgPeriodoAporte BETWEEN @periodo2AniosAtras AND @periodo1MesAtras						
						GROUP BY apg.apgPeriodoAporte
						ORDER BY apg.apgPeriodoAporte DESC 
					) AS tablaAportes					
				END			
			END
		END
			
		IF @deudaPresunta IS NOT NULL AND @deudaPresunta > 0 
		BEGIN
			-- Deuda presunta calculada, ahora se determinan todos los trabajadores/cotizantes
			
			-- HU-223-169 
			-- El valor de la deuda presunta se distribuirán en trabajadores que:
			-- a) Se tenga activos en al menos un día en el periodo en estudio. 
			-- Considerar aquellos que No tengan para el periodo una novedad vigente del tipo:
			-- 		Incapacidad (IGE, IRL)
			-- 		Licencia de Maternidad (LMA)
			-- 		Suspensión Temporal del Contrato (SLN).
			INSERT INTO @tablaTrabajadoresCotizantesSinNov (perId, trabajadorActivoAlMenosUnDia, trabajadorActivo, sinArportePeriodoEvaluacion) 
			SELECT afi.afiPersona, 1, CASE WHEN roa.roaEstadoAfiliado = 'ACTIVO' THEN 1 ELSE 0 END, 0 
			FROM RolAfiliado roa
			JOIN Afiliado afi ON afi.afiId = roa.roaAfiliado
			LEFT JOIN @tablaPersonasConNovedad AS exclusiones ON afi.afiPersona = exclusiones.perId
			WHERE exclusiones.perId IS NULL
			AND roa.roaEmpleador = @idEmpleador
			AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
			-- Activos al menos un día en el periodo 
			AND (
				-- se activó en el periodo de estudio
				CONVERT(DATE, @periodoEvaluacion + '-01',  121) = DATEADD(MONTH, DATEDIFF(MONTH, 0, roa.roaFechaAfiliacion), 0)
				-- aún activo 
				OR (roa.roaFechaRetiro IS NULL AND EOMONTH(CONVERT(DATE, @periodoEvaluacion + '-01',  121)) >= DATEADD(MONTH, DATEDIFF(MONTH, 0, roa.roaFechaAfiliacion), 0)) 
				-- se retiró en el mes de estudio
				OR CONVERT(DATE, @periodoEvaluacion + '-01',  121) = DATEADD(MONTH, DATEDIFF(MONTH, 0, roa.roaFechaRetiro), 0)
			)
			
			
			-- Almacena el identificador de las personas que son trabajadores o cotizantes
			DECLARE @tablaPosiblesCotizantesSinNov AS TABLE (
				perId BIGINT
			)
			 
			-- Personas con aporte del mes anterior al evaluado, sin aporte en el mes evaluado y sin novedad para la empresa evaluada
			INSERT INTO @tablaPosiblesCotizantesSinNov (perId) 
			SELECT DISTINCT(cotizantesMesAnterio.apdPersona) 
			FROM (
				SELECT apdPersona
				FROM @tablaAportesTemp
				WHERE apgPeriodoAporte = @periodo1MesAtras
			) AS cotizantesMesAnterio
			LEFT JOIN (
				SELECT apdPersona
				FROM @tablaAportesTemp
				WHERE apgPeriodoAporte = @periodoEvaluacion
			) AS cotizantesPeriodoEvaluado ON cotizantesMesAnterio.apdPersona = cotizantesPeriodoEvaluado.apdPersona
			LEFT JOIN @tablaPersonasConNovedad AS exclusiones ON cotizantesMesAnterio.apdPersona = exclusiones.perId
			WHERE cotizantesPeriodoEvaluado.apdPersona IS NULL -- Personas con aporte en el mes anterior pero sin aporte en el mes evaluado
			AND exclusiones.perId IS NULL -- Personas con aporte en el mes anterior pero sin noveades en el mes evaluado
			
			
			-- HU-223-169 
			-- El valor de la deuda presunta se distribuirán trabajadores que:
			-- b) Se hayan recibidos pagos de aportes del periodo anterior y no tengan 
			-- aportes para el periodo en estudio y tampoco tengan una novedad registrada o guardada.
			INSERT INTO @tablaTrabajadoresCotizantesSinNov (perId, trabajadorActivoAlMenosUnDia, trabajadorActivo, sinArportePeriodoEvaluacion) 
			SELECT tcot.perId, 0, 0, 1
			FROM @tablaPosiblesCotizantesSinNov tcot
			LEFT JOIN @tablaTrabajadoresCotizantesSinNov ttcs ON ttcs.perId = tcot.perId
			WHERE ttcs.perId IS NULL
			
			-- Se marcan las personas que ya se habian consultado como activas al menos un día 
			-- indicando que no tienen aporte en el periodo evaluado 
			UPDATE ttcs
			SET sinArportePeriodoEvaluacion = 1
			FROM @tablaPosiblesCotizantesSinNov tcot
			INNER JOIN @tablaTrabajadoresCotizantesSinNov ttcs ON ttcs.perId = tcot.perId
			
			IF EXISTS (SELECT TOP 1 perId FROM @tablaAportesVigentes)  -- que no tenga aportes
				AND (NOT EXISTS (SELECT TOP 1 perId FROM @tablaTrabajadoresCotizantesSinNov) 
					OR (@activaParametrizacionLC2=1 AND NOT EXISTS (    -- que tenga al menos UN trabajador/cotizante sin novedad que no tengan aportes
							SELECT TOP 1 tcs.perId 
							FROM @tablaTrabajadoresCotizantesSinNov tcs
							LEFT JOIN @tablaAportesVigentes tap ON tap.perId = tcs.perId
							WHERE tap.perId IS NULL
						))
					OR (@activaParametrizacionLC2=1 AND NOT EXISTS (SELECT TOP 1 perId FROM @tablaAportesVigentes WHERE tipoAporte != 'OK'))
					)					
			BEGIN
				SET @deudaPresunta=0
			END

			UPDATE tapv
			SET deudaIndividual = @deudaPresunta/(SELECT COUNT(DISTINCT(perId)) FROM @tablaTrabajadoresCotizantesSinNov)
			FROM @tablaAportesVigentes tapv
			
		END -- Fin IF @deudaPresunta > 0
		
		SET @deuda = @deudaPresunta
		
	END -- Fin de cálculo de deuda presunta
	RETURN @deuda
END