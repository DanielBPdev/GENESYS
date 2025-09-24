--Drop Store Procedures 
IF ( Object_id( 'USP_SM_GET_CondicionesTrabajadorFallecido_trabajador' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_CondicionesTrabajadorFallecido_trabajador];
IF ( Object_id( 'USP_SM_GET_CondicionesBeneficiarioFallecido_beneficiario' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_CondicionesBeneficiarioFallecido_beneficiario];