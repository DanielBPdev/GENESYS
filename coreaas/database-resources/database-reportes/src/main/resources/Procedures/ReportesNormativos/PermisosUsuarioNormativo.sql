---- Reportes----

if not exists (select * from sys.sysusers where name = 'normativos')
begin
create role normativos
end


grant select on schema::dbo to normativos;
grant select, delete, insert on object::rno.HistoricoAfiliadosACargo to normativos;
grant select, insert, delete on object::rno.HistoricoConsolidadoPagosReintegroMicroDatoFOVIS to normativos;
grant select, insert, delete on object::dbo.niving to normativos;
grant select, insert, delete on object::dbo.niving2 to normativos;
grant select, insert, delete on object::rno.HistoricoAsignacionEntregaReintegroMicrodatoFOVIS to normativos;
grant select, insert on object::rno.HistoricoCierreCartera to normativos;
grant select, insert, delete on object::rno.HistoricoConsolidadoPagosReintegroFOVIS to normativos;
grant select, insert, delete on object::rno.HistoricoAfiliados to normativos;
grant select, insert, update, delete on object::dbo.SolicitudesReportesNormativos to normativos;
grant select, insert, delete on object::dbo.Reporte227 to normativos;
grant execute on object::[dbo].[Plano_Reporte227] to normativos
grant execute on object::[dbo].[Plano_Reporte228] to normativos
grant execute on object::[dbo].[totalCarteraAvisoIncumplimiento] to normativos
grant execute on object::[dbo].[totalCarteraUbicacionYContacto] to normativos
grant execute on object::[dbo].[reporteAfiliadosACargo] to normativos
grant execute on object::[dbo].[reporteAfiliadosYAsignados_ASIGNADOS] to normativos
grant execute on object::[dbo].[ReporteAjustesenlaInformacion] to normativos
grant execute on object::[dbo].[reporteArchivoMaestroSubsidios] to normativos
grant execute on object::[dbo].[reporteAsigPagoReinFovis] to normativos
grant execute on object::[dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS] to normativos
grant execute on object::[dbo].[USP_GET_HistoricoAsignacionEntregaReintegroMicrodatoFOVIS] to normativos
grant execute on object::[dbo].[reporteAsignadosConcurrencia] to normativos
grant execute on object::[dbo].[reporteAvisoIncumplimiento] to normativos
grant execute on object::[dbo].[USP_GET_CierreCartera] to normativos
grant execute on object::[dbo].[USP_GET_HistoricoConsolidadoPagosReintegroFOVIS] to normativos
grant execute on object::[dbo].[reporteConsolidado] to normativos
grant execute on object::[dbo].[reporteDesagregado] to normativos
grant execute on object::[dbo].[USP_REP_Detallado_3_030_Circular] to normativos
grant execute on object::[dbo].[reporteDevolucionesUGPP] to normativos
grant execute on object::[dbo].[reporteEmpresasMora] to normativos
grant execute on object::[dbo].[USP_GET_EjecucionFOVIS] to normativos
grant execute on object::[dbo].[reporteRegistroUnicoDeEmpleadores] to normativos
grant execute on object::[dbo].[reporteUbicacionYContacto] to normativos
grant execute on object::[dbo].[reporteAcciondeCobro] to normativos
grant execute on object::[dbo].[reporteAfiliadosYAsignados_PERDIDA_VIVIENDA] to normativos
grant execute on object::[dbo].[reporteEmpleadoresMorosos] to normativos
grant execute on object::[dbo].[USP_GET_InformeContraloria] to normativos
grant execute on object::[dbo].[reporteTrabajadoresSectorAgropecuario] to normativos
grant execute on object::[dbo].[SP_ReporteConsolidadoAsignacionPagoReintegroMicrodatoFOVIS] to normativos
grant execute on object::[dbo].[reporteRegistroUnicoDeEmpleadores] to normativos
grant execute on object::dbo.reporteAfiliados to normativos;
grant execute on object::[dbo].[reporteAfiliadosACargo] to normativos
grant execute on object::[dbo].[reporteAfiliados] to normativos
grant execute on object::[dbo].[reporteInconsistencias] to normativos
grant execute on object::[dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS] to normativos
grant execute on object::[dbo].[USP_GET_HistoricoConsolidadoPagosReintegroFOVIS] to normativos
grant execute on object::[dbo].[SP_ReporteConsolidadoAsignacionPagoReintegroMicrodatoFOVIS] to normativos
grant execute on object::[dbo].[USP_GET_CierreCartera] to normativos
grant execute on object::[dbo].[reportePagosPorFueraDePilaUGPP] to normativos
grant execute on object::[dbo].[reporteNovedadesConcurrencia] to normativos
grant execute on object::[dbo].[reporteDesagregado] to normativos
grant execute on object::[dbo].[reporteAvisoIncumplimiento] to normativos
grant execute on object::[dbo].[reporteUbicacionYContacto] to normativos
grant execute on object::[dbo].[reporteAcciondeCobro] to normativos
grant execute on object::[dbo].[reporteArchivoMaestroSubsidios] to normativos
grant execute on object::[dbo].[reporteRegistroUnicoDeEmpleadores] to normativos
grant execute on object::[dbo].[reporteEmpresasMora] to normativos
grant execute on object::[dbo].[reporteAsigPagoReinFovis] to normativos
grant execute on object::[dbo].[USP_GET_EjecucionFOVIS] to normativos
grant execute on object::[dbo].[USP_REP_Detallado_3_030_Circular] to normativos
grant execute on object::[dbo].[reporteEmpleadoresMorosos] to normativos
grant execute on object::[dbo].[reporteDevolucionesUGPP] to normativos
grant execute on object::[dbo].[reporteTrabajadoresSectorAgropecuario] to normativos
grant execute on object::[dbo].[reporteAfiliadosYAsignados_ASIGNADOS] to normativos
grant execute on object::[dbo].[reporteAfiliadosYAsignados_AFILIADOS] to normativos
grant execute on object::[dbo].[reporteAfiliadosYAsignados_PERDIDA_VIVIENDA] to normativos
grant execute on object::[dbo].[USP_GET_InformeContraloria] to normativos
grant execute on object::[dbo].[reporteAsignadosConcurrencia] to normativos
grant execute on object::[dbo].[ReporteAjustesenlaInformacion] to normativos

grant execute on object::dbo.reporteAfiliados to normativos;
grant ALTER ANY EXTERNAL DATA SOURCE to normativos;

if not exists (select * from sys.sysusers where name = 'repNormativos')
begin
create user [repNormativos] for login [repNormativos]
end

exec sp_addrolemember 'normativos', 'repNormativos'