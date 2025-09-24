-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/05/08
-- Description:	Procedimiento almacenado para verificar sí un cotizante ha causado
-- subsidio monetario en un período específico
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_GET_CotizanteConSubsidioPeriodo]
	@sTipoDocumentoCotizante VARCHAR(20), 
	@sNumeroIdentificacionCotizante VARCHAR(16),
	@sPeriodoAporte VARCHAR(7),
	@bTieneSubsidio BIT OUTPUT
AS
BEGIN
	print 'Inicia USP_SM_GET_CotizanteConSubsidioPeriodo'

	SELECT @bTieneSubsidio = CAST 
	(CASE WHEN EXISTS (
		SELECT per.perId
		FROM dbo.Persona per
		INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId
		INNER JOIN dbo.DetalleSubsidioAsignado dsa ON dsa.dsaAfiliadoPrincipal = afi.afiId
		WHERE per.perTipoIdentificacion = @sTipoDocumentoCotizante
			AND per.perNumeroIdentificacion = @sNumeroIdentificacionCotizante
			AND CONVERT(VARCHAR(7), dsa.dsaPeriodoLiquidado, 121) = @sPeriodoAporte
		UNION
		SELECT per.perId
		FROM dbo.Persona per
		INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId
		INNER JOIN dbo.DetalleSubsidioAsignadoProgramado dpr ON dpr.dprAfiliadoPrincipal = afi.afiId
		WHERE per.perTipoIdentificacion = @sTipoDocumentoCotizante
			AND per.perNumeroIdentificacion = @sNumeroIdentificacionCotizante
			AND CONVERT(VARCHAR(7), dpr.dprPeriodoLiquidado, 121) = @sPeriodoAporte) 
	THEN 1 ELSE 0 END AS BIT)

	print 'Finaliza USP_SM_GET_CotizanteConSubsidioPeriodo'
END ; 