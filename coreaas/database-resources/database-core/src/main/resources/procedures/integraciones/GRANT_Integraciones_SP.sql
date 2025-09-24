-- =============================================
-- Author:		Diego Suesca
-- Create date: 2018/08/22
-- Description:	Rutina para asignar permisos a objetos de programacion
-- =============================================
GRANT EXEC ON sap.USP_EjecutarIntegracionCM TO PUBLIC;
GRANT EXEC ON sap.USP_GetAcreedoresFOVIS_DocumentoId TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F16_F22 TO PUBLIC;
GRANT EXEC ON sap.MI_consultarCodigoSapAcreedores TO PUBLIC;
GRANT EXEC ON sap.MI_consultarCodigoSapClientes TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionRegistrosAcreedores TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionRegistrosClientes TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionRegistrosContables TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionRegistrosContablesDetalle TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionRegistrosContablesExport TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionTitulosContablesExport TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionRegistrosClientesExport TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionTitulosClientesExport TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionTitulosAcreedores TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionTitulosClientes TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionTitulosContables TO PUBLIC;
GRANT EXEC ON sap.MI_consultarDetalleIntegracionTitulosContablesDetalle TO PUBLIC;
GRANT EXEC ON sap.MI_consultarReferenciasContables TO PUBLIC;
GRANT EXEC ON sap.MI_consultarResumenAcreedores TO PUBLIC;

------- EJECUTAR INTEGRACION APORTES

GRANT EXEC ON sap.USP_GetICAPORTES_Insert_A01_A02 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICAPORTES_Insert_A03_A04 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICAPORTES_Insert_A05_A06 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICAPORTES_Insert_A07_A08 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICAPORTES_Insert_A09_A10 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICAPORTES_Insert_A34 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICAPORTES_Insert_A35 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICAPORTES_Insert_CORRECCION_TP TO PUBLIC;
GRANT EXEC ON sap.USP_GetICAPORTES_Insert_DEVOLUCION_TP TO PUBLIC;
GRANT EXEC ON sap.USP_Interfazcontableaportes_2 TO PUBLIC;
GRANT EXEC ON sap.USP_Interfazcontableaportes_3 TO PUBLIC;
GRANT EXEC ON sap.USP_Interfazcontableaportes_PlanillaN TO PUBLIC;
GRANT EXEC ON sap.USP_EjecutarIntegracionAportes TO PUBLIC;
GRANT EXEC ON sap.sp_GetTopICAportes TO PUBLIC;
GRANT EXEC ON sap.USP_GetlCCARTERA_A22 TO PUBLIC;
GRANT EXEC ON sap.USP_GetlCCARTERA_A26 TO PUBLIC;
GRANT EXEC ON sap.USP_GetlCCARTERA_A32 TO PUBLIC;



----EjecutarIntegracionCliente

GRANT EXEC ON sap.USP_EjecutarIntegracionCliente_G2E TO PUBLIC;
GRANT EXEC ON sap.USP_GetPersonas_Insert TO PUBLIC;
GRANT EXEC ON sap.USP_GetPersonas_DocumentoId TO PUBLIC;
GRANT EXEC ON sap.USP_Relaciones TO PUBLIC;
GRANT EXEC ON sap.USP_GetEmpresas_Insert TO PUBLIC;
GRANT EXEC ON sap.USP_GetEmpresas_DocumentoId TO PUBLIC;
GRANT EXEC ON sap.USP_Contactos_G2E TO PUBLIC;
GRANT EXEC ON sap.USP_INSERT_Contactos_G2E TO PUBLIC;
GRANT EXEC ON sap.USP_UPDATE_Contactos_G2E TO PUBLIC;



----EjecutarIntegracionCM

GRANT EXEC ON sap.USP_InterfazCM_C01 TO PUBLIC;
GRANT EXEC ON sap.USP_InterfazCM_C02 TO PUBLIC; 
GRANT EXEC ON sap.USP_InterfazCM_C05 TO PUBLIC; 
GRANT EXEC ON sap.USP_InterfazCM_C07 TO PUBLIC; 
GRANT EXEC ON sap.USP_InterfazCM_C09 TO PUBLIC; 
GRANT EXEC ON sap.USP_InterfazCM_C14 TO PUBLIC; 
GRANT EXEC ON sap.USP_InterfazCM_C16 TO PUBLIC; 
GRANT EXEC ON sap.USP_InterfazCM_C18 TO PUBLIC; 
GRANT EXEC ON sap.USP_InterfazCM_C19 TO PUBLIC; 
GRANT EXEC ON sap.USP_InterfazCM_C20 TO PUBLIC; 
GRANT EXEC ON sap.sp_GetTopICCuotaMonetaria TO PUBLIC;




