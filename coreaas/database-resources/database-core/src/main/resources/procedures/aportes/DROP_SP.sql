--Drop Store Procedures
IF ( Object_id( 'USP_ActualizarAporte' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_ActualizarAporte];
IF ( Object_id( 'USP_CalculoMasivoCategorias' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_CalculoMasivoCategorias];
IF ( Object_id( 'USP_CopiarAportesDesdeTemporalPila' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_CopiarAportesDesdeTemporalPila];
IF ( Object_id( 'USP_CopiarPilaAportesStaging' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_CopiarPilaAportesStaging];
IF ( Object_id( 'USP_GET_CategoriaAfiliado' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_GET_CategoriaAfiliado];
IF ( Object_id( 'USP_GET_CategoriaBeneficiario' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_GET_CategoriaBeneficiario];
IF ( Object_id( 'USP_GET_BandejaTransitoriaGestion' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_GET_BandejaTransitoriaGestion];
IF ( Object_id( 'USP_CopiarAportesDesdeTemporalPilaCorrecciones' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_CopiarAportesDesdeTemporalPilaCorrecciones];
IF ( Object_id( 'fechaNovedadPila' ) IS NOT NULL )	DROP PROCEDURE [dbo].[fechaNovedadPila];
IF ( Object_id( 'ASP_GET_COTIZANTE_REINTEGRO' ) IS NOT NULL )	DROP PROCEDURE [dbo].[ASP_GET_COTIZANTE_REINTEGRO];
IF ( Object_id( 'ASP_GET_COTIZANTE_FECHA_INGRESO' ) IS NOT NULL )	DROP PROCEDURE [dbo].[ASP_GET_COTIZANTE_FECHA_INGRESO];
IF ( Object_id( 'ASP_NovedadesRegistradasPersonaIntING' ) IS NOT NULL )	DROP PROCEDURE [dbo].[ASP_NovedadesRegistradasPersonaIntING];
IF ( Object_id( 'USP_GET_CategoriasHeredadas' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_GET_CategoriasHeredadas];
IF ( Object_id( 'USP_REP_CalcularCategoriaAportesPila' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_REP_CalcularCategoriaAportesPila];
IF ( Object_id( 'USP_REP_CalcularCategoriaNuevaAfiliacion' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion];
IF ( Object_id( 'USP_CopiarAportesDesdeTemporalPilaPlanillasN' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_CopiarAportesDesdeTemporalPilaPlanillasN];
IF ( Object_id( 'USP_DesafiliacionEmpleador_Trabajadores_Masiva' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_DesafiliacionEmpleador_Trabajadores_Masiva];
IF ( Object_id( 'USP_REP_CalcularCategoriaBeneficiario' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_REP_CalcularCategoriaBeneficiario];
IF ( Object_id( 'USP_EJECUTAR_REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_EJECUTAR_REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION];
IF ( Object_id( 'USP_Consultar_tiempos_multiafiliacion' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_Consultar_tiempos_multiafiliacion];
IF ( Object_id( 'USP_REP_CalcularIngresosFovis' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_REP_CalcularIngresosFovis];
IF ( Object_id( 'USP_REP_CalcularCategoriaBeneficiarioInsert' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_REP_CalcularCategoriaBeneficiarioInsert];
IF ( Object_id( 'USP_CorreccionDiariaCategorias' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_CorreccionDiariaCategorias];





