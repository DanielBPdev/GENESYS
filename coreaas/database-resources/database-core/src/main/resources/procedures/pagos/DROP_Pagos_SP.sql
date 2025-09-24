--Drop Store Procedures 
IF ( Object_id( 'USP_PG_ValidarCamposArchivoRetiroCuenta' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_ValidarCamposArchivoRetiroCuenta];
IF ( Object_id( 'USP_PG_ValidarCamposArchivoRetiroPagos' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_ValidarCamposArchivoRetiroPagos];
IF ( Object_id( 'USP_SM_AlmacenarDetallesAsignadosProgramadosADetallesSubsidiosAsignados' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_SM_AlmacenarDetallesAsignadosProgramadosADetallesSubsidiosAsignados];
IF ( Object_id( 'USP_PG_ValidarContenidoArchivoTerceroPagadorEfectivo' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_ValidarContenidoArchivoTerceroPagadorEfectivo];
IF ( Object_id( 'USP_PG_InsertRestuladosValidacionCargaManualRetiroTerceroPag' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_InsertRestuladosValidacionCargaManualRetiroTerceroPag];
IF ( Object_id( 'USP_PG_ResultadosDispersionAdministradorMedioPago' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_ResultadosDispersionAdministradorMedioPago];
IF ( Object_id( 'USP_PG_ConfirmarAbonosMedioPagoBancos' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_ConfirmarAbonosMedioPagoBancos];
IF ( Object_id( 'USP_PG_DispersarPagosEstadoEnviado' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_DispersarPagosEstadoEnviado];
IF ( Object_id( 'USP_PG_DispersarPagosEstadoAplicado' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_DispersarPagosEstadoAplicado];
IF ( Object_id( 'USP_PG_GET_ConsultaDetallesSubsidio' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_GET_ConsultaDetallesSubsidio];
IF ( Object_id( 'USP_PG_GET_ConsultaTransaccionesSubsidio' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_GET_ConsultaTransaccionesSubsidio];
IF ( Object_id( 'USP_PG_GET_ConsultaTransDetallesSubsidio' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_GET_ConsultaTransDetallesSubsidio];
IF ( Object_id( 'USP_PG_ModificarCuentaYDetallePorReverso' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_PG_ModificarCuentaYDetallePorReverso];
IF ( Object_id( 'sp_consultar_liquidaciones_especificas' ) IS NOT NULL ) DROP PROCEDURE [dbo].[sp_consultar_liquidaciones_especificas];