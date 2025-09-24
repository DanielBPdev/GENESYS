--liquibase formatted sql
--changeset dsuesca:01 runAlways:true runOnChange:true
--comment: Creaci�n de borrado de CKs 2018-03-26T22:34:13Z
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueMultiple_camTipoSolicitante')) ALTER TABLE CargueMultiple DROP CONSTRAINT CK_CargueMultiple_camTipoSolicitante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueMultiple_camClasificacion')) ALTER TABLE CargueMultiple DROP CONSTRAINT CK_CargueMultiple_camClasificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueMultiple_camTipoTransaccion')) ALTER TABLE CargueMultiple DROP CONSTRAINT CK_CargueMultiple_camTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueMultiple_camProceso')) ALTER TABLE CargueMultiple DROP CONSTRAINT CK_CargueMultiple_camProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueMultiple_camEstado')) ALTER TABLE CargueMultiple DROP CONSTRAINT CK_CargueMultiple_camEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DatoTemporalSolicitud_dtsTipoIdentificacion')) ALTER TABLE DatoTemporalSolicitud DROP CONSTRAINT CK_DatoTemporalSolicitud_dtsTipoIdentificacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DocumentoEntidadPagadora_dpgTipoDocumento')) ALTER TABLE DocumentoEntidadPagadora DROP CONSTRAINT CK_DocumentoEntidadPagadora_dpgTipoDocumento;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EscalamientoSolicitud_esoResultadoAnalista')) ALTER TABLE EscalamientoSolicitud DROP CONSTRAINT CK_EscalamientoSolicitud_esoResultadoAnalista;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EscalamientoSolicitud_esoTipoAnalistaFOVIS')) ALTER TABLE EscalamientoSolicitud DROP CONSTRAINT CK_EscalamientoSolicitud_esoTipoAnalistaFOVIS;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoAfiliacion_iafCausaIntentoFallido')) ALTER TABLE IntentoAfiliacion DROP CONSTRAINT CK_IntentoAfiliacion_iafCausaIntentoFallido;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoAfiliacion_iafTipoTransaccion')) ALTER TABLE IntentoAfiliacion DROP CONSTRAINT CK_IntentoAfiliacion_iafTipoTransaccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ItemChequeo_ichEstadoRequisito')) ALTER TABLE ItemChequeo DROP CONSTRAINT CK_ItemChequeo_ichEstadoRequisito;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ItemChequeo_ichFormatoEntregaDocumento')) ALTER TABLE ItemChequeo DROP CONSTRAINT CK_ItemChequeo_ichFormatoEntregaDocumento;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncTipoProductoNoConforme')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncTipoProductoNoConforme;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncTipoError')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncTipoError;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncResultadoGestion')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncResultadoGestion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncResultadoRevisionBack')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncResultadoRevisionBack;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncResultadoRevisionBack2')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncResultadoRevisionBack2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncClasificacionTipoProducto')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncClasificacionTipoProducto;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAfiliaciEmpleador_saeEstadoSolicitud')) ALTER TABLE SolicitudAfiliaciEmpleador DROP CONSTRAINT CK_SolicitudAfiliaciEmpleador_saeEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAfiliacionPersona_sapEstadoSolicitud')) ALTER TABLE SolicitudAfiliacionPersona DROP CONSTRAINT CK_SolicitudAfiliacionPersona_sapEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAsociacionPersonaEntidadPagadora_soaEstado')) ALTER TABLE SolicitudAsociacionPersonaEntidadPagadora DROP CONSTRAINT CK_SolicitudAsociacionPersonaEntidadPagadora_soaEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAsociacionPersonaEntidadPagadora_soaTipoGestion')) ALTER TABLE SolicitudAsociacionPersonaEntidadPagadora DROP CONSTRAINT CK_SolicitudAsociacionPersonaEntidadPagadora_soaTipoGestion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_GestionNotiNoEnviadas_gneTipoInconsistencia')) ALTER TABLE GestionNotiNoEnviadas DROP CONSTRAINT CK_GestionNotiNoEnviadas_gneTipoInconsistencia;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_GestionNotiNoEnviadas_gneCanalContacto')) ALTER TABLE GestionNotiNoEnviadas DROP CONSTRAINT CK_GestionNotiNoEnviadas_gneCanalContacto;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_GestionNotiNoEnviadas_gneEstadoGestion')) ALTER TABLE GestionNotiNoEnviadas DROP CONSTRAINT CK_GestionNotiNoEnviadas_gneEstadoGestion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdTipoCotizante')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdTipoCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdEstadoCotizante')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdEstadoCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdEstadoAporteCotizante')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdEstadoAporteCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdEstadoRegistroAporteCotizante')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdEstadoRegistroAporteCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdEstadoAporteRecaudo')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdEstadoAporteRecaudo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdEstadoAporteAjuste')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdEstadoAporteAjuste;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdEstadoRegistroAporteArchivo')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdEstadoRegistroAporteArchivo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgEstadoAportante')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgEstadoAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgEstadoAporteAportante')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgEstadoAporteAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgEstadoRegistroAporteAportante')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgEstadoRegistroAporteAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgModalidadPlanilla')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgModalidadPlanilla;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgModalidadRecaudoAporte')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgModalidadRecaudoAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgTipoSolicitante')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgTipoSolicitante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgOrigenAporte')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgOrigenAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgFormaReconocimientoAporte')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgFormaReconocimientoAporte;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DevolucionAporte_dapMotivoPeticion')) ALTER TABLE DevolucionAporte DROP CONSTRAINT CK_DevolucionAporte_dapMotivoPeticion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DevolucionAporte_dapDestinatarioDevolucion')) ALTER TABLE DevolucionAporte DROP CONSTRAINT CK_DevolucionAporte_dapDestinatarioDevolucion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_InformacionFaltanteAportante_ifaTipoGestion')) ALTER TABLE InformacionFaltanteAportante DROP CONSTRAINT CK_InformacionFaltanteAportante_ifaTipoGestion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_InformacionFaltanteAportante_ifaResponsableInformacion')) ALTER TABLE InformacionFaltanteAportante DROP CONSTRAINT CK_InformacionFaltanteAportante_ifaResponsableInformacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_InformacionFaltanteAportante_ifaTipoDocumentoGestion')) ALTER TABLE InformacionFaltanteAportante DROP CONSTRAINT CK_InformacionFaltanteAportante_ifaTipoDocumentoGestion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_InformacionFaltanteAportante_ifaMedioComunicacion')) ALTER TABLE InformacionFaltanteAportante DROP CONSTRAINT CK_InformacionFaltanteAportante_ifaMedioComunicacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MovimientoAporte_moaTipoAjuste')) ALTER TABLE MovimientoAporte DROP CONSTRAINT CK_MovimientoAporte_moaTipoAjuste;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MovimientoAporte_moaTipoMovimiento')) ALTER TABLE MovimientoAporte DROP CONSTRAINT CK_MovimientoAporte_moaTipoMovimiento;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MovimientoAporte_moaEstadoAporte')) ALTER TABLE MovimientoAporte DROP CONSTRAINT CK_MovimientoAporte_moaEstadoAporte;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroEstadoAporte_reaActividad')) ALTER TABLE RegistroEstadoAporte DROP CONSTRAINT CK_RegistroEstadoAporte_reaActividad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroEstadoAporte_reaEstadoSolicitud')) ALTER TABLE RegistroEstadoAporte DROP CONSTRAINT CK_RegistroEstadoAporte_reaEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAporte_soaEstadoSolicitud')) ALTER TABLE SolicitudAporte DROP CONSTRAINT CK_SolicitudAporte_soaEstadoSolicitud;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAporte_soaTipoIdentificacion')) ALTER TABLE SolicitudAporte DROP CONSTRAINT CK_SolicitudAporte_soaTipoIdentificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAporte_soaTipoSolicitante')) ALTER TABLE SolicitudAporte DROP CONSTRAINT CK_SolicitudAporte_soaTipoSolicitante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudCorreccionAporte_scaEstadoSolicitud')) ALTER TABLE SolicitudCorreccionAporte DROP CONSTRAINT CK_SolicitudCorreccionAporte_scaEstadoSolicitud;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudCorreccionAporte_scaTipoSolicitante')) ALTER TABLE SolicitudCorreccionAporte DROP CONSTRAINT CK_SolicitudCorreccionAporte_scaTipoSolicitante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudCorreccionAporte_scaResultadoSupervisor')) ALTER TABLE SolicitudCorreccionAporte DROP CONSTRAINT CK_SolicitudCorreccionAporte_scaResultadoSupervisor;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudDevolucionAporte_sdaEstadoSolicitud')) ALTER TABLE SolicitudDevolucionAporte DROP CONSTRAINT CK_SolicitudDevolucionAporte_sdaEstadoSolicitud;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudDevolucionAporte_sdaTipoSolicitante')) ALTER TABLE SolicitudDevolucionAporte DROP CONSTRAINT CK_SolicitudDevolucionAporte_sdaTipoSolicitante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudDevolucionAporte_sdaResultadoAnalista')) ALTER TABLE SolicitudDevolucionAporte DROP CONSTRAINT CK_SolicitudDevolucionAporte_sdaResultadoAnalista;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudDevolucionAporte_sdaResultadoSupervisor')) ALTER TABLE SolicitudDevolucionAporte DROP CONSTRAINT CK_SolicitudDevolucionAporte_sdaResultadoSupervisor;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TasasInteresMora_timTipoInteres')) ALTER TABLE TasasInteresMora DROP CONSTRAINT CK_TasasInteresMora_timTipoInteres;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionGestionCobro_pgcMetodoEnvioComunicado')) ALTER TABLE ParametrizacionGestionCobro DROP CONSTRAINT CK_ParametrizacionGestionCobro_pgcMetodoEnvioComunicado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionGestionCobro_pgcTipoParametrizacion')) ALTER TABLE ParametrizacionGestionCobro DROP CONSTRAINT CK_ParametrizacionGestionCobro_pgcTipoParametrizacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro1C_accVariableCalculo')) ALTER TABLE AccionCobro1C DROP CONSTRAINT CK_AccionCobro1C_accVariableCalculo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro1D1E_acdInicioDiasConteo')) ALTER TABLE AccionCobro1D1E DROP CONSTRAINT CK_AccionCobro1D1E_acdInicioDiasConteo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro1D1E_acdTipoCobro')) ALTER TABLE AccionCobro1D1E DROP CONSTRAINT CK_AccionCobro1D1E_acdTipoCobro;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro1F_abfSiguienteAccion')) ALTER TABLE AccionCobro1F DROP CONSTRAINT CK_AccionCobro1F_abfSiguienteAccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2D_aodInicioDiasConteo')) ALTER TABLE AccionCobro2D DROP CONSTRAINT CK_AccionCobro2D_aodInicioDiasConteo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2E_aceInicioDiasConteo')) ALTER TABLE AccionCobro2E DROP CONSTRAINT CK_AccionCobro2E_aceInicioDiasConteo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2F2G_aofInicioDiasConteo')) ALTER TABLE AccionCobro2F2G DROP CONSTRAINT CK_AccionCobro2F2G_aofInicioDiasConteo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2F2G_aofTipoCobro')) ALTER TABLE AccionCobro2F2G DROP CONSTRAINT CK_AccionCobro2F2G_aofTipoCobro;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2H_achSiguienteAccion')) ALTER TABLE AccionCobro2H DROP CONSTRAINT CK_AccionCobro2H_achSiguienteAccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobroA_acaMetodo')) ALTER TABLE AccionCobroA DROP CONSTRAINT CK_AccionCobroA_acaMetodo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobroB_acbMetodo')) ALTER TABLE AccionCobroB DROP CONSTRAINT CK_AccionCobroB_acbMetodo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ActividadCartera_acrActividadCartera')) ALTER TABLE ActividadCartera DROP CONSTRAINT CK_ActividadCartera_acrActividadCartera;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ActividadCartera_acrResultadoCartera')) ALTER TABLE ActividadCartera DROP CONSTRAINT CK_ActividadCartera_acrResultadoCartera;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ActividadDocumento_adoTipoDocumento')) ALTER TABLE ActividadDocumento DROP CONSTRAINT CK_ActividadDocumento_adoTipoDocumento;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AgendaCartera_agrVisitaAgenda')) ALTER TABLE AgendaCartera DROP CONSTRAINT CK_AgendaCartera_agrVisitaAgenda;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BitacoraCartera_bcaActividad')) ALTER TABLE BitacoraCartera DROP CONSTRAINT CK_BitacoraCartera_bcaActividad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BitacoraCartera_bcaMedio')) ALTER TABLE BitacoraCartera DROP CONSTRAINT CK_BitacoraCartera_bcaMedio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BitacoraCartera_bcaResultado')) ALTER TABLE BitacoraCartera DROP CONSTRAINT CK_BitacoraCartera_bcaResultado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BitacoraCartera_bcaTipoSolicitante')) ALTER TABLE BitacoraCartera DROP CONSTRAINT CK_BitacoraCartera_bcaTipoSolicitante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cartera_carEstadoCartera')) ALTER TABLE Cartera DROP CONSTRAINT CK_Cartera_carEstadoCartera;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cartera_carEstadoOperacion')) ALTER TABLE Cartera DROP CONSTRAINT CK_Cartera_carEstadoOperacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cartera_carMetodo')) ALTER TABLE Cartera DROP CONSTRAINT CK_Cartera_carMetodo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cartera_carRiesgoIncobrabilidad')) ALTER TABLE Cartera DROP CONSTRAINT CK_Cartera_carRiesgoIncobrabilidad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cartera_carTipoAccionCobro')) ALTER TABLE Cartera DROP CONSTRAINT CK_Cartera_carTipoAccionCobro;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cartera_carTipoDeuda')) ALTER TABLE Cartera DROP CONSTRAINT CK_Cartera_carTipoDeuda;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cartera_carTipoLineaCobro')) ALTER TABLE Cartera DROP CONSTRAINT CK_Cartera_carTipoLineaCobro;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cartera_carTipoSolicitante')) ALTER TABLE Cartera DROP CONSTRAINT CK_Cartera_carTipoSolicitante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CarteraDependiente_cadEstadoOperacion')) ALTER TABLE CarteraDependiente DROP CONSTRAINT CK_CarteraDependiente_cadEstadoOperacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CicloAportante_capTipoSolicitante')) ALTER TABLE CicloAportante DROP CONSTRAINT CK_CicloAportante_capTipoSolicitante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CicloCartera_ccrEstadoCiclo')) ALTER TABLE CicloCartera DROP CONSTRAINT CK_CicloCartera_ccrEstadoCiclo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CicloCartera_ccrTipoCiclo')) ALTER TABLE CicloCartera DROP CONSTRAINT CK_CicloCartera_ccrTipoCiclo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConvenioPago_copTipoSolicitante')) ALTER TABLE ConvenioPago DROP CONSTRAINT CK_ConvenioPago_copTipoSolicitante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConvenioPago_copEstadoConvenioPago')) ALTER TABLE ConvenioPago DROP CONSTRAINT CK_ConvenioPago_copEstadoConvenioPago;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConvenioPago_copMotivoAnulacion')) ALTER TABLE ConvenioPago DROP CONSTRAINT CK_ConvenioPago_copMotivoAnulacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DesafiliacionAportante_deaTipoSolicitante')) ALTER TABLE DesafiliacionAportante DROP CONSTRAINT CK_DesafiliacionAportante_deaTipoSolicitante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DesafiliacionAportante_deaTipoLineaCobro')) ALTER TABLE DesafiliacionAportante DROP CONSTRAINT CK_DesafiliacionAportante_deaTipoLineaCobro;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DetalleSolicitudGestionCobro_dsgEstado')) ALTER TABLE DetalleSolicitudGestionCobro DROP CONSTRAINT CK_DetalleSolicitudGestionCobro_dsgEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DetalleSolicitudGestionCobro_dsgResultadoPrimeraEntrega')) ALTER TABLE DetalleSolicitudGestionCobro DROP CONSTRAINT CK_DetalleSolicitudGestionCobro_dsgResultadoPrimeraEntrega;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DetalleSolicitudGestionCobro_dsgResultadoSegundaEntrega')) ALTER TABLE DetalleSolicitudGestionCobro DROP CONSTRAINT CK_DetalleSolicitudGestionCobro_dsgResultadoSegundaEntrega;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DocumentoCartera_dcaAccionCobro')) ALTER TABLE DocumentoCartera DROP CONSTRAINT CK_DocumentoCartera_dcaAccionCobro;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ExclusionCartera_excEstadoExclusionCartera')) ALTER TABLE ExclusionCartera DROP CONSTRAINT CK_ExclusionCartera_excEstadoExclusionCartera;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ExclusionCartera_excTipoExclusionCartera')) ALTER TABLE ExclusionCartera DROP CONSTRAINT CK_ExclusionCartera_excTipoExclusionCartera;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ExclusionCartera_excEstadoAntesExclusion')) ALTER TABLE ExclusionCartera DROP CONSTRAINT CK_ExclusionCartera_excEstadoAntesExclusion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ExclusionCartera_excResultado')) ALTER TABLE ExclusionCartera DROP CONSTRAINT CK_ExclusionCartera_excResultado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ExclusionCartera_excTipoSolicitante')) ALTER TABLE ExclusionCartera DROP CONSTRAINT CK_ExclusionCartera_excTipoSolicitante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_LineaCobro_lcoTipoLineaCobro')) ALTER TABLE LineaCobro DROP CONSTRAINT CK_LineaCobro_lcoTipoLineaCobro;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MotivoNoGestionCobro_mgcTipo')) ALTER TABLE MotivoNoGestionCobro DROP CONSTRAINT CK_MotivoNoGestionCobro_mgcTipo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_NotificacionPersonal_ntpActividad')) ALTER TABLE NotificacionPersonal DROP CONSTRAINT CK_NotificacionPersonal_ntpActividad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_NotificacionPersonal_ntpTipoSolicitante')) ALTER TABLE NotificacionPersonal DROP CONSTRAINT CK_NotificacionPersonal_ntpTipoSolicitante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionCartera_pacEstadoCartera')) ALTER TABLE ParametrizacionCartera DROP CONSTRAINT CK_ParametrizacionCartera_pacEstadoCartera;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionCartera_pacValorPromedioAportes')) ALTER TABLE ParametrizacionCartera DROP CONSTRAINT CK_ParametrizacionCartera_pacValorPromedioAportes;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionCartera_pacTrabajadoresActivos')) ALTER TABLE ParametrizacionCartera DROP CONSTRAINT CK_ParametrizacionCartera_pacTrabajadoresActivos;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionCartera_pacPeriodosMorosos')) ALTER TABLE ParametrizacionCartera DROP CONSTRAINT CK_ParametrizacionCartera_pacPeriodosMorosos;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionCartera_pacTipoParametrizacion')) ALTER TABLE ParametrizacionCartera DROP CONSTRAINT CK_ParametrizacionCartera_pacTipoParametrizacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionCriterioGestionCobro_pcrLineaCobro')) ALTER TABLE ParametrizacionCriterioGestionCobro DROP CONSTRAINT CK_ParametrizacionCriterioGestionCobro_pcrLineaCobro;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionCriterioGestionCobro_pcrMetodo')) ALTER TABLE ParametrizacionCriterioGestionCobro DROP CONSTRAINT CK_ParametrizacionCriterioGestionCobro_pcrMetodo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionCriterioGestionCobro_pcrAccion')) ALTER TABLE ParametrizacionCriterioGestionCobro DROP CONSTRAINT CK_ParametrizacionCriterioGestionCobro_pcrAccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionDesafiliacion_pdeLineaCobro')) ALTER TABLE ParametrizacionDesafiliacion DROP CONSTRAINT CK_ParametrizacionDesafiliacion_pdeLineaCobro;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionDesafiliacion_pdeProgramacionEjecucion')) ALTER TABLE ParametrizacionDesafiliacion DROP CONSTRAINT CK_ParametrizacionDesafiliacion_pdeProgramacionEjecucion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionDesafiliacion_pdeMetodoEnvioComunicado')) ALTER TABLE ParametrizacionDesafiliacion DROP CONSTRAINT CK_ParametrizacionDesafiliacion_pdeMetodoEnvioComunicado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionDesafiliacion_pdeSiguienteAccion')) ALTER TABLE ParametrizacionDesafiliacion DROP CONSTRAINT CK_ParametrizacionDesafiliacion_pdeSiguienteAccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PeriodoExclusionMora_pemEstadoPeriodo')) ALTER TABLE PeriodoExclusionMora DROP CONSTRAINT CK_PeriodoExclusionMora_pemEstadoPeriodo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudDesafiliacion_sodEstadoSolicitud')) ALTER TABLE SolicitudDesafiliacion DROP CONSTRAINT CK_SolicitudDesafiliacion_sodEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudFiscalizacion_sfiEstadoFiscalizacion')) ALTER TABLE SolicitudFiscalizacion DROP CONSTRAINT CK_SolicitudFiscalizacion_sfiEstadoFiscalizacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudGestionCobroElectronico_sgeTipoAccionCobro')) ALTER TABLE SolicitudGestionCobroElectronico DROP CONSTRAINT CK_SolicitudGestionCobroElectronico_sgeTipoAccionCobro;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudGestionCobroElectronico_sgeEstado')) ALTER TABLE SolicitudGestionCobroElectronico DROP CONSTRAINT CK_SolicitudGestionCobroElectronico_sgeEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudGestionCobroFisico_sgfTipoAccionCobro')) ALTER TABLE SolicitudGestionCobroFisico DROP CONSTRAINT CK_SolicitudGestionCobroFisico_sgfTipoAccionCobro;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudGestionCobroFisico_sgfEstado')) ALTER TABLE SolicitudGestionCobroFisico DROP CONSTRAINT CK_SolicitudGestionCobroFisico_sgfEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudGestionCobroManual_scmEstadoSolicitud')) ALTER TABLE SolicitudGestionCobroManual DROP CONSTRAINT CK_SolicitudGestionCobroManual_scmEstadoSolicitud;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudGestionCobroManual_scmLineaCobro')) ALTER TABLE SolicitudGestionCobroManual DROP CONSTRAINT CK_SolicitudGestionCobroManual_scmLineaCobro;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudPreventiva_sprTipoSolicitanteMovimientoAporte')) ALTER TABLE SolicitudPreventiva DROP CONSTRAINT CK_SolicitudPreventiva_sprTipoSolicitanteMovimientoAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudPreventiva_sprEstadoSolicitudPreventiva')) ALTER TABLE SolicitudPreventiva DROP CONSTRAINT CK_SolicitudPreventiva_sprEstadoSolicitudPreventiva;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudPreventiva_sprTipoGestionCartera')) ALTER TABLE SolicitudPreventiva DROP CONSTRAINT CK_SolicitudPreventiva_sprTipoGestionCartera;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Comunicado_comEstadoEnvio')) ALTER TABLE Comunicado DROP CONSTRAINT CK_Comunicado_comEstadoEnvio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Comunicado_comMedioComunicado')) ALTER TABLE Comunicado DROP CONSTRAINT CK_Comunicado_comMedioComunicado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DestinatarioComunicado_dcoEtiquetaPlantilla')) ALTER TABLE DestinatarioComunicado DROP CONSTRAINT CK_DestinatarioComunicado_dcoEtiquetaPlantilla;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DestinatarioGrupo_dgrRolContacto')) ALTER TABLE DestinatarioGrupo DROP CONSTRAINT CK_DestinatarioGrupo_dgrRolContacto;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DetalleComunicadoEnviado_dceTipoTransaccion')) ALTER TABLE DetalleComunicadoEnviado DROP CONSTRAINT CK_DetalleComunicadoEnviado_dceTipoTransaccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizaEnvioComunicado_pecProceso')) ALTER TABLE ParametrizaEnvioComunicado DROP CONSTRAINT CK_ParametrizaEnvioComunicado_pecProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizaEnvioComunicado_pecTipoCorreo')) ALTER TABLE ParametrizaEnvioComunicado DROP CONSTRAINT CK_ParametrizaEnvioComunicado_pecTipoCorreo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PlantillaComunicado_pcoEtiqueta')) ALTER TABLE PlantillaComunicado DROP CONSTRAINT CK_PlantillaComunicado_pcoEtiqueta;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_VariableComunicado_vcoTipoVariableComunicado')) ALTER TABLE VariableComunicado DROP CONSTRAINT CK_VariableComunicado_vcoTipoVariableComunicado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BeneficioEmpleador_bemMotivoInactivacion')) ALTER TABLE BeneficioEmpleador DROP CONSTRAINT CK_BeneficioEmpleador_bemMotivoInactivacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CajaCorrespondencia_ccoEstado')) ALTER TABLE CajaCorrespondencia DROP CONSTRAINT CK_CajaCorrespondencia_ccoEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DocumentoSoporte_dosTipoDocumento')) ALTER TABLE DocumentoSoporte DROP CONSTRAINT CK_DocumentoSoporte_dosTipoDocumento;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EtiquetaCorrespondenciaRadicado_eprTipoEtiqueta')) ALTER TABLE EtiquetaCorrespondenciaRadicado DROP CONSTRAINT CK_EtiquetaCorrespondenciaRadicado_eprTipoEtiqueta;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EtiquetaCorrespondenciaRadicado_eprProcedenciaEtiqueta')) ALTER TABLE EtiquetaCorrespondenciaRadicado DROP CONSTRAINT CK_EtiquetaCorrespondenciaRadicado_eprProcedenciaEtiqueta;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionNovedad_novPuntoResolucion')) ALTER TABLE ParametrizacionNovedad DROP CONSTRAINT CK_ParametrizacionNovedad_novPuntoResolucion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionNovedad_novTipoTransaccion')) ALTER TABLE ParametrizacionNovedad DROP CONSTRAINT CK_ParametrizacionNovedad_novTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionNovedad_novTipoNovedad')) ALTER TABLE ParametrizacionNovedad DROP CONSTRAINT CK_ParametrizacionNovedad_novTipoNovedad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionNovedad_novProceso')) ALTER TABLE ParametrizacionNovedad DROP CONSTRAINT CK_ParametrizacionNovedad_novProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SucursalEmpresa_sueMedioPagoSubsidioMonetario')) ALTER TABLE SucursalEmpresa DROP CONSTRAINT CK_SucursalEmpresa_sueMedioPagoSubsidioMonetario;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SucursalEmpresa_sueEstadoSucursal')) ALTER TABLE SucursalEmpresa DROP CONSTRAINT CK_SucursalEmpresa_sueEstadoSucursal;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Tarjeta_tarEstadoTarjeta')) ALTER TABLE Tarjeta DROP CONSTRAINT CK_Tarjeta_tarEstadoTarjeta;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_UbicacionEmpresa_ubeTipoUbicacion')) ALTER TABLE UbicacionEmpresa DROP CONSTRAINT CK_UbicacionEmpresa_ubeTipoUbicacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConexionOperadorInformacion_coiProtocolo')) ALTER TABLE ConexionOperadorInformacion DROP CONSTRAINT CK_ConexionOperadorInformacion_coiProtocolo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DatoTemporalParametrizacion_dtpParametrizacion')) ALTER TABLE DatoTemporalParametrizacion DROP CONSTRAINT CK_DatoTemporalParametrizacion_dtpParametrizacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DocumentoAdministracionEstadoSolicitud_daeTipoDocumentoAdjunto')) ALTER TABLE DocumentoAdministracionEstadoSolicitud DROP CONSTRAINT CK_DocumentoAdministracionEstadoSolicitud_daeTipoDocumentoAdjunto;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DocumentoAdministracionEstadoSolicitud_daeActividad')) ALTER TABLE DocumentoAdministracionEstadoSolicitud DROP CONSTRAINT CK_DocumentoAdministracionEstadoSolicitud_daeActividad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EjecucionProcesoAsincrono_epsTipoProceso')) ALTER TABLE EjecucionProcesoAsincrono DROP CONSTRAINT CK_EjecucionProcesoAsincrono_epsTipoProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EjecucionProgramada_ejpFrecuencia')) ALTER TABLE EjecucionProgramada DROP CONSTRAINT CK_EjecucionProgramada_ejpFrecuencia;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionEjecucionProgramada_pepProceso')) ALTER TABLE ParametrizacionEjecucionProgramada DROP CONSTRAINT CK_ParametrizacionEjecucionProgramada_pepProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionEjecucionProgramada_pepFrecuencia')) ALTER TABLE ParametrizacionEjecucionProgramada DROP CONSTRAINT CK_ParametrizacionEjecucionProgramada_pepFrecuencia;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionEjecucionProgramada_pepEstado')) ALTER TABLE ParametrizacionEjecucionProgramada DROP CONSTRAINT CK_ParametrizacionEjecucionProgramada_pepEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionMetodoAsignacion_pmaProceso')) ALTER TABLE ParametrizacionMetodoAsignacion DROP CONSTRAINT CK_ParametrizacionMetodoAsignacion_pmaProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionMetodoAsignacion_pmaMetodoAsignacion')) ALTER TABLE ParametrizacionMetodoAsignacion DROP CONSTRAINT CK_ParametrizacionMetodoAsignacion_pmaMetodoAsignacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Parametro_prmSubCategoriaParametro')) ALTER TABLE Parametro DROP CONSTRAINT CK_Parametro_prmSubCategoriaParametro;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ResultadoEjecucionProgramada_repResultadoEjecucion')) ALTER TABLE ResultadoEjecucionProgramada DROP CONSTRAINT CK_ResultadoEjecucionProgramada_repResultadoEjecucion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ResultadoEjecucionProgramada_repTipoResultadoProceso')) ALTER TABLE ResultadoEjecucionProgramada DROP CONSTRAINT CK_ResultadoEjecucionProgramada_repTipoResultadoProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solCanalRecepcion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solCanalRecepcion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solEstadoDocumentacion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solEstadoDocumentacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solMetodoEnvio')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solMetodoEnvio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solTipoTransaccion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solClasificacion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solClasificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solTipoRadicacion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solTipoRadicacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solResultadoProceso')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solResultadoProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueArchivoActualizacion_caaEstado')) ALTER TABLE CargueArchivoActualizacion DROP CONSTRAINT CK_CargueArchivoActualizacion_caaEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueMultipleSupervivencia_cmsEstadoCargueSupervivencia')) ALTER TABLE CargueMultipleSupervivencia DROP CONSTRAINT CK_CargueMultipleSupervivencia_cmsEstadoCargueSupervivencia;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DiferenciasCargueActualizacion_dicTipoTransaccion')) ALTER TABLE DiferenciasCargueActualizacion DROP CONSTRAINT CK_DiferenciasCargueActualizacion_dicTipoTransaccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_glosaComentarioNovedad_gcnNombreGlosaNovedad')) ALTER TABLE glosaComentarioNovedad DROP CONSTRAINT CK_glosaComentarioNovedad_gcnNombreGlosaNovedad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoNovedad_inoCausaIntentoFallido')) ALTER TABLE IntentoNovedad DROP CONSTRAINT CK_IntentoNovedad_inoCausaIntentoFallido;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoNovedad_inoTipoTransaccion')) ALTER TABLE IntentoNovedad DROP CONSTRAINT CK_IntentoNovedad_inoTipoTransaccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroNovedadFutura_rnfTipoTransaccion')) ALTER TABLE RegistroNovedadFutura DROP CONSTRAINT CK_RegistroNovedadFutura_rnfTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroNovedadFutura_rnfCanalRecepcion')) ALTER TABLE RegistroNovedadFutura DROP CONSTRAINT CK_RegistroNovedadFutura_rnfCanalRecepcion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroNovedadFutura_rnfClasificacion')) ALTER TABLE RegistroNovedadFutura DROP CONSTRAINT CK_RegistroNovedadFutura_rnfClasificacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPersonaInconsistente_rpiCanalContacto')) ALTER TABLE RegistroPersonaInconsistente DROP CONSTRAINT CK_RegistroPersonaInconsistente_rpiCanalContacto;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPersonaInconsistente_rpiEstadoGestion')) ALTER TABLE RegistroPersonaInconsistente DROP CONSTRAINT CK_RegistroPersonaInconsistente_rpiEstadoGestion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPersonaInconsistente_rpiTipoInconsistencia')) ALTER TABLE RegistroPersonaInconsistente DROP CONSTRAINT CK_RegistroPersonaInconsistente_rpiTipoInconsistencia;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudNovedad_snoEstadoSolicitud')) ALTER TABLE SolicitudNovedad DROP CONSTRAINT CK_SolicitudNovedad_snoEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Afiliado_afiCausaSinAfiliacion')) ALTER TABLE Afiliado DROP CONSTRAINT CK_Afiliado_afiCausaSinAfiliacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficiario_benTipoBeneficiario')) ALTER TABLE Beneficiario DROP CONSTRAINT CK_Beneficiario_benTipoBeneficiario;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficiario_benEstadoBeneficiarioAfiliado')) ALTER TABLE Beneficiario DROP CONSTRAINT CK_Beneficiario_benEstadoBeneficiarioAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficiario_benMotivoDesafiliacion')) ALTER TABLE Beneficiario DROP CONSTRAINT CK_Beneficiario_benMotivoDesafiliacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catTipoAfiliado')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catTipoAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catCategoriaPersona')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catCategoriaPersona;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catTipoBeneficiario')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catTipoBeneficiario;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catClasificacion')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catClasificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catMotivoCambioCategoria')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catMotivoCambioCategoria;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CondicionEspecialPersona_cepNombreCondicion')) ALTER TABLE CondicionEspecialPersona DROP CONSTRAINT CK_CondicionEspecialPersona_cepNombreCondicion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empleador_empMotivoDesafiliacion')) ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empMotivoDesafiliacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empleador_empMedioPagoSubsidioMonetario')) ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empMedioPagoSubsidioMonetario;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empleador_empEstadoEmpleador')) ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empEstadoEmpleador;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empleador_empMarcaExpulsion')) ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empMarcaExpulsion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empresa_empNaturalezaJuridica')) ALTER TABLE Empresa DROP CONSTRAINT CK_Empresa_empNaturalezaJuridica;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empresa_empMotivoFiscalizacion')) ALTER TABLE Empresa DROP CONSTRAINT CK_Empresa_empMotivoFiscalizacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadPagadora_epaEstadoEntidadPagadora')) ALTER TABLE EntidadPagadora DROP CONSTRAINT CK_EntidadPagadora_epaEstadoEntidadPagadora;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadPagadora_epaCanalComunicacion')) ALTER TABLE EntidadPagadora DROP CONSTRAINT CK_EntidadPagadora_epaCanalComunicacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadPagadora_epaMedioComunicacion')) ALTER TABLE EntidadPagadora DROP CONSTRAINT CK_EntidadPagadora_epaMedioComunicacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadPagadora_epaTipoAfiliacion')) ALTER TABLE EntidadPagadora DROP CONSTRAINT CK_EntidadPagadora_epaTipoAfiliacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MedioDePago_mdpTipo')) ALTER TABLE MedioDePago DROP CONSTRAINT CK_MedioDePago_mdpTipo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MedioCheque_mecTipoIdentificacionTitular')) ALTER TABLE MedioCheque DROP CONSTRAINT CK_MedioCheque_mecTipoIdentificacionTitular;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MedioConsignacion_mcoTipoCuenta')) ALTER TABLE MedioConsignacion DROP CONSTRAINT CK_MedioConsignacion_mcoTipoCuenta;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MedioConsignacion_mcoTipoIdentificacionTitular')) ALTER TABLE MedioConsignacion DROP CONSTRAINT CK_MedioConsignacion_mcoTipoIdentificacionTitular;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MediosPagoCCF_mpcMedioPago')) ALTER TABLE MediosPagoCCF DROP CONSTRAINT CK_MediosPagoCCF_mpcMedioPago;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MedioTarjeta_mtrEstadoTarjetaMultiservicios')) ALTER TABLE MedioTarjeta DROP CONSTRAINT CK_MedioTarjeta_mtrEstadoTarjetaMultiservicios;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MedioTarjeta_mtrSolicitudTarjeta')) ALTER TABLE MedioTarjeta DROP CONSTRAINT CK_MedioTarjeta_mtrSolicitudTarjeta;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MedioTransferencia_metTipoCuenta')) ALTER TABLE MedioTransferencia DROP CONSTRAINT CK_MedioTransferencia_metTipoCuenta;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MedioTransferencia_metTipoIdentificacionTitular')) ALTER TABLE MedioTransferencia DROP CONSTRAINT CK_MedioTransferencia_metTipoIdentificacionTitular;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Persona_perTipoIdentificacion')) ALTER TABLE Persona DROP CONSTRAINT CK_Persona_perTipoIdentificacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PersonaDetalle_pedGenero')) ALTER TABLE PersonaDetalle DROP CONSTRAINT CK_PersonaDetalle_pedGenero;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PersonaDetalle_pedNivelEducativo')) ALTER TABLE PersonaDetalle DROP CONSTRAINT CK_PersonaDetalle_pedNivelEducativo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PersonaDetalle_pedEstadoCivil')) ALTER TABLE PersonaDetalle DROP CONSTRAINT CK_PersonaDetalle_pedEstadoCivil;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaTipoAfiliado')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaTipoAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaEstadoAfiliado')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaEstadoAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaEstadoEnEntidadPagadora')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaEstadoEnEntidadPagadora;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaClaseIndependiente')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaClaseIndependiente;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaClaseTrabajador')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaClaseTrabajador;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaTipoSalario')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaTipoSalario;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaTipoContrato')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaTipoContrato;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaMotivoDesafiliacion')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaMotivoDesafiliacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaEstadoEnEntidadPagadoraPension')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaEstadoEnEntidadPagadoraPension;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaMarcaExpulsion')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaMarcaExpulsion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolContactoEmpleador_rceTipoRolContactoEmpleador')) ALTER TABLE RolContactoEmpleador DROP CONSTRAINT CK_RolContactoEmpleador_rceTipoRolContactoEmpleador;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ArchivoConsumosAnibol_acnTipoCargue')) ALTER TABLE ArchivoConsumosAnibol DROP CONSTRAINT CK_ArchivoConsumosAnibol_acnTipoCargue;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ArchivoConsumosAnibol_acnEstadoArchivo')) ALTER TABLE ArchivoConsumosAnibol DROP CONSTRAINT CK_ArchivoConsumosAnibol_acnEstadoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ArchivoConsumosAnibol_acnResultadoValidacionEstructura')) ALTER TABLE ArchivoConsumosAnibol DROP CONSTRAINT CK_ArchivoConsumosAnibol_acnResultadoValidacionEstructura;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ArchivoConsumosAnibol_acnResultadoValidacionContenido')) ALTER TABLE ArchivoConsumosAnibol DROP CONSTRAINT CK_ArchivoConsumosAnibol_acnResultadoValidacionContenido;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ArchivoConsumosAnibol_acnTipoInconsistenciaArchivo')) ALTER TABLE ArchivoConsumosAnibol DROP CONSTRAINT CK_ArchivoConsumosAnibol_acnTipoInconsistenciaArchivo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CampoArchivoConsumosAnibol_caaInconsistenciaContenidoDetectada')) ALTER TABLE CampoArchivoConsumosAnibol DROP CONSTRAINT CK_CampoArchivoConsumosAnibol_caaInconsistenciaContenidoDetectada;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroArchivoConsumosAnibol_racTipoRegistroArchivoConsumo')) ALTER TABLE RegistroArchivoConsumosAnibol DROP CONSTRAINT CK_RegistroArchivoConsumosAnibol_racTipoRegistroArchivoConsumo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroArchivoConsumosAnibol_racEstadoRegistro')) ALTER TABLE RegistroArchivoConsumosAnibol DROP CONSTRAINT CK_RegistroArchivoConsumosAnibol_racEstadoRegistro;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroArchivoConsumosAnibol_racTipoInconsistenciaResultadoValidacion')) ALTER TABLE RegistroArchivoConsumosAnibol DROP CONSTRAINT CK_RegistroArchivoConsumosAnibol_racTipoInconsistenciaResultadoValidacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ArchivoRetiroTerceroPagador_arrEstado')) ALTER TABLE ArchivoRetiroTerceroPagador DROP CONSTRAINT CK_ArchivoRetiroTerceroPagador_arrEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CampoArchivoRetiroTerceroPagador_carDescripcionCampo')) ALTER TABLE CampoArchivoRetiroTerceroPagador DROP CONSTRAINT CK_CampoArchivoRetiroTerceroPagador_carDescripcionCampo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CampoArchivoRetiroTerceroPagador_carInconsistencia')) ALTER TABLE CampoArchivoRetiroTerceroPagador DROP CONSTRAINT CK_CampoArchivoRetiroTerceroPagador_carInconsistencia;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConvenioTerceroPagador_conEstado')) ALTER TABLE ConvenioTerceroPagador DROP CONSTRAINT CK_ConvenioTerceroPagador_conEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConvenioTerceroPagador_conMedioDePago')) ALTER TABLE ConvenioTerceroPagador DROP CONSTRAINT CK_ConvenioTerceroPagador_conMedioDePago;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CuentaAdministradorSubsidio_casTipoTransaccionSubsidio')) ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT CK_CuentaAdministradorSubsidio_casTipoTransaccionSubsidio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CuentaAdministradorSubsidio_casEstadoLiquidacionSubsidio ')) ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT CK_CuentaAdministradorSubsidio_casEstadoLiquidacionSubsidio ;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CuentaAdministradorSubsidio_casEstadoTransaccionSubsidio')) ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT CK_CuentaAdministradorSubsidio_casEstadoTransaccionSubsidio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CuentaAdministradorSubsidio_casOrigenTransaccion')) ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT CK_CuentaAdministradorSubsidio_casOrigenTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CuentaAdministradorSubsidio_casMedioDePagoTransaccion')) ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT CK_CuentaAdministradorSubsidio_casMedioDePagoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CuentaAdministradorSubsidio_casTipoCuentaAdmonSubsidio')) ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT CK_CuentaAdministradorSubsidio_casTipoCuentaAdmonSubsidio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CuentaAdministradorSubsidio_casTipoIdentificacionTitularCuentaAdmonSubsidio')) ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT CK_CuentaAdministradorSubsidio_casTipoIdentificacionTitularCuentaAdmonSubsidio;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DetalleSubsidioAsignado_dsaEstado')) ALTER TABLE DetalleSubsidioAsignado DROP CONSTRAINT CK_DetalleSubsidioAsignado_dsaEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DetalleSubsidioAsignado_dsaMotivoAnulacion')) ALTER TABLE DetalleSubsidioAsignado DROP CONSTRAINT CK_DetalleSubsidioAsignado_dsaMotivoAnulacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DetalleSubsidioAsignado_dsaOrigenRegistroSubsidio')) ALTER TABLE DetalleSubsidioAsignado DROP CONSTRAINT CK_DetalleSubsidioAsignado_dsaOrigenRegistroSubsidio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DetalleSubsidioAsignado_dsaTipoliquidacionSubsidio')) ALTER TABLE DetalleSubsidioAsignado DROP CONSTRAINT CK_DetalleSubsidioAsignado_dsaTipoliquidacionSubsidio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DetalleSubsidioAsignado_dsaTipoCuotaSubsidio')) ALTER TABLE DetalleSubsidioAsignado DROP CONSTRAINT CK_DetalleSubsidioAsignado_dsaTipoCuotaSubsidio;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroArchivoRetiroTerceroPagador_rarEstado')) ALTER TABLE RegistroArchivoRetiroTerceroPagador DROP CONSTRAINT CK_RegistroArchivoRetiroTerceroPagador_rarEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroArchivoRetiroTerceroPagador_rarTipoIdentificacionAdminSubsidio')) ALTER TABLE RegistroArchivoRetiroTerceroPagador DROP CONSTRAINT CK_RegistroArchivoRetiroTerceroPagador_rarTipoIdentificacionAdminSubsidio;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroOperacionTransaccionSubsidio_rotTipoOperacion')) ALTER TABLE RegistroOperacionTransaccionSubsidio DROP CONSTRAINT CK_RegistroOperacionTransaccionSubsidio_rotTipoOperacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroOperacionTransaccionSubsidio_rotTipoTransaccion')) ALTER TABLE RegistroOperacionTransaccionSubsidio DROP CONSTRAINT CK_RegistroOperacionTransaccionSubsidio_rotTipoTransaccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAnulacionSubsidioCobrado_sasEstadoSolicitud')) ALTER TABLE SolicitudAnulacionSubsidioCobrado DROP CONSTRAINT CK_SolicitudAnulacionSubsidioCobrado_sasEstadoSolicitud;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAnulacionSubsidioCobrado_sasMotivoAnulacion')) ALTER TABLE SolicitudAnulacionSubsidioCobrado DROP CONSTRAINT CK_SolicitudAnulacionSubsidioCobrado_sasMotivoAnulacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TransaccionesFallidasSubsidio_tfsCanal')) ALTER TABLE TransaccionesFallidasSubsidio DROP CONSTRAINT CK_TransaccionesFallidasSubsidio_tfsCanal;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TransaccionesFallidasSubsidio_tfsEstadoResolucion')) ALTER TABLE TransaccionesFallidasSubsidio DROP CONSTRAINT CK_TransaccionesFallidasSubsidio_tfsEstadoResolucion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TransaccionesFallidasSubsidio_tfsResultadoGestion')) ALTER TABLE TransaccionesFallidasSubsidio DROP CONSTRAINT CK_TransaccionesFallidasSubsidio_tfsResultadoGestion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TransaccionesFallidasSubsidio_tfsTipoTransaccionPagoSubsidio')) ALTER TABLE TransaccionesFallidasSubsidio DROP CONSTRAINT CK_TransaccionesFallidasSubsidio_tfsTipoTransaccionPagoSubsidio;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConsolaEstadoCargueMasivo_cecTipoProcesoMasivo')) ALTER TABLE ConsolaEstadoCargueMasivo DROP CONSTRAINT CK_ConsolaEstadoCargueMasivo_cecTipoProcesoMasivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConsolaEstadoCargueMasivo_cecEstadoCargueMasivo')) ALTER TABLE ConsolaEstadoCargueMasivo DROP CONSTRAINT CK_ConsolaEstadoCargueMasivo_cecEstadoCargueMasivo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_HistoriaResultadoValidacion_hrvValidacion')) ALTER TABLE HistoriaResultadoValidacion DROP CONSTRAINT CK_HistoriaResultadoValidacion_hrvValidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_HistoriaResultadoValidacion_hrvResultado')) ALTER TABLE HistoriaResultadoValidacion DROP CONSTRAINT CK_HistoriaResultadoValidacion_hrvResultado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_HistoriaResultadoValidacion_hrvTipoExepcion')) ALTER TABLE HistoriaResultadoValidacion DROP CONSTRAINT CK_HistoriaResultadoValidacion_hrvTipoExepcion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ValidacionProceso_vapValidacion')) ALTER TABLE ValidacionProceso DROP CONSTRAINT CK_ValidacionProceso_vapValidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ValidacionProceso_vapProceso')) ALTER TABLE ValidacionProceso DROP CONSTRAINT CK_ValidacionProceso_vapProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ValidacionProceso_vapEstadoProceso')) ALTER TABLE ValidacionProceso DROP CONSTRAINT CK_ValidacionProceso_vapEstadoProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_NotificacionEnviada_noeEstadoEnvioNot')) ALTER TABLE NotificacionEnviada DROP CONSTRAINT CK_NotificacionEnviada_noeEstadoEnvioNot;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ListaEspecialRevision_lerTipoIdentificacion')) ALTER TABLE ListaEspecialRevision DROP CONSTRAINT CK_ListaEspecialRevision_lerTipoIdentificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ListaEspecialRevision_lerRazonInclusion')) ALTER TABLE ListaEspecialRevision DROP CONSTRAINT CK_ListaEspecialRevision_lerRazonInclusion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ListaEspecialRevision_lerEstado')) ALTER TABLE ListaEspecialRevision DROP CONSTRAINT CK_ListaEspecialRevision_lerEstado;

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

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficio_befTipoBeneficio')) ALTER TABLE Beneficio DROP CONSTRAINT CK_Beneficio_befTipoBeneficio;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Infraestructura_infAreaGeografica')) ALTER TABLE Infraestructura DROP CONSTRAINT CK_Infraestructura_infAreaGeografica;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TipoInfraestructura_tifMedidaCapacidad')) ALTER TABLE TipoInfraestructura DROP CONSTRAINT CK_TipoInfraestructura_tifMedidaCapacidad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CajaCompensacion_ccfMetodoGeneracionEtiquetas')) ALTER TABLE CajaCompensacion DROP CONSTRAINT CK_CajaCompensacion_ccfMetodoGeneracionEtiquetas;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Requisito_reqEstado')) ALTER TABLE Requisito DROP CONSTRAINT CK_Requisito_reqEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsClasificacion')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsClasificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsTipoTransaccion')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsEstado')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsTipoRequisito')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsTipoRequisito;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_GradoAcademico_graNivelEducativo')) ALTER TABLE GradoAcademico DROP CONSTRAINT CK_GradoAcademico_graNivelEducativo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadDescuento_endTipo')) ALTER TABLE EntidadDescuento DROP CONSTRAINT CK_EntidadDescuento_endTipo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadDescuento_endEstado')) ALTER TABLE EntidadDescuento DROP CONSTRAINT CK_EntidadDescuento_endEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PeriodoLiquidacion_pelTipoPeriodo')) ALTER TABLE PeriodoLiquidacion DROP CONSTRAINT CK_PeriodoLiquidacion_pelTipoPeriodo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsTipoLiquidacion')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsTipoLiquidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsTipoLiquidacionEspecifica')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsTipoLiquidacionEspecifica;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsEstadoLiquidacion')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsEstadoLiquidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsTipoEjecucionProceso')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsTipoEjecucionProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudLiquidacionSubsidio_slsRazonRechazoLiquidacion')) ALTER TABLE SolicitudLiquidacionSubsidio DROP CONSTRAINT CK_SolicitudLiquidacionSubsidio_slsRazonRechazoLiquidacion;