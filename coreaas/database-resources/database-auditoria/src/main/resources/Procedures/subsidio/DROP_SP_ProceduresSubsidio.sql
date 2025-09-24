--Drop Store Procedures of Transversales
IF ( Object_id( 'USP_GET_BeneficiariosSubsidioFechasAfiliacion' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_BeneficiariosSubsidioFechasAfiliacion];
IF ( Object_id( 'USP_GET_BeneficiariosSubsidioFechasRetiro' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_BeneficiariosSubsidioFechasRetiro];
IF ( Object_id( 'USP_GET_BeneficiariosSubsidioUltimoEstado' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_BeneficiariosSubsidioUltimoEstado];
IF ( Object_id( 'USP_GET_TrabajadorSubsidioFechasAfiliacion' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_TrabajadorSubsidioFechasAfiliacion];
IF ( Object_id( 'USP_GET_TrabajadorSubsidioFechasRetiro' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_TrabajadorSubsidioFechasRetiro];
IF ( Object_id( 'USP_GET_TrabajadorSubsidioUltimoEstado' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_TrabajadorSubsidioUltimoEstado];

