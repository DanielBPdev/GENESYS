--liquibase formatted sql
--changeset dsuesca:01 runAlways:true runOnChange:true
--comment: Creación de borrado de CKs 2019-07-24T13:41:44Z
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AhorroPrevio_ahpNombreAhorro')) ALTER TABLE AhorroPrevio DROP CONSTRAINT CK_AhorroPrevio_ahpNombreAhorro;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CondicionVisita_covCondicion')) ALTER TABLE CondicionVisita DROP CONSTRAINT CK_CondicionVisita_covCondicion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cruce_cruEstadoCruce')) ALTER TABLE Cruce DROP CONSTRAINT CK_Cruce_cruEstadoCruce;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CruceDetalle_crdCausalCruce')) ALTER TABLE CruceDetalle DROP CONSTRAINT CK_CruceDetalle_crdCausalCruce;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CruceDetalle_crdTipo')) ALTER TABLE CruceDetalle DROP CONSTRAINT CK_CruceDetalle_crdTipo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CruceDetalle_crdClasificacion')) ALTER TABLE CruceDetalle DROP CONSTRAINT CK_CruceDetalle_crdClasificacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntegranteHogar_inhTipoIntegrante')) ALTER TABLE IntegranteHogar DROP CONSTRAINT CK_IntegranteHogar_inhTipoIntegrante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntegranteHogar_inhEstadoHogar')) ALTER TABLE IntegranteHogar DROP CONSTRAINT CK_IntegranteHogar_inhEstadoHogar;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoLegalizacionDesembolso_ildCausaIntentoFallido')) ALTER TABLE IntentoLegalizacionDesembolso DROP CONSTRAINT CK_IntentoLegalizacionDesembolso_ildCausaIntentoFallido;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoLegalizacionDesembolso_ildProceso')) ALTER TABLE IntentoLegalizacionDesembolso DROP CONSTRAINT CK_IntentoLegalizacionDesembolso_ildProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoLegalizacionDesembolso_ildModalidad')) ALTER TABLE IntentoLegalizacionDesembolso DROP CONSTRAINT CK_IntentoLegalizacionDesembolso_ildModalidad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoPostulacion_ipoCausaIntentoFallido')) ALTER TABLE IntentoPostulacion DROP CONSTRAINT CK_IntentoPostulacion_ipoCausaIntentoFallido;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoPostulacion_ipoTipoTransaccion')) ALTER TABLE IntentoPostulacion DROP CONSTRAINT CK_IntentoPostulacion_ipoTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoPostulacion_ipoProceso')) ALTER TABLE IntentoPostulacion DROP CONSTRAINT CK_IntentoPostulacion_ipoProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoPostulacion_ipoModalidad')) ALTER TABLE IntentoPostulacion DROP CONSTRAINT CK_IntentoPostulacion_ipoModalidad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_JefeHogar_jehEstadoHogar')) ALTER TABLE JefeHogar DROP CONSTRAINT CK_JefeHogar_jehEstadoHogar;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_LegalizacionDesembolso_lgdFormaPago')) ALTER TABLE LegalizacionDesembolso DROP CONSTRAINT CK_LegalizacionDesembolso_lgdFormaPago;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_LegalizacionDesembolso_lgdTipoMedioPago')) ALTER TABLE LegalizacionDesembolso DROP CONSTRAINT CK_LegalizacionDesembolso_lgdTipoMedioPago;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Licencia_licEntidadExpide')) ALTER TABLE Licencia DROP CONSTRAINT CK_Licencia_licEntidadExpide;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Licencia_licTipoLicencia')) ALTER TABLE Licencia DROP CONSTRAINT CK_Licencia_licTipoLicencia;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_LicenciaDetalle_lidClasificacionLicencia')) ALTER TABLE LicenciaDetalle DROP CONSTRAINT CK_LicenciaDetalle_lidClasificacionLicencia;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Oferente_ofeEstado')) ALTER TABLE Oferente DROP CONSTRAINT CK_Oferente_ofeEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Oferente_ofeTipoCuenta')) ALTER TABLE Oferente DROP CONSTRAINT CK_Oferente_ofeTipoCuenta;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Oferente_ofeTipoIdentificacionTitular')) ALTER TABLE Oferente DROP CONSTRAINT CK_Oferente_ofeTipoIdentificacionTitular;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionAsignacion_pasResultadoAsignacion')) ALTER TABLE PostulacionAsignacion DROP CONSTRAINT CK_PostulacionAsignacion_pasResultadoAsignacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionAsignacion_pasPrioridadAsignacion')) ALTER TABLE PostulacionAsignacion DROP CONSTRAINT CK_PostulacionAsignacion_pasPrioridadAsignacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofModalidad')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofModalidad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofEstadoHogar')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofEstadoHogar;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofCondicionHogar')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofCondicionHogar;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofResultadoAsignacion')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofResultadoAsignacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofPrioridadAsignacion')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofPrioridadAsignacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofMotivoDesistimiento')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofMotivoDesistimiento;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofMotivoRechazo')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofMotivoRechazo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofMotivoHabilitacion')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofMotivoHabilitacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofTiempoSancion')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofTiempoSancion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofMotivoRestitucion')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofMotivoRestitucion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PostulacionFOVIS_pofMotivoEnajenacion')) ALTER TABLE PostulacionFOVIS DROP CONSTRAINT CK_PostulacionFOVIS_pofMotivoEnajenacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProyectoSolucionVivienda_psvModalidad')) ALTER TABLE ProyectoSolucionVivienda DROP CONSTRAINT CK_ProyectoSolucionVivienda_psvModalidad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RecursoComplementario_recNombre')) ALTER TABLE RecursoComplementario DROP CONSTRAINT CK_RecursoComplementario_recNombre;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAnalisisNovedadFovis_sanEstadoSolicitud')) ALTER TABLE SolicitudAnalisisNovedadFovis DROP CONSTRAINT CK_SolicitudAnalisisNovedadFovis_sanEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAsignacion_safEstadoSolicitudAsignacion')) ALTER TABLE SolicitudAsignacion DROP CONSTRAINT CK_SolicitudAsignacion_safEstadoSolicitudAsignacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudGestionCruce_sgcEstadoCruceHogar')) ALTER TABLE SolicitudGestionCruce DROP CONSTRAINT CK_SolicitudGestionCruce_sgcEstadoCruceHogar;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudGestionCruce_sgcTipoCruce')) ALTER TABLE SolicitudGestionCruce DROP CONSTRAINT CK_SolicitudGestionCruce_sgcTipoCruce;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudGestionCruce_sgcEstado')) ALTER TABLE SolicitudGestionCruce DROP CONSTRAINT CK_SolicitudGestionCruce_sgcEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLegalizacionDesembolso_sldEstadoSolicitud')) ALTER TABLE SolicitudLegalizacionDesembolso DROP CONSTRAINT CK_SolicitudLegalizacionDesembolso_sldEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudNovedadFovis_snfEstadoSolicitud')) ALTER TABLE SolicitudNovedadFovis DROP CONSTRAINT CK_SolicitudNovedadFovis_snfEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudPostulacion_spoEstadoSolicitud')) ALTER TABLE SolicitudPostulacion DROP CONSTRAINT CK_SolicitudPostulacion_spoEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudVerificacionFovis_svfEstadoSolicitud')) ALTER TABLE SolicitudVerificacionFovis DROP CONSTRAINT CK_SolicitudVerificacionFovis_svfEstadoSolicitud;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudVerificacionFovis_svfTipo')) ALTER TABLE SolicitudVerificacionFovis DROP CONSTRAINT CK_SolicitudVerificacionFovis_svfTipo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CicloAsignacion_ciaEstadoCicloAsignacion')) ALTER TABLE CicloAsignacion DROP CONSTRAINT CK_CicloAsignacion_ciaEstadoCicloAsignacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CicloModalidad_cmoModalidad')) ALTER TABLE CicloModalidad DROP CONSTRAINT CK_CicloModalidad_cmoModalidad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_FormaPagoModalidad_fpmModalidad')) ALTER TABLE FormaPagoModalidad DROP CONSTRAINT CK_FormaPagoModalidad_fpmModalidad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_FormaPagoModalidad_fpmFormaPago')) ALTER TABLE FormaPagoModalidad DROP CONSTRAINT CK_FormaPagoModalidad_fpmFormaPago;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionFOVIS_pafNombre')) ALTER TABLE ParametrizacionFOVIS DROP CONSTRAINT CK_ParametrizacionFOVIS_pafNombre;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionFOVIS_pafPlazoVencimiento')) ALTER TABLE ParametrizacionFOVIS DROP CONSTRAINT CK_ParametrizacionFOVIS_pafPlazoVencimiento;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionFOVIS_pafPlazoAdicional')) ALTER TABLE ParametrizacionFOVIS DROP CONSTRAINT CK_ParametrizacionFOVIS_pafPlazoAdicional;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionModalidad_pmoNombre')) ALTER TABLE ParametrizacionModalidad DROP CONSTRAINT CK_ParametrizacionModalidad_pmoNombre;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RangoTopeValorSFV_rtvModalidad')) ALTER TABLE RangoTopeValorSFV DROP CONSTRAINT CK_RangoTopeValorSFV_rtvModalidad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RangoTopeValorSFV_rtvOperadorValorMinimo')) ALTER TABLE RangoTopeValorSFV DROP CONSTRAINT CK_RangoTopeValorSFV_rtvOperadorValorMinimo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RangoTopeValorSFV_rtvOperadorValorMaximo')) ALTER TABLE RangoTopeValorSFV DROP CONSTRAINT CK_RangoTopeValorSFV_rtvOperadorValorMaximo;

