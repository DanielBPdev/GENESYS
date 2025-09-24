--Drop Store Procedures of Odoo
IF ( Object_id( 'odoo.consultasTerceros' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[consultasTerceros];
IF ( Object_id( 'odoo.ConsultaAfiliacionesEmpresas' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaAfiliacionesEmpresas];
IF ( Object_id( 'odoo.ConsultaAfiliacionesPersonas' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaAfiliacionesPersonas];
IF ( Object_id( 'odoo.ConsultaNovedadesEmpresasPersonas' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaNovedadesEmpresasPersonas];
IF ( Object_id( 'odoo.ConsultaAportesRecaudosPila' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaAportesRecaudosPila];
IF ( Object_id( 'odoo.ConsultaAportesRecaudoManual' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaAportesRecaudoManual];
IF ( Object_id( 'odoo.ConsultaDevolucionAporte' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaDevolucionAporte];
IF ( Object_id( 'odoo.ConsultaCorreccionesAportes' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaCorreccionesAportes];
IF ( Object_id( 'odoo.ConsultaPrescripcionAportes' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaPrescripcionAportes];
IF ( Object_id( 'odoo.ConsultaGestionConveniosPagosCarteraAportes' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaGestionConveniosPagosCarteraAportes];
IF ( Object_id( 'odoo.ConsultaLiquidacionPagoEspecificoSubsidioMonetario' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaLiquidacionPagoEspecificoSubsidioMonetario];
IF ( Object_id( 'odoo.ConsultaCobroCuotasSubsidioMonetario' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaCobroCuotasSubsidioMonetario];
IF ( Object_id( 'odoo.ConsultaAnulacionSubsidioLiquidado' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaAnulacionSubsidioLiquidado];
IF ( Object_id( 'odoo.ConsultaPrescripcionSubsidioMonetario' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaPrescripcionSubsidioMonetario];
IF ( Object_id( 'odoo.ConsultaAsignacionFovis' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaAsignacionFovis];
IF ( Object_id( 'odoo.ConsultaLegalizacionDesembolsoFovis' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaLegalizacionDesembolsoFovis];
IF ( Object_id( 'odoo.ConsultaNovedadesFovisPresencial' ) IS NOT NULL )
  DROP PROCEDURE [odoo].[ConsultaNovedadesFovisPresencial];
