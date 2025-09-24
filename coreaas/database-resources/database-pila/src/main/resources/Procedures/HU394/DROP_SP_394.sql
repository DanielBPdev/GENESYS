--Drop Store Procedures of HU394
IF ( Object_id( 'USP_GET_RevalidarPila2Fase1' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_RevalidarPila2Fase1];
IF ( Object_id( 'USP_GET_CalcularEstadoServiciosIndependientePensionado' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_CalcularEstadoServiciosIndependientePensionado];