-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoNumeroPersonasACargo
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	DECLARE @sql NVARCHAR(MAX),
			@minSls bigint,
			@maxSls bigint

	SELECT @minSls = MIN(dsa.dsaSolicitudLiquidacionSubsidio),
		   @maxSls = MAX(dsa.dsaSolicitudLiquidacionSubsidio)
 	FROM DetalleSubsidioAsignado dsa
 	WHERE dsa.dsaFechaHoraCreacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
		AND dsa.dsaEstado = 'DERECHO_ASIGNADO'
		and dsa.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'

	CREATE TABLE #periodosRegulares(pelSolicitudLiquidacionSubsidio BIGINT,priPeriodoRegular DATE, s1 VARCHAR(500))

		SET @sql = 'SELECT pel.pelSolicitudLiquidacionSubsidio,pri.priPeriodo
				FROM PeriodoLiquidacion pel
				INNER JOIN Periodo pri ON pri.priId = pel.pelPeriodo
				WHERE pel.pelTipoPeriodo = ''REGULAR''
				  AND pel.pelSolicitudLiquidacionSubsidio BETWEEN @minSls AND @maxSls'

	INSERT INTO #periodosRegulares (pelSolicitudLiquidacionSubsidio,priPeriodoRegular, s1)
	EXEC sp_execute_remote CoreReferenceData,
	@sql,
	N'@minSls bigint, @maxSls bigint',
	@minSls = @minSls,@maxSls = @maxSls

	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoNumeroPersonasACargo(
			hpcFechaHistorico,
		  hpcCodigoDepartamento,
		  hpcNumeroPersonasMes,
		  hpcNumeroPersonasRetroactivo,
		  hpcFechaInicialReporte,
		  hpcFechaFinalReporte)
		SELECT @fechaFin,
				dep.depCodigo,
				count(distinct CASE WHEN dsa.dsaPeriodoLiquidado = pre.priPeriodoRegular THEN dsa.dsaBeneficiarioDetalle  ELSE NULL END),
				count(CASE WHEN dsa.dsaPeriodoLiquidado < pre.priPeriodoRegular THEN dsa.dsaBeneficiarioDetalle ELSE NULL END),
				@fechaInicio,
				@fechaFin
		FROM DetalleSubsidioAsignado dsa
		INNER JOIN #periodosRegulares pre ON pre.pelSolicitudLiquidacionSubsidio = dsa.dsaSolicitudLiquidacionSubsidio
		LEFT JOIN Parametro p ON p.prmNombre = 'CAJA_COMPENSACION_DEPTO_ID'
		LEFT JOIN Departamento dep on dep.depCodigo = p.prmValor

		WHERE 1=1
		AND dsa.dsaFechaHoraCreacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
		AND dsa.dsaEstado = 'DERECHO_ASIGNADO'
		and dsa.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
		GROUP BY dep.depCodigo
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
