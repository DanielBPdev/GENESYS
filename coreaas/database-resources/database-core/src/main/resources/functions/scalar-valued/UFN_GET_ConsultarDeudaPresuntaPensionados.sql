--liquibase formatted sql

--changeset clmarin:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[UFN_GET_ConsultarDeudaPresuntaPensionados] 

/****** Object:  UserDefinedFunction [dbo].[UFN_GET_ConsultarDeudaPresuntaPensionados]   ******/

IF (OBJECT_ID('UFN_GET_ConsultarDeudaPresuntaPensionados') IS NOT NULL)
	DROP FUNCTION [dbo].[UFN_GET_ConsultarDeudaPresuntaPensionados]
GO
-- =============================================
-- Author: Claudia Milena Marín Hincapié
-- Create date: 2020/09/16 
-- Description: Obtiene el calculo de la deuda presunta para el tipo de aportante pensionado
-- =============================================
CREATE FUNCTION UFN_GET_ConsultarDeudaPresuntaPensionados
(
    @idPersona BIGINT, 				-- Identificador único de persona
	@idRolAfiliado BIGINT, 			-- Identificador único de rol afiliado 
	@periodoEvaluacion VARCHAR(7) 	-- Periodo en evaluación YYYY-MM
)
RETURNS NUMERIC(19,5)
AS
BEGIN 
	DECLARE @deuda NUMERIC(19,5) 
	DECLARE @aportePeriodoEvaluacion NUMERIC(19,5) = 0
	DECLARE @deudaPresunta NUMERIC(19,5) = 0
	DECLARE @fechaPeriodoEvaluacion DATE
	DECLARE @fechaActual DATETIME
	DECLARE @estadoRolAfiliado VARCHAR(8)
	
	-- Fechas y periodos
	SET @fechaPeriodoEvaluacion = CONVERT(DATE, @periodoEvaluacion + '-01',  121) 
	
	-- Consulta el valor del aporte a pagar, según el tipo de pensionado
	SELECT @deudaPresunta = 
		roa.roaValorSalarioMesadaIngresos * 
		CASE sol.solClasificacion 
			WHEN 'MENOS_1_5_SM_0_6_POR_CIENTO' THEN 0.006 
			WHEN 'MENOS_1_5_SM_2_POR_CIENTO' THEN 0.02
			WHEN 'MAS_1_5_SM_0_6_POR_CIENTO' THEN 0.006
			WHEN 'MAS_1_5_SM_2_POR_CIENTO' THEN 0.02				
			ELSE 0 
		END  
	FROM SolicitudAfiliacionPersona sap
	JOIN Solicitud sol ON sap.sapSolicitudGlobal = sol.solId
	JOIN RolAfiliado roa ON roa.roaId = sap.sapRolAfiliado
	WHERE (sol.solResultadoProceso = 'APROBADA' OR sol.solAnulada = 0 OR sol.solAnulada IS NULL)
		AND sap.sapRolAfiliado = @idRolAfiliado
	ORDER BY sol.solFechaRadicacion 

	SELECT @estadoRolAfiliado = roaEstadoAfiliado FROM RolAfiliado WHERE roaId = @idRolAfiliado

	IF @estadoRolAfiliado = 'INACTIVO'
	BEGIN
		-- Consulta el total de aportes del pensionado INACTIVO para el periodo en evaluación
		SELECT @aportePeriodoEvaluacion = ISNULL(SUM(apd.apdAporteObligatorio), 0) 
		FROM AporteDetallado apd WITH(NOLOCK)
		JOIN AporteGeneral apg WITH(NOLOCK) ON apd.apdAporteGeneral = apg.apgId
		WHERE apd.apdPersona = @idPersona 
			AND apd.apdTipoCotizante = 'PENSIONADO'
			AND apg.apgPeriodoAporte = @periodoEvaluacion
			AND apd.apdEstadoRegistroAporteArchivo = 'NO_VALIDADO_BD_APROBADO'
	END
	ELSE
	BEGIN
		-- Consulta el total de aportes del pensionado para el periodo en evaluación
		SELECT @aportePeriodoEvaluacion = ISNULL(SUM(apd.apdAporteObligatorio), 0) 
		FROM AporteDetallado apd WITH(NOLOCK)
		JOIN AporteGeneral apg WITH(NOLOCK) ON apd.apdAporteGeneral = apg.apgId
		WHERE apd.apdPersona = @idPersona 
			AND apd.apdTipoCotizante = 'PENSIONADO'
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