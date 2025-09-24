--Drop Store Procedures of Indicadores
IF ( Object_id( 'USP_REP_MERGE_FactSolicitudNovedadPersonaV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactSolicitudNovedadPersonaV2];
IF ( Object_id( 'USP_REP_MERGE_FactSolicitudNovedadEmpleadorV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactSolicitudNovedadEmpleadorV2];

IF ( Object_id( 'USP_REP_MERGE_FactAfiliadoV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactAfiliadoV2];
IF ( Object_id( 'USP_REP_MERGE_FactAfiliacionPersonaV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactAfiliacionPersonaV2];
IF ( Object_id( 'USP_REP_MERGE_FactSolicitudAfiliacionPersonaV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactSolicitudAfiliacionPersonaV2];
IF ( Object_id( 'USP_REP_MERGE_FactSolicitudAfiliacionEmpleadorV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactSolicitudAfiliacionEmpleadorV2]; 
IF ( Object_id( 'USP_REP_MERGE_FactAfiliacionEmpleadorV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactAfiliacionEmpleadorV2]; 

IF ( Object_id( 'USP_REP_MERGE_FactNovedadFOVISV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactNovedadFOVISV2];
IF ( Object_id( 'USP_REP_MERGE_FactLegalizacionDesembolsoFOVISV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactLegalizacionDesembolsoFOVISV2];
IF ( Object_id( 'USP_REP_MERGE_FactAsignacionFOVISV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactAsignacionFOVISV2];
IF ( Object_id( 'USP_REP_MERGE_FactPostulacionFOVISV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactPostulacionFOVISV2];
IF ( Object_id( 'USP_REP_INSERT_DimCicloAsignacionV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_INSERT_DimCicloAsignacionV2];
IF ( Object_id( 'USP_REP_MERGE_FactGestionCarteraV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactGestionCarteraV2];
IF ( Object_id( 'USP_REP_MERGE_FactCondicionSolicitanteCarteraV2' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactCondicionSolicitanteCarteraV2];

IF ( Object_id( 'USP_REP_MERGE_FactNovedadEmpleador' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactNovedadEmpleador];
IF ( Object_id( 'USP_REP_MERGE_FactNovedadPersona' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactNovedadPersona];
IF ( Object_id( 'USP_REP_MERGE_FactCondicionCotizante' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactCondicionCotizante];
IF ( Object_id( 'USP_REP_MERGE_FactArchivoPILA' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactArchivoPILA];
IF ( Object_id( 'USP_REP_MERGE_FactCondicionAportante' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactCondicionAportante];
IF ( Object_id( 'USP_REP_UPDATE_ValorMetaPeriodoCanalKPI' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_UPDATE_ValorMetaPeriodoCanalKPI];
IF ( Object_id( 'USP_REP_MERGE_FactCondicionBeneficiario' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactCondicionBeneficiario];
IF ( Object_id( 'USP_REP_MERGE_FactCondicionSolicitudesPersona' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactCondicionSolicitudesPersona];
IF ( Object_id( 'USP_REP_MERGE_FactCondicionPersona' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactCondicionPersona];
IF ( Object_id( 'USP_REP_MERGE_FactCondicionSolicitudesEmpleador' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactCondicionSolicitudesEmpleador];
IF ( Object_id( 'USP_REP_MERGE_FactCondicionEmpleador' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_MERGE_FactCondicionEmpleador];
IF ( Object_id( 'USP_REP_INSERT_DimPeriodo' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_INSERT_DimPeriodo];
IF ( Object_id( 'USP_REP_INSERT_DimSede' ) IS NOT NULL )
  DROP PROCEDURE [dbo].[USP_REP_INSERT_DimSede];