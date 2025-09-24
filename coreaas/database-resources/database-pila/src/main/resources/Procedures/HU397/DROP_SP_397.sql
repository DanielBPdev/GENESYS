--Drop Store Procedures of HU397
IF ( Object_id( 'USP_ExecuteRegistrarRelacionarAportesRegistro' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ExecuteRegistrarRelacionarAportesRegistro]; 
IF ( Object_id( 'USP_AsignarMarcaProcesoFiscalizacion' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_AsignarMarcaProcesoFiscalizacion]; 
IF ( Object_id( 'USP_AsignarMarcaNovedadFutura' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_AsignarMarcaNovedadFutura];   
IF ( Object_id( 'USP_ActualizarAportanteAportePropio' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_ActualizarAportanteAportePropio];
IF ( Object_id( 'USP_VerificarCumplimientoSucursal' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_VerificarCumplimientoSucursal];
