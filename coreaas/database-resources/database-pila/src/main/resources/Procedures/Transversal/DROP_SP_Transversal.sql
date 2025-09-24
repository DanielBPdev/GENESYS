--Drop Store Procedures of Transversales
IF ( Object_id( 'USP_AlmacenarHistorialEstado' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_AlmacenarHistorialEstado];
  
IF ( Object_id( 'USP_CalcularDiasNovedadesBD' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_CalcularDiasNovedadesBD];
  
IF ( Object_id( 'USP_DeterminarAportePropio' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_DeterminarAportePropio];
  
IF ( Object_id( 'USP_SolicitarEvaluacionNovedadFutura' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_SolicitarEvaluacionNovedadFutura];
  
IF ( Object_id( 'USP_GetAportesSSIS' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GetAportesSSIS];

IF ( Object_id( 'USP_DeleteBloqueStaging' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_DeleteBloqueStaging];
  
IF ( Object_id( 'USP_ExecuteIntegrationResource' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteIntegrationResource];  

IF ( Object_id( 'USP_ExecuteBloqueStaging' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteBloqueStaging];

IF ( Object_id( 'USP_ExecutePILA2CopiarPlanilla' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecutePILA2CopiarPlanilla];

IF ( Object_id( 'USP_ExecutePILA2Fase1Validacion' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecutePILA2Fase1Validacion];

IF ( Object_id( 'USP_ExecutePILA2Fase2RegistrarRelacionarAportes' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecutePILA2Fase2RegistrarRelacionarAportes];

IF ( Object_id( 'USP_ExecutePILA2Fase3RegistrarRelacionarNovedades' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecutePILA2Fase3RegistrarRelacionarNovedades];

IF ( Object_id( 'USP_ExecutePILA2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecutePILA2]; 
  
IF ( Object_id( 'USP_ReiniciarProcesamiento' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ReiniciarProcesamiento];  
  
IF ( Object_id( 'USP_ExecutePILA2CopiarPlanillasN' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecutePILA2CopiarPlanillasN];  

IF ( Object_id( 'ASP_ValidarProcesadoNovedades' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[ASP_ValidarProcesadoNovedades];  

IF ( Object_id( 'ASP_ConsultaNovedadesPlanillasPilaB9' ) IS NOT NULL )	
DROP PROCEDURE [dbo].[ASP_ConsultaNovedadesPlanillasPilaB9];
  

  