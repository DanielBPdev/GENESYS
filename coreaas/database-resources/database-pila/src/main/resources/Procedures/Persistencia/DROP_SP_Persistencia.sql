--Drop Store Procedures of Persistencia Bloque 4
IF ( Object_id( 'USP_ExecutePILA1Persistencia' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecutePILA1Persistencia];

IF ( Object_id( 'USP_EliminarPreliminarArchivoPila' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_EliminarPreliminarArchivoPila];

IF ( Object_id( 'USP_CargarParametrizacionPersistencia' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_CargarParametrizacionPersistencia];

IF ( Object_id( 'ASP_PILA_REPROCESO' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[ASP_PILA_REPROCESO];

IF ( Object_id( 'FN_CrearInsertPersistenciaPILA' ) IS NOT NULL )
  DROP FUNCTION [dbo].[FN_CrearInsertPersistenciaPILA]; 
