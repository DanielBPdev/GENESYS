--Drop Store Procedures
IF ( Object_id( 'USP_SM_GET_CondicionesTrabajadorFallecido_trabajador' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_CondicionesTrabajadorFallecido_trabajador];
IF ( Object_id( 'USP_SM_GET_CondicionesBeneficiarioFallecido_beneficiario' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_CondicionesBeneficiarioFallecido_beneficiario];
IF ( Object_id( 'USP_SM_GET_CotizanteConSubsidioPeriodo' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_CotizanteConSubsidioPeriodo];
IF ( Object_id( 'USP_SM_GET_InsertCuentaAdministradorSubsidios' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_InsertCuentaAdministradorSubsidios];
IF ( Object_id( 'USP_SM_GET_InsertDetalleSubsidioAsignado' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_InsertDetalleSubsidioAsignado];
IF ( Object_id( 'USP_SM_GET_InsertDescuentosSubsidioAsignado' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_InsertDescuentosSubsidioAsignado];
IF ( Object_id( 'USP_SM_GET_InsertDetalleSubsidioAsignadoProgramado' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_InsertDetalleSubsidioAsignadoProgramado];
IF ( Object_id( 'USP_SM_GET_InsertPeriodo' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_InsertPeriodo];
IF ( Object_id( 'USP_SM_GET_InsertTablasMigracion' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_InsertTablasMigracion];
IF ( Object_id( 'USP_SM_UTIL_EliminarDispersion' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_UTIL_EliminarDispersion];
IF ( Object_id( 'USP_SM_GET_LiquidacionesFallecimientoProgramado' ) IS NOT NULL )DROP PROCEDURE [dbo].[USP_SM_GET_LiquidacionesFallecimientoProgramado]
IF ( Object_id( 'USP_UTIL_GET_CrearRevisionBloqueoCuotaMonetaria' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_UTIL_GET_CrearRevisionBloqueoCuotaMonetaria];
IF ( Object_id( 'USP_UTIL_GET_CrearRevisionDesbloqueoCuotaMonetaria' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_UTIL_GET_CrearRevisionDesbloqueoCuotaMonetaria];
IF ( Object_id( 'USP_SM_GET_InsertCuentaAdministradorSubsidioProgramada' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_InsertCuentaAdministradorSubsidioProgramada];
IF ( Object_id( 'USP_SM_GET_ConsultarDispersionMontoLiquidado' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_ConsultarDispersionMontoLiquidado];
IF ( Object_id( 'USP_SM_GET_ListadoAbonosPorVencimientoYPrescripcion' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_ListadoAbonosPorVencimientoYPrescripcion];
IF ( Object_id( 'USP_SM_GET_ListadoAbonosPorVencimientoYPrescripcionResumen' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_ListadoAbonosPorVencimientoYPrescripcionResumen];
IF ( Object_id( 'USP_SM_GET_ListadoArchivoTransDetaSubsidio' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_GET_ListadoArchivoTransDetaSubsidio];
IF ( Object_id( 'Subsidio_Especie_Liq_Manual' ) IS NOT NULL )	DROP PROCEDURE [dbo].[Subsidio_Especie_Liq_Manual];
IF ( Object_id( 'DATOS_ESPECIFICICOS_FALLECIMIENTO' ) IS NOT NULL )	DROP PROCEDURE [dbo].[DATOS_ESPECIFICICOS_FALLECIMIENTO];
IF ( Object_id( 'TRUNCATE_CORE_FALLECIMIENTO' ) IS NOT NULL )	DROP PROCEDURE [dbo].[TRUNCATE_CORE_FALLECIMIENTO];
IF ( Object_id( 'USP_Calculo_Novedades_Fovis_Pendientes' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_Calculo_Novedades_Fovis_Pendientes];
IF ( Object_id( 'USP_Consultar_Postulaciones_Para_Legalizacion' ) IS NOT NULL )	DROP PROCEDURE [dbo].[USP_Consultar_Postulaciones_Para_Legalizacion];
IF ( Object_id( 'SP_ActualizarEstadisticasGenesys' ) IS NOT NULL )	DROP PROCEDURE [dbo].[SP_ActualizarEstadisticasGenesys];