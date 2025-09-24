--liquibase formatted sql

--changeset clmarin:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[UFN_GET_ConsultarDeudaPresuntaIndependientes] 

/****** Object:  UserDefinedFunction [dbo].[UFN_GET_ConsultarDeudaPresuntaIndependientes]   ******/

IF (OBJECT_ID('UFN_GET_ConsultarDeudaPresuntaIndependientes') IS NOT NULL)
	DROP FUNCTION [dbo].[UFN_GET_ConsultarDeudaPresuntaIndependientes]
GO
-- =============================================
-- Author: Claudia Milena Marín Hincapié
-- Create date: 2020/09/16 
-- Description: Obtiene el calculo de la deuda presunta para el tipo de aportante Independiente
-- =============================================
CREATE FUNCTION UFN_GET_ConsultarDeudaPresuntaIndependientes
(
    @idPersona BIGINT, 				-- Identificador único de persona
	@idRolAfiliado BIGINT, 			-- Identificador único de rol afiliado 
	@periodoEvaluacion VARCHAR(7) 	-- Periodo en evaluación YYYY-MM
) 
RETURNS NUMERIC(19,5)
AS
BEGIN 
	DECLARE @estadoRolAfiliado VARCHAR(8)
	
	DECLARE @contadorPeriodos INT

	DECLARE @deuda NUMERIC(19,5)
	DECLARE @aportePeriodoEvaluacion NUMERIC(19,5)
	DECLARE @deudaPresunta NUMERIC(19,5)
	DECLARE @valorAporte NUMERIC(19,5)
	DECLARE @sumatoriaAportes NUMERIC(19,5)

	DECLARE @fechaPeriodoEvaluacion DATE

	DECLARE @periodoDesde VARCHAR(7)
	DECLARE @periodoHasta VARCHAR(7)
	DECLARE @periodoAporte VARCHAR(7)

	DECLARE @periodosCursor AS CURSOR

	-- Fechas y periodos
	SET @fechaPeriodoEvaluacion = CONVERT(DATE, @periodoEvaluacion + '-01',  121) 
	SET @periodoDesde = CONVERT(VARCHAR(7), DATEADD(yy, -1, @fechaPeriodoEvaluacion), 20)
	SET @periodoHasta = CONVERT(VARCHAR(7), DATEADD(mm, -1, @fechaPeriodoEvaluacion), 20)
	
	-- Consulta si el independiente tiene impago el mes anterior al periodo en evaluación
	SELECT @deudaPresunta = ISNULL(car.carDeudaPresunta, 0)
	FROM Cartera car
	WHERE car.carPersona = @idPersona
		AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = @periodoHasta
		AND car.carTipoSolicitante = 'INDEPENDIENTE'
		AND car.carEstadoOperacion = 'VIGENTE'

	SELECT @estadoRolAfiliado = roaEstadoAfiliado FROM RolAfiliado WHERE roaId = @idRolAfiliado
	
	IF @deudaPresunta IS NULL OR @deudaPresunta = 0 -- El independiente no presenta impago en el periodo anterior
	BEGIN 	
		IF @estadoRolAfiliado = 'INACTIVO'
		BEGIN
			-- Consulta los aportes de los últimos 12 meses	para el independiente INACTIVO
			SET @periodosCursor = CURSOR FAST_FORWARD FOR
				SELECT apg.apgPeriodoAporte, ISNULL(SUM(apd.apdAporteObligatorio), 0) 
				FROM AporteDetallado apd WITH(NOLOCK) 
				JOIN AporteGeneral apg WITH(NOLOCK) ON apd.apdAporteGeneral = apg.apgId
				WHERE apd.apdPersona = @idPersona 
					AND apd.apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE'
					AND apg.apgPeriodoAporte BETWEEN @periodoDesde AND @periodoHasta
					AND apd.apdEstadoRegistroAporteArchivo = 'NO_VALIDADO_BD_APROBADO'
				GROUP BY apg.apgPeriodoAporte
				ORDER BY apg.apgPeriodoAporte DESC
		END
		ELSE
		BEGIN	
			-- Consulta los aportes de los últimos 12 meses	para el independiente			
			SET @periodosCursor = CURSOR FAST_FORWARD FOR
				SELECT apg.apgPeriodoAporte, ISNULL(SUM(apd.apdAporteObligatorio), 0) 
				FROM AporteDetallado apd WITH(NOLOCK) 
				JOIN AporteGeneral apg WITH(NOLOCK) ON apd.apdAporteGeneral = apg.apgId
				WHERE apd.apdPersona = @idPersona 
					AND apd.apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE'
					AND apg.apgPeriodoAporte BETWEEN @periodoDesde AND @periodoHasta
					AND apd.apdEstadoRegistroAporteArchivo = 'OK'
				GROUP BY apg.apgPeriodoAporte
				ORDER BY apg.apgPeriodoAporte DESC
		END
		OPEN @periodosCursor	
		FETCH NEXT FROM @periodosCursor INTO @periodoAporte, @valorAporte	

		SET @contadorPeriodos = 0
		SET @sumatoriaAportes = 0

		-- Recorre los aportes del independiente para los últimos 12 meses, desde el más reciente hasta el más antiguo 
		WHILE @@FETCH_STATUS = 0
		BEGIN	
			SET @contadorPeriodos = @contadorPeriodos + 1
		
			IF @periodoAporte = CONVERT(VARCHAR(7), DATEADD(mm, - @contadorPeriodos, @fechaPeriodoEvaluacion), 20) 
			AND @valorAporte > 0 -- Indica que hubo pago para el periodo, entonces, se agrega el valor del aporte a la suma
			BEGIN 
				SET @sumatoriaAportes = @sumatoriaAportes + @valorAporte						
			END
			ELSE -- Indica que hubo impago para el periodo, entonces se promediará hasta este periodo
			BEGIN 			
				BREAK
			END
		
			FETCH NEXT FROM @periodosCursor INTO @periodoAporte, @valorAporte
		END
	
		CLOSE @periodosCursor
		DEALLOCATE @periodosCursor

		IF @contadorPeriodos = 0 -- Indica que el independiente no tiene histórico de aportes
		BEGIN 
			SELECT @deudaPresunta = ISNULL(roa.roaValorSalarioMesadaIngresos * 0.4 * roa.roaPorcentajePagoAportes, 0)	
			FROM RolAfiliado roa
			WHERE roa.roaId = @idRolAfiliado			
		END
		ELSE -- Indica que el independiente sí tiene histórico de aportes
		BEGIN 
			SET @deudaPresunta = @sumatoriaAportes / @contadorPeriodos			
		END
	END

	IF @estadoRolAfiliado = 'INACTIVO'
	BEGIN
		-- Consulta el total de aportes del independiente para el periodo en evaluación
		SELECT @aportePeriodoEvaluacion = ISNULL(SUM(apd.apdAporteObligatorio), 0) 
		FROM AporteDetallado apd WITH(NOLOCK) 
		JOIN AporteGeneral apg WITH(NOLOCK) ON apd.apdAporteGeneral = apg.apgId
		WHERE apd.apdPersona = @idPersona 
			AND apd.apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE'
			AND apg.apgPeriodoAporte = @periodoEvaluacion
			AND apd.apdEstadoRegistroAporteArchivo = 'NO_VALIDADO_BD_APROBADO'
	END
	ELSE
	BEGIN
		-- Consulta el total de aportes del independiente para el periodo en evaluación
		SELECT @aportePeriodoEvaluacion = ISNULL(SUM(apd.apdAporteObligatorio), 0) 
		FROM AporteDetallado apd WITH(NOLOCK) 
		JOIN AporteGeneral apg WITH(NOLOCK) ON apd.apdAporteGeneral = apg.apgId
		WHERE apd.apdPersona = @idPersona 
			AND apd.apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE'
			AND apg.apgPeriodoAporte = @periodoEvaluacion
			AND apd.apdEstadoRegistroAporteArchivo = 'OK'	
	END

	IF (@aportePeriodoEvaluacion = 0 OR (@deudaPresunta > @aportePeriodoEvaluacion))
	BEGIN
		SET @deuda = @deudaPresunta - @aportePeriodoEvaluacion
	END	
	ELSE 
	BEGIN
		SET @deuda = 0
	END
	RETURN @deuda
END