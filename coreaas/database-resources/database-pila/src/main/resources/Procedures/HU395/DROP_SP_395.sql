--Drop Store Procedures of HU 395
IF ( Object_id( 'USP_ValidateHU395CondicionesAportesDependiente' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU395CondicionesAportesDependiente];

IF ( Object_id( 'USP_ValidateHU395CondicionesAportesIndependiente' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU395CondicionesAportesIndependiente];

IF ( Object_id( 'USP_ValidateHU395CondicionesAportesPensionados' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU395CondicionesAportesPensionados];

IF ( Object_id( 'USP_ExecutePILA2Fase1VerificarCondicionesValidarAportes' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecutePILA2Fase1VerificarCondicionesValidarAportes]; 
