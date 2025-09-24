--Drop Store Procedures of HU391
IF ( Object_id( 'USP_ValidateHU391EsAportePropio' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ValidateHU391EsAportePropio];
IF ( Object_id( 'USP_GET_ConjuntoSecuenciasPersistenciaPilaM1' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_GET_ConjuntoSecuenciasPersistenciaPilaM1];