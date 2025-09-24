-- =============================================
-- Author:		Andres Julian Rocha Cruz y Alfonso Baquero Echeverry
-- Create date: 2017/11/17
-- Description:	Estado de los servicios de los afiliados (solo independientes y pensionados)
-- =============================================
CREATE PROCEDURE [dbo].[servicios]
	@sTipoID VARCHAR(20),
	@sNumID VARCHAR(16),
	@sTipoCotiz VARCHAR(30)
AS
BEGIN

	DECLARE @dFechaActual DATE = dbo.GetLocalDate(), 
		@iMes AS INT,
		@iAnio AS INT,
		@iDiaHabil AS SMALLINT,
		@dFechaHabil AS DATE,
		@sNombreAportante VARCHAR(200),
		@iIdRegistroAportante BIGINT, 
		@iIdRegGeneralAporte BIGINT,
		@sEstadoAfiliado VARCHAR(8), 
		@sEstadoAporte VARCHAR(60), 
		@sEstadoServicios VARCHAR(10)

	-- se consulta el día habil de vencimiento del aporte
	SELECT @iDiaHabil = roa.roaDiaHabilVencimientoAporte,
		@sNombreAportante = CASE WHEN per.perPrimerNombre IS NULL THEN per.perRazonSocial
		ELSE (
			per.perPrimerNombre + 
			CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre END +
			per.perPrimerApellido + 
			CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
		) END, 
		@iIdRegistroAportante = per.perId,
		@sEstadoAfiliado = roa.roaEstadoAfiliado
	FROM Persona per 
		LEFT JOIN Afiliado afi ON afi.afiPersona = Per.perId
		LEFT JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado
	WHERE perTipoIdentificacion = @sTipoID 
		AND perNumeroIdentificacion = @sNumID
		AND (roaId IS NULL OR roaTipoAfiliado = @sTipoCotiz)

	IF @sNombreAportante IS NULL
	BEGIN
		SET @sNombreAportante = 'PERSONA NO IDENTIFICADA'
	END

	print '@iDiaHabil'
	print @iDiaHabil
	print '@sNombreAportante'
	print @sNombreAportante
	print '@iIdRegistroAportante'
	print @iIdRegistroAportante

	print '------------------------------------------------'

	IF @iDiaHabil IS NULL
	BEGIN
		-- la persona no se encuentra afiliada
		SET @sEstadoServicios = 'INACTIVOS'
	END
	ELSE
	BEGIN
		-- la persona se encuentra afilada, se calcula la fecha de vencimiento de su último aporte
		-- para los independientes, el año y mes de vencimiento de su aporte es el mismo de la fecha actual
		DECLARE @sPeriodo VARCHAR(7)
		
		SET @iMes = DATEPART(MONTH, @dFechaActual) 
		SET @iAnio = DATEPART(YEAR, @dFechaActual)

		IF @sTipoCotiz = 'PENSIONADO'
		BEGIN
			-- para los pensionados, los servicios se calculan con base en el mes anterior a la fecha actual
			SET @dFechaActual = DATEADD(MONTH, -1, @dFechaActual)
		END
        ELSE
        BEGIN
            -- para los independientes, se consulta el tipo de cotizante desde PILA
            DECLARE @iTipoCotizantePILA SMALLINT

            EXEC USP_UTIL_GET_TipoCotizanteAportanteIndependiente @sTipoID, @sNumID, @iTipoCotizantePILA OUTPUT

            IF ISNULL(@iTipoCotizantePILA, 0) = 57
            BEGIN
                -- para los independientes con tipo cotizante 57, los servicios se calculan con base en el mes anterior a la fecha actual
                SET @dFechaActual = DATEADD(MONTH, -1, @dFechaActual)
            END
        END
			
		SET @sPeriodo = CAST(@dFechaActual AS VARCHAR(7))

		EXEC [dbo].[USP_calculoFechaDiaHabil] @iMes, @iAnio, @iDiaHabil, @dFechaHabil OUTPUT

		print '@dFechaHabil'
		print @dFechaHabil
		print '@sPeriodo'
		print @sPeriodo
		
		-- se comprueban el estado de la afiliacion y el estado del aporte (de acuerdo a su fecha de vencimiento)

		SELECT @sEstadoAporte = apd.apdEstadoRegistroAporteArchivo, 
			@iIdRegGeneralAporte = apg.apgRegistroGeneral 
		FROM dbo.AporteDetallado apd
		INNER JOIN dbo.AporteGeneral apg ON apd.apdAporteGeneral = apg.apgId
		WHERE apd.apdPersona = @iIdRegistroAportante
		AND apd.apdTipoCotizante = @sTipoCotiz
		AND apg.apgPeriodoAporte = @sPeriodo
		AND apg.apgFechaRecaudo <= @dFechaHabil
		
		print '------------------------------------------------'

		print '@sEstadoAfiliado'
		print @sEstadoAfiliado
		print '@sEstadoAporte'
		print @sEstadoAporte
		print '@iIdRegGeneralAporte'
		print @iIdRegGeneralAporte
		
		-- sí el aporte no es OK, se evalua la fase 1 de PILA 2
		IF ISNULL(@sEstadoAporte, '') != 'OK'
		BEGIN
			print 'Se evalua aporte según fase 1 de PILA 2 para actualizar datos afiliación'
		END
		
		IF ISNULL(@sEstadoAfiliado, '') = 'ACTIVO' AND ISNULL(@sEstadoAporte, '') = 'OK'
		BEGIN
			SET @sEstadoServicios = 'ACTIVOS'
		END
		ELSE
		BEGIN
			SET @sEstadoServicios = 'INACTIVOS'
		END
	END

	SELECT @sTipoID as TipoID,
		@sNumID as NumID,
		@sNombreAportante as Nombre_Razon,
		@sTipoCotiz as TipoCotizanteConsulta,
		roaEstadoAfiliado as estadoAfiliacionCaja,
		@sEstadoServicios EstadoServicios
	FROM VW_EstadoAfiliacionPersonaCaja
	WHERE perTipoIdentificacion = @sTipoID
	AND perNumeroIdentificacion = @sNumID
END;