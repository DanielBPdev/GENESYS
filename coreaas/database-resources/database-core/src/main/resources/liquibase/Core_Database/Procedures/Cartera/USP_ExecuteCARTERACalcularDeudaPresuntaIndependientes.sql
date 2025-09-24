-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2017/12/07
-- Description:	Procedimiento almacenado encargado de calcular y almacenar la deuda presunta para todos los aportantes independientes 
-- que a la fecha actual (día hábil vencido) presentan aportes con impago o pago parcial
-- HU169
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaIndependientes]
AS

DECLARE @buscarImpagoAnterior BIT

DECLARE @diaSemana INT
DECLARE @diaMes INT
DECLARE @diaHabil INT
DECLARE @diaHabilVencimientoAporte INT
DECLARE @contador INT

DECLARE @idPersona BIGINT
DECLARE @idRolAfiliado BIGINT

DECLARE @aportePeriodoActual NUMERIC(19,5)
DECLARE @valor NUMERIC(19,5)
DECLARE @sumatoria NUMERIC(19,5)
DECLARE @deudaPresunta NUMERIC(19,5)

DECLARE @fechaInicioMesActual DATETIME
DECLARE @fechaActual DATETIME
	
DECLARE @periodoEvaluacion VARCHAR(7)
DECLARE @periodoAnteriorEvaluacion VARCHAR(7)
DECLARE @periodoAnioAnterior VARCHAR(7)
DECLARE @periodo VARCHAR(7)
DECLARE @estadoCartera VARCHAR(6)
DECLARE @tipoDeuda VARCHAR(11)

DECLARE @independientesCursor AS CURSOR
DECLARE @periodosCursor AS CURSOR


-- Fechas
SET @fechaActual = dbo.GetLocalDate()

-- Periodos
SET @periodoEvaluacion = CONVERT(VARCHAR(7), @fechaActual, 20) 
SET @periodoAnteriorEvaluacion = CONVERT(VARCHAR(7), DATEADD(mm, -1, @fechaActual), 20) 
SET @periodoAnioAnterior = CONVERT(VARCHAR(7), DATEADD(yy, -1, @fechaActual), 20) 

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
	
	-- Consulta la lista de independientes	
	SET @independientesCursor = CURSOR FAST_FORWARD FOR
		SELECT afi.afiPersona, roa.roaId 
		FROM dbo.RolAfiliado roa
		JOIN dbo.Afiliado afi ON roa.roaAfiliado = afi.afiId
		WHERE roa.roaEstadoAfiliado = 'ACTIVO'
			AND roa.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
			AND roa.roaDiaHabilVencimientoAporte = @diaHabilVencimientoAporte
			
	OPEN @independientesCursor	
	FETCH NEXT FROM @independientesCursor INTO @idPersona, @idRolAfiliado
	
	-- Recorre la lista de independientes
	WHILE @@FETCH_STATUS = 0
	BEGIN		
			
		-- Consulta los aportes de los últimos 12 meses	para el independiente			
		SET @periodosCursor = CURSOR FAST_FORWARD FOR
			SELECT apg.apgPeriodoAporte, SUM(apg.apgValTotalApoObligatorio) 
			FROM AporteDetallado apd
			JOIN AporteGeneral apg ON apd.apdAporteGeneral = apg.apgId
			WHERE apd.apdPersona = @idPersona
				AND apd.apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE'
				AND apg.apgPeriodoAporte BETWEEN @periodoAnioAnterior AND @periodoAnteriorEvaluacion
			GROUP BY apg.apgPeriodoAporte
			ORDER BY apg.apgPeriodoAporte DESC
			
		OPEN @periodosCursor	
		FETCH NEXT FROM @periodosCursor INTO @periodo, @valor	
		
		SET @contador = 0
		SET @sumatoria = 0
		SET @buscarImpagoAnterior = 0
		
		-- Recorre los aportes del independiente para los últimos 12 meses, desde el más reciente hasta el más antiguo 
		WHILE @@FETCH_STATUS = 0
		BEGIN	
			SET @contador = @contador + 1
			
			IF @periodo = CONVERT(VARCHAR(7), DATEADD(mm, -@contador, @fechaActual), 20)
			BEGIN -- Indica que hubo pago para el periodo, entonces, se agrega el valor del aporte a la suma
				SET @sumatoria = @sumatoria + @valor						
			END
			ELSE
			BEGIN -- Indica que hubo impago para el periodo, entonces se validará si el periodo inmediatamente anterior también tuvo impago						
				SET @buscarImpagoAnterior = 1
				SET @contador = @contador - 1				
				BREAK
			END
			
			FETCH NEXT FROM @periodosCursor INTO @periodo, @valor
		END
		
		IF @contador = 0
		BEGIN -- Indica que el independiente no tiene histórico de aportes
			SELECT @deudaPresunta = ISNULL(roa.roaValorSalarioMesadaIngresos * 0.4 * roa.roaPorcentajePagoAportes, 0)	
			FROM RolAfiliado roa
			WHERE roa.roaId = @idRolAfiliado			
		END
		ELSE
		BEGIN -- Indica que el independiente sí tiene histórico de aportes
			SET @deudaPresunta = @sumatoria / @contador
		
			IF @buscarImpagoAnterior = 1
			BEGIN -- Se valida si el periodo inmediatamente anterior también tuvo impago
				FETCH NEXT FROM @periodosCursor INTO @periodo, @valor
				
				IF @@FETCH_STATUS = 0
				BEGIN
					IF @periodo <> CONVERT(VARCHAR(7), DATEADD(mm, -@contador-1, @fechaActual), 20)
					BEGIN -- La deuda presunta se hereda del periodo anterior 
						SELECT @deudaPresunta = car.carDeudaPresunta
						FROM Cartera car
						WHERE car.carPersona = @idPersona
							AND CONVERT(VARCHAR(7), car.carPeriodoDeuda, 20) = CONVERT(VARCHAR(7), DATEADD(mm, -@contador, @fechaActual), 20)
					END
				END
			END				
		END
		
		CLOSE @periodosCursor
		DEALLOCATE @periodosCursor

		
		-- Consulta el total de aportes del independiente para el periodo en evaluación
		SELECT @aportePeriodoActual = ISNULL(SUM(apg.apgValTotalApoObligatorio),0) 
		FROM AporteDetallado apd
		JOIN AporteGeneral apg ON apd.apdAporteGeneral = apg.apgId
		WHERE apd.apdPersona = @idPersona
			AND apd.apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE'
			AND apg.apgPeriodoAporte = @periodoEvaluacion
		
		
		IF @deudaPresunta > @aportePeriodoActual 
		BEGIN -- Indica que el independiente entra en Cartera				
			SET @estadoCartera = 'AL_DIA'
			SET @tipoDeuda = 'INEXACTITUD'
			
			IF @aportePeriodoActual = 0
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
				(	@deudaPresunta - @aportePeriodoActual,
					@estadoCartera,
					'VIGENTE',
					dbo.GetLocalDate(),
					@idPersona,
					NULL,
					@fechaActual,
					NULL,
					NULL,
					@tipoDeuda,
					'LC4',
					'INDEPENDIENTE' )
		END
		
		
		FETCH NEXT FROM @independientesCursor INTO @idPersona, @idRolAfiliado
	END
	
	CLOSE @independientesCursor
	DEALLOCATE @independientesCursor

END