---EjecutarIntegracionFovis

GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F01 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F03 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F05 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F06 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F07 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F08 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F09_F10 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F11_F12 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F13 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F14 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F16_F22 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F17_F19_F22 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F18_F19_F22 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F20 TO PUBLIC;
GRANT EXEC ON sap.USP_GetICFOVIS_Insert_F21 TO PUBLIC;
GRANT EXEC ON sap.USP_EjecutarIntegracionFovis TO PUBLIC;
GRANT EXEC ON sap.sp_GetTopICFovis TO PUBLIC;



--SP_Integracion ERP a SAP o SP_Correcciones

GRANT EXEC ON sap.USP_Correciones_Contables_Enc TO PUBLIC;
GRANT EXEC ON sap.USP_Correciones_Contables TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateTopRelaciones TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateTopPersonas TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateTopEmpresas TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateTopContactos TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateTopAcreedores TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateRelacionRespuestaSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdatePI TO PUBLIC;
GRANT EXEC ON sap.sp_UpdatePersonaRespuestaSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdatePersonaInfoSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateICFovisRespuestaSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateICCuotaMonetariaRespuestaSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateICAportesRespuestaSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateEmpresaRespuestaSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateEmpresaInfoSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateContactoRespuestaSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateContactoInfoSAP TO PUBLIC;
GRANT EXEC ON sap.sp_UpdateAcreedoresRespuestaSAP TO PUBLIC;
GRANT EXEC ON sap.sp_HomologarCodSAPGenesysDeudor TO PUBLIC;
GRANT EXEC ON sap.sp_HomologarCodSAPGenesysAcreedor TO PUBLIC;
GRANT EXEC ON sap.sp_GetTopRelaciones TO PUBLIC;
GRANT EXEC ON sap.sp_GetTopPersonas TO PUBLIC;
GRANT EXEC ON sap.sp_GetTopEmpresas TO PUBLIC;
GRANT EXEC ON sap.sp_GetTopContactos TO PUBLIC;
GRANT EXEC ON sap.sp_GetTopAcreedores TO PUBLIC;
GRANT EXEC ON sap.sp_CambioCodigoSapAcreedor TO PUBLIC;
GRANT EXEC ON sap.sp_CambioCodigoSAP TO PUBLIC;
GRANT EXEC ON sap.MI_TitulosCodigoSapClientes TO PUBLIC;
GRANT EXEC ON sap.MI_TitulosCodigoSapAcreedores TO PUBLIC;
GRANT EXEC ON sap.MI_reenviarDetalleIntegracionContables TO PUBLIC;
GRANT EXEC ON sap.MI_reenviarDetalleIntegracionClientes TO PUBLIC;
GRANT EXEC ON sap.MI_reenviarDetalleIntegracionAcreedores TO PUBLIC;
GRANT EXEC ON sap.MI_guardarreferencia TO PUBLIC;
GRANT EXEC ON sap.MI_guardarCodigoSapClientes TO PUBLIC;
GRANT EXEC ON sap.MI_guardarCodigoSapAcreedores TO PUBLIC;
GRANT EXEC ON sap.MI_eliminarDetalleIntegracionClientes TO PUBLIC;
GRANT EXEC ON sap.MI_consultarResumenGENESYStoERPClientesTotal TO PUBLIC;
GRANT EXEC ON sap.MI_consultarResumenGENESYStoERPClientes TO PUBLIC;
GRANT EXEC ON sap.MI_consultarResumenERPtoGENESYSClientesTotal TO PUBLIC;
GRANT EXEC ON sap.MI_consultarResumenERPtoGENESYSClientes TO PUBLIC;
GRANT EXEC ON sap.MI_consultarResumenContablesTotal TO PUBLIC;
GRANT EXEC ON sap.LIMPIAR_INFO_INTEGRACIONES_6_MESES TO PUBLIC;
GRANT EXEC ON sap.ASP_JSON_NOVEDADES_PERSONAS TO PUBLIC;
GRANT EXEC ON sap.ASP_JSON_NOVEDADES_EMPLEADORES TO PUBLIC;
GRANT EXEC ON sap.USP_GetAcreedoresAportes_DocumentoId TO PUBLIC;



