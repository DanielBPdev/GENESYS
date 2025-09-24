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
		@dFechaHabil AS DATE

	-- se consulta el día habil de vencimiento del aporte
	SELECT @iDiaHabil = roa.roaDiaHabilVencimientoAporte
	FROM Persona per 
		INNER JOIN Afiliado afi ON afi.afiPersona = Per.perId
		INNER JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado
	WHERE perTipoIdentificacion = @sTipoID 
		AND perNumeroIdentificacion = @sNumID
		AND roaTipoAfiliado = @sTipoCotiz

	IF @iDiaHabil IS NULL
	BEGIN
		-- la persona no se encuentra afiliada
		SELECT @sTipoID as TipoID,
			@sNumID as NumID,
			'' as Nombre_Razon,
			@sTipoCotiz as TipoCotiz,
			'INACTIVOS' EstadoServicios
	END
	ELSE
	BEGIN
		-- la persona se encuentra afilada, se calcula la fecha de vencimiento de su último aporte
		-- para los independientes, el año y mes de vencimiento de su aporte es el mismo de la fecha actual

		IF @sTipoCotiz = 'PENSIONADO'
		BEGIN
			-- para los pensionados, los servicios se calculan con base en el mes anterior a la fecha actual
			SET @dFechaActual = DATEADD(MONTH, -1, @dFechaActual)
		END
		
		SET @iMes = DATEPART(MONTH, @dFechaActual) 
		SET @iAnio = DATEPART(YEAR, @dFechaActual)

		EXEC [dbo].[USP_calculoFechaDiaHabil] @iMes, @iAnio, @iDiaHabil, @dFechaHabil OUTPUT
		
		-- se comprueban el estado de la afiliacion y el estado del aporte (de acuerdo a su fecha de vencimiento)
		SELECT DISTINCT
			perTipoIdentificacion as TipoID,
			perNumeroIdentificacion as NumID,
			perRazonSocial as Nombre_Razon,
			roaTipoAfiliado as TipoCotiz,
			CASE
				WHEN apdEstadoRegistroAporteArchivo = 'OK'
					OR (dbo.GetLocalDate() < @dFechaHabil  AND roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE')
				THEN
					CASE
						WHEN isnull(roa.roaEstadoAfiliado,'INACTIVO') <> 'ACTIVO' THEN 'INACTIVOS'
						ELSE 'ACTIVOS'
					END
				ELSE 'INACTIVOS'
			END
			EstadoServicios
		FROM
			Persona per
			INNER JOIN Afiliado afi ON afi.afiPersona = Per.perId
			INNER JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado
			LEFT JOIN AporteDetallado apd ON apd.apdPersona = Per.perId AND roa.roaTipoAfiliado = apd.apdTipoCotizante
			LEFT JOIN AporteGeneral apg ON apg.apgId = apd.apdId AND apgPeriodoAporte = DATEPART(YEAR, @dFechaActual) + '-' + DATEPART(MONTH, @dFechaActual)
		WHERE
		perTipoIdentificacion = @sTipoID 
		AND perNumeroIdentificacion = @sNumID
		AND roaTipoAfiliado = @sTipoCotiz
	END

END;