--Drop Store Procedures of HU 396
IF ( Object_id( 'USP_ValidateEstadoArchivoDependientes' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateEstadoArchivoDependientes];

IF ( Object_id( 'USP_ValidateEstadoArchivoInDependiente' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateEstadoArchivoInDependiente];

IF ( Object_id( 'USP_ValidateEstadoArchivoPensionado' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateEstadoArchivoPensionado];

IF ( Object_id( 'USP_ValidateHU396BD590BD1429_T2T3' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU396BD590BD1429_T2T3];

IF ( Object_id( 'USP_ValidateHU396V0_T1T2T3' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU396V0_T1T2T3];

IF ( Object_id( 'USP_ValidateNovedadesPILA' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateNovedadesPILA];

IF ( Object_id( 'USP_ValidateHU396V1BD1429BD590_T2T3' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU396V1BD1429BD590_T2T3];

IF ( Object_id( 'USP_ValidateHU396V1BDNormal_T1T2T3' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU396V1BDNormal_T1T2T3];

IF ( Object_id( 'USP_ValidateHU396V1Independientes' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU396V1Independientes];

IF ( Object_id( 'USP_ValidateHU396V1Pensionados' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU396V1Pensionados];

IF ( Object_id( 'USP_ValidateHU396V2BDNormalBD1429BD590_T1T2T3' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU396V2BDNormalBD1429BD590_T1T2T3];

IF ( Object_id( 'USP_ValidateHU396V3BDNormalBD1429BD590_T1T2T3' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU396V3BDNormalBD1429BD590_T1T2T3];

IF ( Object_id( 'USP_ValidateHU396V1Dependientes' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[Usp_validatehu396v1dependientes];
  
IF ( Object_id( 'USP_ValidateDiasNovedadesPlanillaPILA' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateDiasNovedadesPlanillaPILA];

IF ( Object_id( 'USP_ExecutePILA2Fase1ValidarRegistrosPILAvsBD' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecutePILA2Fase1ValidarRegistrosPILAvsBD]; 
