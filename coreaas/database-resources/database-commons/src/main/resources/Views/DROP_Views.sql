--Drop Views 
IF ( Object_id( 'VW_EstadoAfiliacionPersonaPensionado' ) IS NOT NULL ) DROP VIEW [dbo].[VW_EstadoAfiliacionPersonaPensionado];
IF ( Object_id( 'VW_EstadoAfiliacionPersonaIndependiente' ) IS NOT NULL ) DROP VIEW [dbo].[VW_EstadoAfiliacionPersonaIndependiente];
IF ( Object_id( 'VW_EstadoAfiliacionPersonaCaja' ) IS NOT NULL ) DROP VIEW [dbo].[VW_EstadoAfiliacionPersonaCaja];
IF ( Object_id( 'VW_EstadoAfiliacionBeneficiario' ) IS NOT NULL ) DROP VIEW [dbo].[VW_EstadoAfiliacionBeneficiario];
IF ( Object_id( 'VW_EstadoAfiliacionPersonaEmpresa' ) IS NOT NULL ) DROP VIEW [dbo].[VW_EstadoAfiliacionPersonaEmpresa];
IF ( Object_id( 'VW_EstadoAfiliacionEmpleadorCaja' ) IS NOT NULL ) DROP VIEW [dbo].[VW_EstadoAfiliacionEmpleadorCaja];
IF ( Object_id( 'VW_EstadoAfiliacionPersonaCaja_Novedades' ) IS NOT NULL ) DROP VIEW [dbo].[VW_EstadoAfiliacionPersonaCaja_Novedades];