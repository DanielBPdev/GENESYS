--Drop Store Procedures of Reportes Normativos in Core - Xphera
IF ( Object_id( 'reporteEmpresasAportantes' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteEmpresasAportantes];
IF ( Object_id( 'reporteAfiliadosACargo' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAfiliadosACargo];
IF ( Object_id( 'reporteCuotaMonetariaNumeroPersonas' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteCuotaMonetariaNumeroPersonas];
IF ( Object_id( 'reporteCuotaMonetariaNumeroTotalCuotas' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteCuotaMonetariaNumeroTotalCuotas];
IF ( Object_id( 'reporteInactivosAfiliados' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteInactivosAfiliados];
IF ( Object_id( 'reporteMaestroAfiliados' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteMaestroAfiliados];
IF ( Object_id( 'reporteNovedadesAfiliacionAportante' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteNovedadesAfiliacionAportante];
IF ( Object_id( 'reporteNovedadesAfiliadosSubsidios' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteNovedadesAfiliadosSubsidios];
IF ( Object_id( 'reportePastulacionesyAsignacionesFOVIS' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reportePastulacionesyAsignacionesFOVIS];
IF ( Object_id( 'ReporteDetalladoTrabajadoresSectorAgropecuario' ) IS NOT NULL )
DROP PROCEDURE [dbo].[ReporteDetalladoTrabajadoresSectorAgropecuario];