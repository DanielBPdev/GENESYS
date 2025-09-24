--Drop Store Procedures of HU400
IF ( Object_id( 'USP_GetNotificacionesRegistro' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GetNotificacionesRegistro];

IF ( Object_id( 'USP_GetNotificacionesRegistroDetallado' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GetNotificacionesRegistroDetallado];
  
IF ( Object_id( 'USP_GetNotificacionesRegistroEspecial' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GetNotificacionesRegistroEspecial];