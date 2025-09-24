--Drop Store Procedures of HU398
IF ( Object_id( 'USP_ExecuteRegistrarNovedadesNoAplicadas' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteRegistrarNovedadesNoAplicadas];

IF ( Object_id( 'USP_ValidarNovedadesRetroactivasEmpleador' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidarNovedadesRetroactivasEmpleador];

IF ( Object_id( 'USP_ValidarNovedadesRetiros' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidarNovedadesRetiros];

IF ( Object_id( 'USP_RegistrarRelacionarNovedadesReferenciales' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_RegistrarRelacionarNovedadesReferenciales];

IF ( Object_id( 'USP_ValidarNovedadesEmpleadorActivo' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidarNovedadesEmpleadorActivo];
 
IF ( Object_id( 'USP_ExecuteCopiarNovedades' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteCopiarNovedades];
  
IF ( Object_id( 'USP_ExecuteRegistrarRelacionarNovedadesRegistro' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteRegistrarRelacionarNovedadesRegistro]; 
  
IF ( Object_id( 'FN_RegistrarRelacionarNovedadesReferenciales' ) IS NOT NULL )
  DROP FUNCTION [dbo].[FN_RegistrarRelacionarNovedadesReferenciales]; 

IF ( Object_id( 'USP_CrearNovedadIngresoPorAportesOK' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_CrearNovedadIngresoPorAportesOK]; 

IF ( Object_id( 'USP_ExecuteRegistrarRelacionarNovedadesRegistro_N' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteRegistrarRelacionarNovedadesRegistro_N]; 
