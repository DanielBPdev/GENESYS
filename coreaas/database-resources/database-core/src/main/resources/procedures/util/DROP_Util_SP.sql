--Drop Store Procedures 
IF ( Object_id( 'USP_AUD_DELETE_BufferAuditoria' ) IS NOT NULL )
	 DROP PROCEDURE [dbo].[USP_AUD_DELETE_BufferAuditoria];

IF ( Object_id( 'USP_AUD_UTIL_TableList' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_AUD_UTIL_TableList];

IF ( Object_id( 'USP_UTIL_GET_TipoCotizanteAportanteIndependiente' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_GET_TipoCotizanteAportanteIndependiente];

IF ( Object_id( 'USP_UTIL_GET_HistoricoAsignacionFOVIS' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_GET_HistoricoAsignacionFOVIS];
	
IF ( Object_id( 'USP_UTIL_GET_CrearRevision' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_GET_CrearRevision];

IF ( Object_id( 'USP_ExecuteConsultarHistoricoContactoPersona' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoContactoPersona];

IF ( Object_id( 'USP_UTIL_GET_CrearRevisionActualizar' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_GET_CrearRevisionActualizar];

IF ( Object_id( 'USP_UTIL_CURSOR_AVI_INC' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_AVI_INC];

IF ( Object_id( 'USP_UTIL_CURSOR_AVI_INC_PER' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_AVI_INC_PER];

IF ( Object_id( 'USP_UTIL_CURSOR_CAR_EMP_EXP' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_CAR_EMP_EXP];

IF ( Object_id( 'USP_UTIL_CURSOR_CAR_PER_EXP' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_CAR_PER_EXP];

IF ( Object_id( 'USP_UTIL_CURSOR_CIT_NTF_PER' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_CIT_NTF_PER];

IF ( Object_id( 'USP_UTIL_CURSOR_LIQ_APO_MOR' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_LIQ_APO_MOR];

IF ( Object_id( 'USP_UTIL_CURSOR_NTF_AVI' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_NTF_AVI];

IF ( Object_id( 'USP_UTIL_CURSOR_PRI_AVI_COB_PRS' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_PRI_AVI_COB_PRS];

IF ( Object_id( 'USP_UTIL_CURSOR_SEG_AVI_COB_PRS' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_SEG_AVI_COB_PRS];

IF ( Object_id( 'USP_UTIL_CURSOR_SUS_NTF_NO_PAG__NTF_NO_REC_APO' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_CURSOR_SUS_NTF_NO_PAG__NTF_NO_REC_APO];

IF ( Object_id( 'USP_UTIL_PregenerarComunicadosConsolidadoCartera' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_UTIL_PregenerarComunicadosConsolidadoCartera];

IF ( Object_id( 'USP_GET_Consultar_Estado_Afiliado_Consulta_En_Linea_Confa' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_GET_Consultar_Estado_Afiliado_Consulta_En_Linea_Confa];

IF ( Object_id( 'USP_GET_Consultar_Estado_Beneficiario_Consulta_En_Linea_Confa' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_GET_Consultar_Estado_Beneficiario_Consulta_En_Linea_Confa];

IF ( Object_id( 'ASP_ConsultarTrabajadoresNoAfiliadosEmpleador' ) IS NOT NULL )	DROP PROCEDURE [dbo].[ASP_ConsultarTrabajadoresNoAfiliadosEmpleador];

IF ( Object_id( 'SP_DesistirSolicitudesAfiliacion' ) IS NOT NULL )	DROP PROCEDURE [dbo].[SP_DesistirSolicitudesAfiliacion];

IF ( Object_id( 'SP_ConsultarInfoPersona' ) IS NOT NULL )	DROP PROCEDURE [dbo].[SP_ConsultarInfoPersona];

IF ( Object_id( 'SP_ConsultarInfoBeneficiario' ) IS NOT NULL )	DROP PROCEDURE [dbo].[SP_ConsultarInfoBeneficiario];

IF ( Object_id( 'ASP_JsonPersona' ) IS NOT NULL )	DROP PROCEDURE [dbo].[ASP_JsonPersona];

IF ( Object_id( 'ASP_JsonValidacionesCargueMultipleWeb' ) IS NOT NULL )	DROP PROCEDURE [dbo].[ASP_JsonValidacionesCargueMultipleWeb];

IF ( Object_id( 'ASP_JsonCruceAportes' ) IS NOT NULL )	DROP PROCEDURE [dbo].[ASP_JsonCruceAportes];

IF ( Object_id( 'SP_HistoricoAfiliacionPersona' ) IS NOT NULL )	DROP PROCEDURE [dbo].[SP_HistoricoAfiliacionPersona];

IF ( Object_id( 'SP_CalcularTiempoDesistirSolicitud' ) IS NOT NULL )	DROP PROCEDURE [dbo].[SP_CalcularTiempoDesistirSolicitud];