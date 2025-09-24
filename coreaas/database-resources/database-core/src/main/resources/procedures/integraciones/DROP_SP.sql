--Drop Store Procedures

IF ( Object_id( 'USP_GetAcreedoresFOVIS_DocumentoId' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetAcreedoresFOVIS_DocumentoId];
IF ( Object_id( 'USP_GetICFOVIS_Insert_F16_F22' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F16_F22];
IF ( Object_id( 'sap.MI_consultarCodigoSapAcreedores' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarCodigoSapAcreedores];
IF ( Object_id( 'sap.MI_consultarCodigoSapClientes' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarCodigoSapClientes];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionRegistrosAcreedores' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosAcreedores];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionRegistrosClientes' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosClientes];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionRegistrosContables' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosContables];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionRegistrosContablesDetalle' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosContablesDetalle];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionRegistrosContablesExport' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosContablesExport];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionTitulosContablesExport' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosContablesExport];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionRegistrosClientesExport' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosClientesExport];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionTitulosClientesExport' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosClientesExport];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionTitulosAcreedores' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosAcreedores];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionTitulosClientes' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosClientes];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionTitulosContables' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosContables];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionTitulosContables' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosContablesDetalle];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionTitulosContables' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarReferenciasContables];
IF ( Object_id( 'sap.MI_consultarDetalleIntegracionTitulosContables' ) IS NOT NULL )	DROP PROCEDURE [sap].[MI_consultarResumenAcreedores];


