--Drop Store Procedures of Transversales
IF ( Object_id( 'USP_AUD_DELETE_Revision' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_AUD_DELETE_Revision];
  
IF ( Object_id( 'USP_AUD_DELETE_AudTableREV' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_AUD_DELETE_AudTableREV];

IF ( Object_id( 'USP_AUD_INSERT_MarcaProcesamiento' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_AUD_INSERT_MarcaProcesamiento];

IF ( Object_id( 'USP_AUD_DELETE_MarcaProcesamiento' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_AUD_DELETE_MarcaProcesamiento];

IF ( Object_id( 'USP_ExecuteConsultarHistoricoAfiliacionEmpleador' ) IS NOT NULL ) 
  DROP PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoAfiliacionEmpleador];

IF ( Object_id( 'USP_ExecuteConsultarHistoricoEstadoEmpleador' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoEstadoEmpleador];

IF ( Object_id( 'USP_ExecuteConsultarHistoricoIntentoAfiliacionEmpleador' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoIntentoAfiliacionEmpleador];
  
IF ( Object_id( 'USP_ExecuteConsultarHistoricoContactoPersona' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoContactoPersona];  

IF ( Object_id( 'DATOS_ESPECIFICICOS_FALLECIMIENTO_AUD' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[DATOS_ESPECIFICICOS_FALLECIMIENTO_AUD];  

IF ( Object_id( 'TRUNCATE_CORE_AUD_FALLECIMIENTO' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[TRUNCATE_CORE_AUD_FALLECIMIENTO];  
 