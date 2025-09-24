--Drop Store Procedures of Transversal
IF ( Object_id( 'USP_REP_GenerarRangoFechasReporte' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_GenerarRangoFechasReporte];
IF ( Object_id( 'USP_REP_CargarReportesNormativos' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_CargarReportesNormativos];
IF ( Object_id( 'USP_GET_HistoricoDesagregadoCarteraAportante' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_HistoricoDesagregadoCarteraAportante];
IF ( Object_id( 'USP_GET_HistoricoEmpresasAportantes' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_HistoricoEmpresasAportantes];
IF ( Object_id( 'USP_GET_HistoricoAvisoIncumplimiento' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoAvisoIncumplimiento];
IF ( Object_id( 'USP_GET_HistoricoAfiliadosACargo' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoAfiliadosACargo];
IF ( Object_id( 'USP_GET_HistoricoAfiliadosVivienda' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoAfiliadosVivienda];
IF ( Object_id( 'USP_GET_HistoricoEmpresasMora' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoEmpresasMora];
IF ( Object_id( 'USP_GET_HistoricoUbicacionYContacto' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoUbicacionYContacto];
IF ( Object_id( 'USP_GET_HistoricoConsolidadoCartera' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoConsolidadoCartera];
IF ( Object_id( 'USP_GET_HistoricoDevolucionesUGPP' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoDevolucionesUGPP];
IF ( Object_id( 'USP_GET_HistoricoRegistroUnicoEmpleadores' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoRegistroUnicoEmpleadores];
IF ( Object_id( 'USP_GET_HistoricoMaestroAfiliados' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoMaestroAfiliados];
IF ( Object_id( 'USP_GET_HistoricoArchivoMaestroSubsidios' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoArchivoMaestroSubsidios];
IF ( Object_id( 'USP_GET_HistoricoNovedadesAfiliadosYSubsidios' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoNovedadesAfiliadosYSubsidios];
IF ( Object_id( 'USP_GET_HistoricoNovedadesEstadoAfiliacion' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoNovedadesEstadoAfiliacion];
IF ( Object_id( 'USP_GET_HistoricoAfiliados' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoAfiliados];
IF ( Object_id( 'USP_GET_HistoricoAsignacionEntregaReintegroFOVIS' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoAsignacionEntregaReintegroFOVIS];
IF ( Object_id( 'USP_GET_HistoricoPagoPorFueraPila' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoPagoPorFueraPila];
IF ( Object_id( 'USP_GET_HistoricoInconsistenciasUGPP' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoInconsistenciasUGPP];
IF ( Object_id( 'USP_GET_HistoricoEmpleadoresMorosos' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoEmpleadoresMorosos];
IF ( Object_id( 'USP_GET_HistoricoAfiliadosBeneficiariosFOVIS' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoAfiliadosBeneficiariosFOVIS];
IF ( Object_id( 'USP_GET_HistoricoNumeroCuotas' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoNumeroCuotas];
IF ( Object_id( 'USP_GET_HistoricoNumeroPersonasACargo' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoNumeroPersonasACargo];
IF ( Object_id( 'USP_GET_HistoricoAportantesProcesoEnUnidad' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoAportantesProcesoEnUnidad];
IF ( Object_id( 'USP_GET_HistoricoSeguimientosTrasladosMora' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoSeguimientosTrasladosMora];
IF ( Object_id( 'USP_GET_HistoricoConsolidadoPagosReintegroFOVIS' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroFOVIS];
IF ( Object_id( 'USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS];
IF ( Object_id( 'USP_GET_HistoricoAsignacionEntregaReintegroMicrodatoFOVIS' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_HistoricoAsignacionEntregaReintegroMicrodatoFOVIS];

--Drop Store Procedures of Reportes Normativos Xphera
IF ( Object_id( 'creaHistoricoReportesNormativos' ) IS NOT NULL )
DROP PROCEDURE [dbo].[creaHistoricoReportesNormativos];
IF ( Object_id( 'consultaHistoricoReportesNormativos' ) IS NOT NULL )
DROP PROCEDURE [dbo].[consultaHistoricoReportesNormativos];
IF ( Object_id( 'reporteAfiliados' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAfiliados];
IF ( Object_id( 'reporteAfiliadosYAsignados_AFILIADOS' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAfiliadosYAsignados_AFILIADOS];
IF ( Object_id( 'reporteAfiliadosYAsignados_ASIGNADOS' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAfiliadosYAsignados_ASIGNADOS];
IF ( Object_id( 'reporteAfiliadosYAsignados_PERDIDA_VIVIENDA' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAfiliadosYAsignados_PERDIDA_VIVIENDA];
IF ( Object_id( 'reporteArchivoMaestroSubsidios' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteArchivoMaestroSubsidios];
IF ( Object_id( 'reporteAsigPagoReinFovis' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAsigPagoReinFovis];
IF ( Object_id( 'reporteAvisoIncumplimiento' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAvisoIncumplimiento];
IF ( Object_id( 'reporteConsolidado' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteConsolidado];
IF ( Object_id( 'reporteConsolidadoAsignacionPagoYReintegroDeSubsidiosViviendasFOVIS_microdato' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteConsolidadoAsignacionPagoYReintegroDeSubsidiosViviendasFOVIS_microdato];
IF ( Object_id( 'reporteConsolidadoAsigPagoReinFovis' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteConsolidadoAsigPagoReinFovis];
IF ( Object_id( 'reporteDesagregado' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteDesagregado];
IF ( Object_id( 'reporteDevolucionesUGPP' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteDevolucionesUGPP];
IF ( Object_id( 'reporteEmpleadoresMorosos' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteEmpleadoresMorosos];
IF ( Object_id( 'reporteEmpresasMora' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteEmpresasMora];
IF ( Object_id( 'reporteInconsistencias' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteInconsistencias];
IF ( Object_id( 'reportePagosPorFueraDePilaUGPP' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reportePagosPorFueraDePilaUGPP];
IF ( Object_id( 'reporteRegistroUnicoDeEmpleadores' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteRegistroUnicoDeEmpleadores];
IF ( Object_id( 'reporteAsignacionPagoYReintegroDeSubsidiosViviendasFOVIS_microdato' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAsignacionPagoYReintegroDeSubsidiosViviendasFOVIS_microdato];
IF ( Object_id( 'reporteTrabajadoresSectorAgropecuario' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteTrabajadoresSectorAgropecuario];
IF ( Object_id( 'reporteUbicacionYContacto' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteUbicacionYContacto];
IF ( Object_id( 'totalCarteraAvisoIncumplimiento' ) IS NOT NULL )
DROP PROCEDURE [dbo].[totalCarteraAvisoIncumplimiento];
IF ( Object_id( 'reporteAsignadosConcurrencia' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAsignadosConcurrencia];
IF ( Object_id( 'reporteNovedadesConcurrencia' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteNovedadesConcurrencia];
IF ( Object_id( 'totalCarteraUbicacionYContacto' ) IS NOT NULL )
DROP PROCEDURE [dbo].[totalCarteraUbicacionYContacto];
IF ( Object_id( 'reporteAcciondeCobro' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAcciondeCobro];
IF ( Object_id( 'SP_ReporteSubsidiosFovis' ) IS NOT NULL )
DROP PROCEDURE [dbo].[SP_ReporteSubsidiosFovis];
IF ( Object_id( 'ReporteAjustesenlaInformacion' ) IS NOT NULL )
DROP PROCEDURE [dbo].[ReporteAjustesenlaInformacion];
IF ( Object_id( 'reporteAfiliadosACargo' ) IS NOT NULL )
DROP PROCEDURE [dbo].[reporteAfiliadosACargo];
IF ( Object_id( 'USP_GET_EjecucionFOVIS' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_EjecucionFOVIS];
IF ( Object_id( 'USP_GET_CierreCartera' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_GET_CierreCartera];
IF ( Object_id( 'USP_REP_Detallado_3_030_Circular' ) IS NOT NULL )
DROP PROCEDURE [dbo].[USP_REP_Detallado_3_030_Circular];
--==================================
--==================================