---- Ejecutar Integracion Aportes
IF ( Object_id( 'USP_GetICAPORTES_Insert_A01_A02') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICAPORTES_Insert_A01_A02]; 
IF ( Object_id( 'USP_GetICAPORTES_Insert_A03_A04') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICAPORTES_Insert_A03_A04]; 
IF ( Object_id( 'USP_GetICAPORTES_Insert_A05_A06') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICAPORTES_Insert_A05_A06]; 
IF ( Object_id( 'USP_GetICAPORTES_Insert_A07_A08') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICAPORTES_Insert_A07_A08]; 
IF ( Object_id( 'USP_GetICAPORTES_Insert_A09_A10') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICAPORTES_Insert_A09_A10];
IF ( Object_id( 'USP_GetICAPORTES_Insert_A34') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICAPORTES_Insert_A34];
IF ( Object_id( 'USP_GetICAPORTES_Insert_A35') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICAPORTES_Insert_A35];
IF ( Object_id( 'USP_GetICAPORTES_Insert_CORRECCION_TP') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICAPORTES_Insert_CORRECCION_TP];
IF ( Object_id( 'USP_GetICAPORTES_Insert_DEVOLUCION_TP') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICAPORTES_Insert_DEVOLUCION_TP];
IF ( Object_id( 'USP_Interfazcontableaportes_2') IS NOT NULL )	DROP PROCEDURE [sap].[USP_Interfazcontableaportes_2];
IF ( Object_id( 'USP_Interfazcontableaportes_3') IS NOT NULL )	DROP PROCEDURE [sap].[USP_Interfazcontableaportes_3];
IF ( Object_id( 'USP_Interfazcontableaportes_PlanillaN') IS NOT NULL )	DROP PROCEDURE [sap].[USP_Interfazcontableaportes_PlanillaN];
IF ( Object_id( 'USP_EjecutarIntegracionAportes') IS NOT NULL )	DROP PROCEDURE [sap].[USP_EjecutarIntegracionAportes];
IF ( Object_id( 'sap.sp_GetTopICAportes' ) IS NOT NULL )	DROP PROCEDURE [sap].[sp_GetTopICAportes];
IF ( Object_id( 'sap.USP_GetAcreedoresAportes_DocumentoId' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetAcreedoresAportes_DocumentoId];
IF ( Object_id( 'USP_GetlCCARTERA_A22' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetlCCARTERA_A22];
IF ( Object_id( 'USP_GetlCCARTERA_A26' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetlCCARTERA_A26];
IF ( Object_id( 'USP_GetlCCARTERA_A32' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetlCCARTERA_A32];


--- EjecutarIntegracionCM
IF ( Object_id( 'sap.USP_InterfazCM_C01' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C01];
IF ( Object_id( 'sap.USP_InterfazCM_C02' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C02]; 
IF ( Object_id( 'sap.USP_InterfazCM_C05' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C05]; 
IF ( Object_id( 'sap.USP_InterfazCM_C07' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C07]; 
IF ( Object_id( 'sap.USP_InterfazCM_C09' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C09]; 
IF ( Object_id( 'sap.USP_InterfazCM_C14' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C14]; 
IF ( Object_id( 'sap.USP_InterfazCM_C16' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C16]; 
IF ( Object_id( 'sap.USP_InterfazCM_C18' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C18]; 
IF ( Object_id( 'sap.USP_InterfazCM_C19' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C19]; 
IF ( Object_id( 'sap.USP_InterfazCM_C20' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_InterfazCM_C20]; 
IF ( Object_id( 'sap.USP_EjecutarIntegracionCM' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_EjecutarIntegracionCM];
IF ( Object_id( 'sap.sp_GetTopICCuotaMonetaria' ) IS NOT NULL )	DROP PROCEDURE [sap].[sp_GetTopICCuotaMonetaria];

----EjecutarIntegracionCliente
IF ( Object_id( 'sap.USP_GetPersonas_Insert') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetPersonas_Insert];
IF ( Object_id( 'sap.USP_EjecutarIntegracionCliente_G2E') IS NOT NULL )	DROP PROCEDURE [sap].[USP_EjecutarIntegracionCliente_G2E];
IF ( Object_id( 'sap.USP_GetPersonas_DocumentoId') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetPersonas_DocumentoId];
IF ( Object_id( 'sap.USP_Relaciones') IS NOT NULL )	DROP PROCEDURE [sap].[USP_Relaciones];
IF ( Object_id( 'sap.USP_GetEmpresas_Insert') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetEmpresas_Insert];
IF ( Object_id( 'sap.USP_GetEmpresas_DocumentoId') IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetEmpresas_DocumentoId];
IF ( Object_id( 'sap.USP_Contactos_G2E') IS NOT NULL )	DROP PROCEDURE [sap].[USP_Contactos_G2E];
IF ( Object_id( 'sap.USP_INSERT_Contactos_G2E') IS NOT NULL )	DROP PROCEDURE [sap].[USP_INSERT_Contactos_G2E];
IF ( Object_id( 'sap.USP_UPDATE_Contactos_G2E') IS NOT NULL )	DROP PROCEDURE [sap].[USP_UPDATE_Contactos_G2E];



---EjecutarIntegracionFovis

IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F01' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F01];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F03' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F03];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F05' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F05];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F06' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F06];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F07' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F07];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F08' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F08];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F09_F10' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F09_F10];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F11_F12' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F11_F12];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F13' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F13];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F14' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F14];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F16_F22' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F16_F22];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F17_F19_F22' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F17_F19_F22];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F18_F19_F22' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F18_F19_F22];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F20' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F20];
IF ( Object_id( 'sap.USP_GetICFOVIS_Insert_F21' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_GetICFOVIS_Insert_F21];
IF ( Object_id( 'sap.USP_EjecutarIntegracionFovis' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_EjecutarIntegracionFovis];
IF ( Object_id( 'sap.sp_GetTopICFovis' ) IS NOT NULL )	DROP PROCEDURE [sap].[sp_GetTopICFovis];


--SP_Integracion ERP a GENESYS o SP_Correcciones

IF ( Object_id( 'sap.USP_Correciones_Contables_Enc' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_Correciones_Contables_Enc];
IF ( Object_id( 'sap.USP_Correciones_Contables' ) IS NOT NULL )	DROP PROCEDURE [sap].[USP_Correciones_Contables];
IF ( Object_id( 'sap.sp_UpdateTopRelaciones' ) IS NOT NULL )	DROP PROCEDURE [sap].[sp_UpdateTopRelaciones];
IF ( Object_id( 'sap.sp_UpdateTopPersonas' ) IS NOT NULL )	DROP PROCEDURE [sap].[sp_UpdateTopPersonas];
IF ( Object_id( 'sap.sp_UpdateTopEmpresas' ) IS NOT NULL )	DROP PROCEDURE [sap].[sp_UpdateTopEmpresas];
IF ( Object_id( 'sap.sp_UpdateTopContactos' ) IS NOT NULL )	DROP PROCEDURE [sap].[sp_UpdateTopContactos];
IF ( Object_id( 'sap.sp_UpdateTopAcreedores' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateTopAcreedores];
IF ( Object_id( 'sap.sp_UpdateRelacionRespuestaSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateRelacionRespuestaSAP];
IF ( Object_id( 'sap.sp_UpdatePI' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdatePI];
IF ( Object_id( 'sap.sp_UpdatePersonaRespuestaSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdatePersonaRespuestaSAP];
IF ( Object_id( 'sap.sp_UpdatePersonaInfoSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdatePersonaInfoSAP];
IF ( Object_id( 'sap.sp_UpdateICFovisRespuestaSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateICFovisRespuestaSAP];
IF ( Object_id( 'sap.sp_UpdateICCuotaMonetariaRespuestaSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateICCuotaMonetariaRespuestaSAP];
IF ( Object_id( 'sap.sp_UpdateICAportesRespuestaSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateICAportesRespuestaSAP];
IF ( Object_id( 'sap.sp_UpdateEmpresaRespuestaSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateEmpresaRespuestaSAP];
IF ( Object_id( 'sap.sp_UpdateEmpresaInfoSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateEmpresaInfoSAP];
IF ( Object_id( 'sap.sp_UpdateContactoRespuestaSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateContactoRespuestaSAP];
IF ( Object_id( 'sap.sp_UpdateContactoInfoSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateContactoInfoSAP];
IF ( Object_id( 'sap.sp_UpdateAcreedoresRespuestaSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_UpdateAcreedoresRespuestaSAP];
IF ( Object_id( 'sap.sp_HomologarCodSAPGenesysDeudor' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_HomologarCodSAPGenesysDeudor];
IF ( Object_id( 'sap.sp_HomologarCodSAPGenesysAcreedor' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_HomologarCodSAPGenesysAcreedor];
IF ( Object_id( 'sap.sp_GetTopRelaciones' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_GetTopRelaciones];
IF ( Object_id( 'sap.sp_GetTopPersonas' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_GetTopPersonas];
IF ( Object_id( 'sap.sp_GetTopEmpresas' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_GetTopEmpresas];
IF ( Object_id( 'sap.sp_GetTopContactos' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_GetTopContactos];
IF ( Object_id( 'sap.sp_GetTopAcreedores' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_GetTopAcreedores];
IF ( Object_id( 'sap.sp_CambioCodigoSapAcreedor' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_CambioCodigoSapAcreedor];
IF ( Object_id( 'sap.sp_CambioCodigoSAP' ) IS NOT NULL ) DROP PROCEDURE [sap].[sp_CambioCodigoSAP];
IF ( Object_id( 'sap.MI_TitulosCodigoSapClientes' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_TitulosCodigoSapClientes];
IF ( Object_id( 'sap.MI_TitulosCodigoSapAcreedores' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_TitulosCodigoSapAcreedores];
IF ( Object_id( 'sap.MI_reenviarDetalleIntegracionContables' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_reenviarDetalleIntegracionContables];
IF ( Object_id( 'sap.MI_reenviarDetalleIntegracionClientes' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_reenviarDetalleIntegracionClientes];
IF ( Object_id( 'sap.MI_reenviarDetalleIntegracionAcreedores' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_reenviarDetalleIntegracionAcreedores];
IF ( Object_id( 'sap.MI_guardarreferencia' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_guardarreferencia];
IF ( Object_id( 'sap.MI_guardarCodigoSapClientes' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_guardarCodigoSapClientes];
IF ( Object_id( 'sap.MI_guardarCodigoSapAcreedores' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_guardarCodigoSapAcreedores];
IF ( Object_id( 'sap.MI_eliminarDetalleIntegracionClientes' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_eliminarDetalleIntegracionClientes];
IF ( Object_id( 'sap.MI_consultarResumenGENESYStoERPClientesTotal' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_consultarResumenGENESYStoERPClientesTotal];
IF ( Object_id( 'sap.MI_consultarResumenGENESYStoERPClientes' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_consultarResumenGENESYStoERPClientes];
IF ( Object_id( 'sap.MI_consultarResumenERPtoGENESYSClientesTotal' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_consultarResumenERPtoGENESYSClientesTotal];
IF ( Object_id( 'sap.MI_consultarResumenERPtoGENESYSClientes' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_consultarResumenERPtoGENESYSClientes];
IF ( Object_id( 'sap.MI_consultarResumenContablesTotal' ) IS NOT NULL ) DROP PROCEDURE [sap].[MI_consultarResumenContablesTotal];
IF ( Object_id( 'sap.LIMPIAR_INFO_INTEGRACIONES_6_MESES' ) IS NOT NULL ) DROP PROCEDURE [sap].[LIMPIAR_INFO_INTEGRACIONES_6_MESES];
IF ( Object_id( 'sap.ASP_JSON_NOVEDADES_PERSONAS' ) IS NOT NULL ) DROP PROCEDURE [sap].[ASP_JSON_NOVEDADES_PERSONAS];
IF ( Object_id( 'sap.ASP_JSON_NOVEDADES_EMPLEADORES' ) IS NOT NULL ) DROP PROCEDURE [sap].[ASP_JSON_NOVEDADES_EMPLEADORES];







