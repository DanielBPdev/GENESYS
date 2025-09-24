-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2017/12/07
-- Description:	Procedimiento almacenado encargado de calcular y almacenar la deuda presunta para todos los aportantes pensionados 
-- que a la fecha actual (día hábil vencido) presentan aportes con impago o pago parcial
-- HU169
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaPensionados]
AS

DECLARE @buscarImpagoSeguido BIT

DECLARE @diaSemana INT
DECLARE @diaMes INT
DECLARE @diaHabil INT
DECLARE @diaHabilVencimientoAporte INT

DECLARE @idPersona BIGINT
DECLARE @idRolAfiliado BIGINT

DECLARE @aportePeriodoEvaluacion NUMERIC(19,5)
DECLARE @deudaPresunta NUMERIC(19,5)

DECLARE @fechaInicioMesActual DATETIME
DECLARE @fechaActual DATETIME
DECLARE @fechaPeriodoEvaluacion DATETIME

DECLARE @periodoEvaluacion VARCHAR(7)	
DECLARE @estadoCartera VARCHAR(6)
DECLARE @tipoDeuda VARCHAR(11)

DECLARE @pensionadosCursor AS CURSOR


-- Fechas
SET @fechaActual = dbo.GetLocalDate()

-- Periodos
SET @periodoEvaluacion = CONVERT(VARCHAR(7), @fechaActual, 20) 

-- Días
SET @diaSemana = DATEPART(dw, @fechaActual)	
SET @diaMes = DATEPART(dd, @fechaActual)

-- Fecha de inicio de mes
SET @fechaInicioMesActual = DATEADD(dd, 1 - @diaMes, @fechaActual)

-- Día hábil del mes
SET @diaHabil =
	(DATEDIFF(dd, @fechaInicioMesActual, @fechaActual) + 1)
	-(DATEDIFF(wk, @fechaInicioMesActual, @fechaActual) * 2)
	-(CASE WHEN DATEPART(dw, @fechaInicioMesActual) = 1 THEN 1 ELSE 0 END)
	-(CASE WHEN DATEPART(dw, @fechaActual) = 7 THEN 1 ELSE 0 END)

SELECT @diaHabil = @diaHabil - COUNT(0) 
	FROM DiasFestivos 
	WHERE pifFecha BETWEEN CONVERT(DATE, @fechaInicioMesActual) AND CONVERT(DATE, @fechaActual)

-- Día hábil de vencimiento de aportes
SET @diaHabilVencimientoAporte = @diaHabil - 1


IF @diaSemana > 1 AND @diaSemana < 7
BEGIN
	
	-- Consulta la lista de pensionados	
	SET @pensionadosCursor = CURSOR FAST_FORWARD FOR
		SELECT afi.afiPersona, roa.roaId 
		FROM dbo.RolAfiliado roa
		JOIN dbo.Afiliado afi ON roa.roaAfiliado = afi.afiId
		WHERE roa.roaEstadoAfiliado = 'ACTIVO'
			AND roa.roaTipoAfiliado = 'PENSIONADO'
			AND roa.roaDiaHabilVencimientoAporte = @diaHabilVencimientoAporte
			
	OPEN @pensionadosCursor	
	FETCH NEXT FROM @pensionadosCursor INTO @idPersona, @idRolAfiliado
	
	-- Recorre la lista de pensionados
	WHILE @@FETCH_STATUS = 0
	BEGIN		
			
		-- Consulta el valor del aporte a pagar, según el tipo de pensionado
		SELECT TOP 1 @deudaPresunta = 
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
		WHERE sol.solResultadoProceso = 'APROBADA'
			AND sap.sapRolAfiliado = @idRolAfiliado
		ORDER BY sol.solFechaRadicacion DESC
		
		-- Consulta el total de aportes del pensionado, para el periodo actual
		SELECT @aportePeriodoEvaluacion = ISNULL(SUM(apg.apgValTotalApoObligatorio), 0) 
		FROM AporteDetallado apd
		JOIN AporteGeneral apg ON apd.apdAporteGeneral = apg.apgId
		WHERE apd.apdPersona = @idPersona
			AND apd.apdTipoCotizante = 'PENSIONADO'
			AND apg.apgPeriodoAporte = @periodoEvaluacion
					
					
		IF @deudaPresunta > @aportePeriodoEvaluacion 
		BEGIN -- Indica que el pensionado entra en Cartera				
			SET @estadoCartera = 'AL_DIA'
			SET @tipoDeuda = 'INEXACTITUD'
			
			IF @aportePeriodoEvaluacion = 0
			BEGIN
				SET @estadoCartera = 'MOROSO'
			END
		
			-- Almacena en Cartera
			INSERT INTO Cartera
				(	carDeudaPresunta,
					carEstadoCartera,
					carEstadoOperacion,
					carFechaCreacion,
					carPersona,
					carMetodo,
					carPeriodoDeuda,
					carRiesgoIncobrabilidad,
					carTipoAccionCobro,
					carTipoDeuda,
					carTipoLineaCobro,
					carTipoSolicitante )
				VALUES
				(	@deudaPresunta - @aportePeriodoEvaluacion,
					@estadoCartera,
					'VIGENTE',
					dbo.GetLocalDate(),
					@idPersona,
					NULL,
					@fechaActual,
					NULL,
					NULL,
					@tipoDeuda,
					'LC5',
					'PENSIONADO' )
		END
		
		
		FETCH NEXT FROM @pensionadosCursor INTO @idPersona, @idRolAfiliado
	END
	
	CLOSE @pensionadosCursor
	DEALLOCATE @pensionadosCursor

END