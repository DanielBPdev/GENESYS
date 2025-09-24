--Drop Store Procedures of Transversal
IF ( Object_id( 'USP_REP_INSERT_ParametrizacionMeta' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_INSERT_ParametrizacionMeta];
IF ( Object_id( 'USP_REP_GET_ParametrizacionMeta' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_GET_ParametrizacionMeta];