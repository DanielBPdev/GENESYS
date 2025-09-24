--Drop Store Procedures 
IF ( Object_id( 'USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores];
IF ( Object_id( 'USP_ExecuteCARTERACalcularDeudaPresuntaIndependientes' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaIndependientes];
IF ( Object_id( 'USP_ExecuteCARTERACalcularDeudaPresuntaPensionados' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_ExecuteCARTERACalcularDeudaPresuntaPensionados];
--IF ( Object_id( 'USP_ExecuteCARTERAAsignarAccionCobro' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_ExecuteCARTERAAsignarAccionCobro];
IF ( Object_id( 'USP_ExecuteCARTERAConsultarAportantesGestionCobro' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_ExecuteCARTERAConsultarAportantesGestionCobro];
IF ( Object_id( 'USP_ExecuteCARTERAConsultarAportantesGestionCobroPersonas' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_ExecuteCARTERAConsultarAportantesGestionCobroPersonas];