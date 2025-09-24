--Drop Store Procedures of Transversal
IF ( Object_id( 'USP_ExecuteConsultarHistoricoAfiliacionEmpleador' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoAfiliacionEmpleador];
  
IF ( Object_id( 'USP_ExecuteConsultarHistoricoAfiliacionPersona' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoAfiliacionPersona];
  
IF ( Object_id( 'USP_ExecuteConsultarHistoricoEstadoAportante' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoEstadoAportante];
  