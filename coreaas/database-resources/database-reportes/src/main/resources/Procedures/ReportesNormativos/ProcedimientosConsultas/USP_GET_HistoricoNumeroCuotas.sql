-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoNumeroCuotas
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoNumeroCuotas(
			hncFechaHistorico,  
		  hncCodigoDepartamento,
		  hncAnio,
		  hncValor,
		  hncFechaInicialReporte,
		  hncFechaFinalReporte)
		SELECT @fechaFin,
		dep.depCodigo,
		YEAR(dsa.dsaPeriodoLiquidado),
		SUM(dsa.dsaValorSubsidioMonetario),
		@fechaInicio,
		@fechaFin
		FROM DetalleSubsidioAsignado dsa
		LEFT JOIN Parametro p ON p.prmNombre = 'CAJA_COMPENSACION_DEPTO_ID'
		LEFT JOIN Departamento dep on dep.depCodigo = p.prmValor
		WHERE 1=1
		AND dsa.dsaFechaHoraCreacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
		AND dsa.dsaEstado = 'DERECHO_ASIGNADO'
		GROUP BY dep.depCodigo,YEAR(dsa.dsaPeriodoLiquidado)
		ORDER BY dep.depCodigo,YEAR(dsa.dsaPeriodoLiquidado)
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
