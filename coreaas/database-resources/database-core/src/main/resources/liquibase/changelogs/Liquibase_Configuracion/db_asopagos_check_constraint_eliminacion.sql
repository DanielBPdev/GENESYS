--liquibase formatted sql
--changeset lepaez:01 runAlways:true runOnChange:true
--comment: Creación de borrado de CKs 2017-03-29 18:57
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueAfiliacionMultiple_camTipoSolicitante')) ALTER TABLE CargueAfiliacionMultiple DROP CONSTRAINT CK_CargueAfiliacionMultiple_camTipoSolicitante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueAfiliacionMultiple_camClasificacion')) ALTER TABLE CargueAfiliacionMultiple DROP CONSTRAINT CK_CargueAfiliacionMultiple_camClasificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueAfiliacionMultiple_camTipoTransaccion')) ALTER TABLE CargueAfiliacionMultiple DROP CONSTRAINT CK_CargueAfiliacionMultiple_camTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueAfiliacionMultiple_camProceso')) ALTER TABLE CargueAfiliacionMultiple DROP CONSTRAINT CK_CargueAfiliacionMultiple_camProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CargueAfiliacionMultiple_camEstado')) ALTER TABLE CargueAfiliacionMultiple DROP CONSTRAINT CK_CargueAfiliacionMultiple_camEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_DocumentoEntidadPagadora_dpgTipoDocumento')) ALTER TABLE DocumentoEntidadPagadora DROP CONSTRAINT CK_DocumentoEntidadPagadora_dpgTipoDocumento;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EscalamientoSolicitud_esoResultadoAnalista')) ALTER TABLE EscalamientoSolicitud DROP CONSTRAINT CK_EscalamientoSolicitud_esoResultadoAnalista;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoAfiliacion_iafCausaIntentoFallido')) ALTER TABLE IntentoAfiliacion DROP CONSTRAINT CK_IntentoAfiliacion_iafCausaIntentoFallido;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoAfiliacion_iafTipoTransaccion')) ALTER TABLE IntentoAfiliacion DROP CONSTRAINT CK_IntentoAfiliacion_iafTipoTransaccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ItemChequeo_ichEstadoRequisito')) ALTER TABLE ItemChequeo DROP CONSTRAINT CK_ItemChequeo_ichEstadoRequisito;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ItemChequeo_ichFormatoEntregaDocumento')) ALTER TABLE ItemChequeo DROP CONSTRAINT CK_ItemChequeo_ichFormatoEntregaDocumento;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncTipoProductoNoConforme')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncTipoProductoNoConforme;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncTipoError')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncTipoError;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncResultadoGestion')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncResultadoGestion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncResultadoRevisionBack')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncResultadoRevisionBack;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ProductoNoConforme_pncResultadoRevisionBack2')) ALTER TABLE ProductoNoConforme DROP CONSTRAINT CK_ProductoNoConforme_pncResultadoRevisionBack2;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAfiliaciEmpleador_saeEstadoSolicitud')) ALTER TABLE SolicitudAfiliaciEmpleador DROP CONSTRAINT CK_SolicitudAfiliaciEmpleador_saeEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAfiliacionPersona_sapEstadoSolicitud')) ALTER TABLE SolicitudAfiliacionPersona DROP CONSTRAINT CK_SolicitudAfiliacionPersona_sapEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAsociacionPersonaEntidadPagadora_soaEstado')) ALTER TABLE SolicitudAsociacionPersonaEntidadPagadora DROP CONSTRAINT CK_SolicitudAsociacionPersonaEntidadPagadora_soaEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudAsociacionPersonaEntidadPagadora_soaTipoGestion')) ALTER TABLE SolicitudAsociacionPersonaEntidadPagadora DROP CONSTRAINT CK_SolicitudAsociacionPersonaEntidadPagadora_soaTipoGestion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Aportante_apoEstadoAportanteInicial')) ALTER TABLE Aportante DROP CONSTRAINT CK_Aportante_apoEstadoAportanteInicial;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Aportante_apoEstadoAportanteFinal')) ALTER TABLE Aportante DROP CONSTRAINT CK_Aportante_apoEstadoAportanteFinal;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Aportante_apoEstadoAporteAportante')) ALTER TABLE Aportante DROP CONSTRAINT CK_Aportante_apoEstadoAporteAportante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Aportante_apoEstadoRegistroAporteAportante')) ALTER TABLE Aportante DROP CONSTRAINT CK_Aportante_apoEstadoRegistroAporteAportante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdEstadoAporteRecaudo')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdEstadoAporteRecaudo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdEstadoAporteAjuste')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdEstadoAporteAjuste;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetallado_apdEstadoRegistroAporte')) ALTER TABLE AporteDetallado DROP CONSTRAINT CK_AporteDetallado_apdEstadoRegistroAporte;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoAporteRecaudo')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoAporteRecaudo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoAporteAjuste')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoAporteAjuste;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoRegistroAporte')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoValidacionV0')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoValidacionV0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoValidacionV1')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoValidacionV1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoValidacionV2')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoValidacionV2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteDetalladoSimulado_adsEstadoValidacionV3')) ALTER TABLE AporteDetalladoSimulado DROP CONSTRAINT CK_AporteDetalladoSimulado_adsEstadoValidacionV3;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgModalidadPlanilla')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgModalidadPlanilla;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgModalidadRecaudoAporte')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgModalidadRecaudoAporte;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneralSimulado_agsModalidadPlanilla')) ALTER TABLE AporteGeneralSimulado DROP CONSTRAINT CK_AporteGeneralSimulado_agsModalidadPlanilla;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneralSimulado_agsModalidadRecaudoAporte')) ALTER TABLE AporteGeneralSimulado DROP CONSTRAINT CK_AporteGeneralSimulado_agsModalidadRecaudoAporte;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cotizante_cotTipoCotizante')) ALTER TABLE Cotizante DROP CONSTRAINT CK_Cotizante_cotTipoCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cotizante_cotEstadoCotizanteInicial')) ALTER TABLE Cotizante DROP CONSTRAINT CK_Cotizante_cotEstadoCotizanteInicial;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cotizante_cotEstadoCotizanteFinal')) ALTER TABLE Cotizante DROP CONSTRAINT CK_Cotizante_cotEstadoCotizanteFinal;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cotizante_cotEstadoAporteCotizante')) ALTER TABLE Cotizante DROP CONSTRAINT CK_Cotizante_cotEstadoAporteCotizante;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Cotizante_cotEstadoRegistroAporteCotizante')) ALTER TABLE Cotizante DROP CONSTRAINT CK_Cotizante_cotEstadoRegistroAporteCotizante;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MovimientoAjusteAporte_maaTipoMovimientoRecaudoAporte')) ALTER TABLE MovimientoAjusteAporte DROP CONSTRAINT CK_MovimientoAjusteAporte_maaTipoMovimientoRecaudoAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MovimientoAjusteAporte_maaTipoAjusteMovimientoAporte')) ALTER TABLE MovimientoAjusteAporte DROP CONSTRAINT CK_MovimientoAjusteAporte_maaTipoAjusteMovimientoAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_MovimientoAjusteAporte_maaEstadoAjusteRegAporte')) ALTER TABLE MovimientoAjusteAporte DROP CONSTRAINT CK_MovimientoAjusteAporte_maaEstadoAjusteRegAporte;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Comunicado_comEstadoEnvio')) ALTER TABLE Comunicado DROP CONSTRAINT CK_Comunicado_comEstadoEnvio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Comunicado_comMedioComunicado')) ALTER TABLE Comunicado DROP CONSTRAINT CK_Comunicado_comMedioComunicado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ComunicadoEnviado_coeEstadoEnvio')) ALTER TABLE ComunicadoEnviado DROP CONSTRAINT CK_ComunicadoEnviado_coeEstadoEnvio;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizaEnvioComunicado_pecProceso')) ALTER TABLE ParametrizaEnvioComunicado DROP CONSTRAINT CK_ParametrizaEnvioComunicado_pecProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizaEnvioComunicado_pecTipoCorreo')) ALTER TABLE ParametrizaEnvioComunicado DROP CONSTRAINT CK_ParametrizaEnvioComunicado_pecTipoCorreo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PlantillaComunicado_pcoEtiqueta')) ALTER TABLE PlantillaComunicado DROP CONSTRAINT CK_PlantillaComunicado_pcoEtiqueta;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_VariableComunicado_vcoTipoVariableComunicado')) ALTER TABLE VariableComunicado DROP CONSTRAINT CK_VariableComunicado_vcoTipoVariableComunicado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BeneficioEmpleador_bemTipoBeneficio')) ALTER TABLE BeneficioEmpleador DROP CONSTRAINT CK_BeneficioEmpleador_bemTipoBeneficio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BeneficioEmpleador_bemMotivoInactivacionBeneficioLey1429')) ALTER TABLE BeneficioEmpleador DROP CONSTRAINT CK_BeneficioEmpleador_bemMotivoInactivacionBeneficioLey1429;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BeneficioEmpleador_bemMotivoInactivacionBeneficioLey590')) ALTER TABLE BeneficioEmpleador DROP CONSTRAINT CK_BeneficioEmpleador_bemMotivoInactivacionBeneficioLey590;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CajaCorrespondencia_ccoEstado')) ALTER TABLE CajaCorrespondencia DROP CONSTRAINT CK_CajaCorrespondencia_ccoEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EtiquetaCorrespondenciaRadicado_eprTipoEtiqueta')) ALTER TABLE EtiquetaCorrespondenciaRadicado DROP CONSTRAINT CK_EtiquetaCorrespondenciaRadicado_eprTipoEtiqueta;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EtiquetaCorrespondenciaRadicado_eprProcedenciaEtiqueta')) ALTER TABLE EtiquetaCorrespondenciaRadicado DROP CONSTRAINT CK_EtiquetaCorrespondenciaRadicado_eprProcedenciaEtiqueta;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Novedad_novPuntoResolucion')) ALTER TABLE Novedad DROP CONSTRAINT CK_Novedad_novPuntoResolucion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Novedad_novTipoTransaccion')) ALTER TABLE Novedad DROP CONSTRAINT CK_Novedad_novTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Novedad_novTipoNovedad')) ALTER TABLE Novedad DROP CONSTRAINT CK_Novedad_novTipoNovedad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Novedad_novProceso')) ALTER TABLE Novedad DROP CONSTRAINT CK_Novedad_novProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SucursalEmpresa_sueEstadoSucursal')) ALTER TABLE SucursalEmpresa DROP CONSTRAINT CK_SucursalEmpresa_sueEstadoSucursal;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Tarjeta_tarEstadoTarjeta')) ALTER TABLE Tarjeta DROP CONSTRAINT CK_Tarjeta_tarEstadoTarjeta;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_UbicacionEmpresa_ubeTipoUbicacion')) ALTER TABLE UbicacionEmpresa DROP CONSTRAINT CK_UbicacionEmpresa_ubeTipoUbicacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionEjecucionProgramada_pepFrecuencia')) ALTER TABLE ParametrizacionEjecucionProgramada DROP CONSTRAINT CK_ParametrizacionEjecucionProgramada_pepFrecuencia;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ParametrizacionMetodoAsignacion_pmaMetodoAsignacion')) ALTER TABLE ParametrizacionMetodoAsignacion DROP CONSTRAINT CK_ParametrizacionMetodoAsignacion_pmaMetodoAsignacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ResultadoEjecucionProgramada_repResultadoEjecucion')) ALTER TABLE ResultadoEjecucionProgramada DROP CONSTRAINT CK_ResultadoEjecucionProgramada_repResultadoEjecucion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solCanalRecepcion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solCanalRecepcion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solEstadoDocumentacion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solEstadoDocumentacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solMetodoEnvio')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solMetodoEnvio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solTipoTransaccion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solClasificacion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solClasificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solTipoRadicacion')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solTipoRadicacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Solicitud_solResultadoProceso')) ALTER TABLE Solicitud DROP CONSTRAINT CK_Solicitud_solResultadoProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_glosaComentarioNovedad_gcnNombreGlosaNovedad')) ALTER TABLE glosaComentarioNovedad DROP CONSTRAINT CK_glosaComentarioNovedad_gcnNombreGlosaNovedad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoNovedad_inoCausaIntentoFallido')) ALTER TABLE IntentoNovedad DROP CONSTRAINT CK_IntentoNovedad_inoCausaIntentoFallido;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_IntentoNovedad_inoTipoTransaccion')) ALTER TABLE IntentoNovedad DROP CONSTRAINT CK_IntentoNovedad_inoTipoTransaccion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnTipoNovedadPila')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnTipoNovedadPila;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnTipoTransaccionNovedad')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnTipoTransaccionNovedad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnMarcaNovedad')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnMarcaNovedad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnCanalRecepcion')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnCanalRecepcion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnEstadoNovedad')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnEstadoNovedad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SolicitudNovedad_snoEstadoSolicitud')) ALTER TABLE SolicitudNovedad DROP CONSTRAINT CK_SolicitudNovedad_snoEstadoSolicitud;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Afiliado_afiEstadoAfiliadoCaja')) ALTER TABLE Afiliado DROP CONSTRAINT CK_Afiliado_afiEstadoAfiliadoCaja;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficiario_benTipoBeneficiario')) ALTER TABLE Beneficiario DROP CONSTRAINT CK_Beneficiario_benTipoBeneficiario;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficiario_benEstadoBeneficiarioCaja')) ALTER TABLE Beneficiario DROP CONSTRAINT CK_Beneficiario_benEstadoBeneficiarioCaja;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficiario_benEstadoBeneficiarioAfiliado')) ALTER TABLE Beneficiario DROP CONSTRAINT CK_Beneficiario_benEstadoBeneficiarioAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficiario_benMotivoDesafiliacion')) ALTER TABLE Beneficiario DROP CONSTRAINT CK_Beneficiario_benMotivoDesafiliacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catTipoAfiliado')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catTipoAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catCategoriaPersona')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catCategoriaPersona;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catTipoBeneficiario')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catTipoBeneficiario;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catClasificacion')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catClasificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Categoria_catMotivoCambioCategoria')) ALTER TABLE Categoria DROP CONSTRAINT CK_Categoria_catMotivoCambioCategoria;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empleador_empEstadoAportesEmpleador')) ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empEstadoAportesEmpleador;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empleador_empMotivoDesafiliacion')) ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empMotivoDesafiliacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empleador_empEstadoEmpleador')) ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empEstadoEmpleador;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empresa_empNaturalezaJuridica')) ALTER TABLE Empresa DROP CONSTRAINT CK_Empresa_empNaturalezaJuridica;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadPagadora_epaEstadoEntidadPagadora')) ALTER TABLE EntidadPagadora DROP CONSTRAINT CK_EntidadPagadora_epaEstadoEntidadPagadora;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadPagadora_epaCanalComunicacion')) ALTER TABLE EntidadPagadora DROP CONSTRAINT CK_EntidadPagadora_epaCanalComunicacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadPagadora_epaMedioComunicacion')) ALTER TABLE EntidadPagadora DROP CONSTRAINT CK_EntidadPagadora_epaMedioComunicacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_EntidadPagadora_epaTipoAfiliacion')) ALTER TABLE EntidadPagadora DROP CONSTRAINT CK_EntidadPagadora_epaTipoAfiliacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Persona_perTipoIdentificacion')) ALTER TABLE Persona DROP CONSTRAINT CK_Persona_perTipoIdentificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Persona_perGenero')) ALTER TABLE Persona DROP CONSTRAINT CK_Persona_perGenero;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Persona_perNivelEducativo')) ALTER TABLE Persona DROP CONSTRAINT CK_Persona_perNivelEducativo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Persona_perEstadoCivil')) ALTER TABLE Persona DROP CONSTRAINT CK_Persona_perEstadoCivil;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaTipoAfiliado')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaTipoAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaEstadoAfiliado')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaEstadoAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaEstadoEnEntidadPagadora')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaEstadoEnEntidadPagadora;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaClaseIndependiente')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaClaseIndependiente;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaClaseTrabajador')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaClaseTrabajador;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaTipoSalario')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaTipoSalario;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaTipoContrato')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaTipoContrato;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaMotivoDesafiliacion')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaMotivoDesafiliacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolContactoEmpleador_rceTipoRolContactoEmpleador')) ALTER TABLE RolContactoEmpleador DROP CONSTRAINT CK_RolContactoEmpleador_rceTipoRolContactoEmpleador;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoFRegistro6_pf6EstadoConciliacion')) ALTER TABLE PilaArchivoFRegistro6 DROP CONSTRAINT CK_PilaArchivoFRegistro6_pf6EstadoConciliacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoIPRegistro2_ip2MarcaValRegistroAporte')) ALTER TABLE PilaArchivoIPRegistro2 DROP CONSTRAINT CK_PilaArchivoIPRegistro2_ip2MarcaValRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoIPRegistro2_ip2EstadoRegistroAporte')) ALTER TABLE PilaArchivoIPRegistro2 DROP CONSTRAINT CK_PilaArchivoIPRegistro2_ip2EstadoRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoIPRegistro2_ip2EstadoValidacionV1')) ALTER TABLE PilaArchivoIPRegistro2 DROP CONSTRAINT CK_PilaArchivoIPRegistro2_ip2EstadoValidacionV1;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoIRegistro2_pi2MarcaValRegistroAporte')) ALTER TABLE PilaArchivoIRegistro2 DROP CONSTRAINT CK_PilaArchivoIRegistro2_pi2MarcaValRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoIRegistro2_pi2EstadoRegistroAporte')) ALTER TABLE PilaArchivoIRegistro2 DROP CONSTRAINT CK_PilaArchivoIRegistro2_pi2EstadoRegistroAporte;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoIRegistro2_pi2EstadoValidacionV0')) ALTER TABLE PilaArchivoIRegistro2 DROP CONSTRAINT CK_PilaArchivoIRegistro2_pi2EstadoValidacionV0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoIRegistro2_pi2EstadoValidacionV1')) ALTER TABLE PilaArchivoIRegistro2 DROP CONSTRAINT CK_PilaArchivoIRegistro2_pi2EstadoValidacionV1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoIRegistro2_pi2EstadoValidacionV2')) ALTER TABLE PilaArchivoIRegistro2 DROP CONSTRAINT CK_PilaArchivoIRegistro2_pi2EstadoValidacionV2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaArchivoIRegistro2_pi2EstadoValidacionV3')) ALTER TABLE PilaArchivoIRegistro2 DROP CONSTRAINT CK_PilaArchivoIRegistro2_pi2EstadoValidacionV3;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaClasificacionAportante_pcaComparacion')) ALTER TABLE PilaClasificacionAportante DROP CONSTRAINT CK_PilaClasificacionAportante_pcaComparacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConexionOperadorInformacion_coiProtocolo')) ALTER TABLE ConexionOperadorInformacion DROP CONSTRAINT CK_ConexionOperadorInformacion_coiProtocolo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEjecucionProgramada_pepFrecuencia')) ALTER TABLE PilaEjecucionProgramada DROP CONSTRAINT CK_PilaEjecucionProgramada_pepFrecuencia;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevTipoArchivo')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevTipoError')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevTipoError;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaErrorValidacionLog_pevBloqueValidacion')) ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT CK_PilaErrorValidacionLog_pevBloqueValidacion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebTipoArchivo')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque0')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque0')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque1')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque1')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque2')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque2')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque2;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque3')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque3;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque3')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque3;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque4')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque4;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque4')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque4;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque5')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque5;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque5')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque5;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebEstadoBloque6')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebEstadoBloque6;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloque_pebAccionBloque6')) ALTER TABLE PilaEstadoBloque DROP CONSTRAINT CK_PilaEstadoBloque_pebAccionBloque6;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoEstadoBloque0')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoEstadoBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoAccionBloque0')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoAccionBloque0;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoEstadoBloque1')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoEstadoBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoAccionBloque1')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoAccionBloque1;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoEstadoBloque6')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoEstadoBloque6;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaEstadoBloqueOF_peoAccionBloque6')) ALTER TABLE PilaEstadoBloqueOF DROP CONSTRAINT CK_PilaEstadoBloqueOF_peoAccionBloque6;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndiceCorreccionPlanilla_picAccion')) ALTER TABLE PilaIndiceCorreccionPlanilla DROP CONSTRAINT CK_PilaIndiceCorreccionPlanilla_picAccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndiceCorreccionPlanilla_picEstadoArchivoAfectado')) ALTER TABLE PilaIndiceCorreccionPlanilla DROP CONSTRAINT CK_PilaIndiceCorreccionPlanilla_picEstadoArchivoAfectado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanilla_pipTipoArchivo')) ALTER TABLE PilaIndicePlanilla DROP CONSTRAINT CK_PilaIndicePlanilla_pipTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanilla_pipEstadoArchivo')) ALTER TABLE PilaIndicePlanilla DROP CONSTRAINT CK_PilaIndicePlanilla_pipEstadoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanilla_pipTipoCargaArchivo')) ALTER TABLE PilaIndicePlanilla DROP CONSTRAINT CK_PilaIndicePlanilla_pipTipoCargaArchivo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanillaOF_pioTipoArchivo')) ALTER TABLE PilaIndicePlanillaOF DROP CONSTRAINT CK_PilaIndicePlanillaOF_pioTipoArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanillaOF_pioTipoCargaArchivo')) ALTER TABLE PilaIndicePlanillaOF DROP CONSTRAINT CK_PilaIndicePlanillaOF_pioTipoCargaArchivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaIndicePlanillaOF_pioEstadoArchivo')) ALTER TABLE PilaIndicePlanillaOF DROP CONSTRAINT CK_PilaIndicePlanillaOF_pioEstadoArchivo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaNormatividadFechaVencimiento_pfvTipoFecha')) ALTER TABLE PilaNormatividadFechaVencimiento DROP CONSTRAINT CK_PilaNormatividadFechaVencimiento_pfvTipoFecha;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaOportunidadPresentacionPlanilla_popOportunidad')) ALTER TABLE PilaOportunidadPresentacionPlanilla DROP CONSTRAINT CK_PilaOportunidadPresentacionPlanilla_popOportunidad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaPasoValores_ppvTipoPlanilla')) ALTER TABLE PilaPasoValores DROP CONSTRAINT CK_PilaPasoValores_ppvTipoPlanilla;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaPasoValores_ppvBloque')) ALTER TABLE PilaPasoValores DROP CONSTRAINT CK_PilaPasoValores_ppvBloque;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaProceso_pprTipoProceso')) ALTER TABLE PilaProceso DROP CONSTRAINT CK_PilaProceso_pprTipoProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaProceso_pprEstadoProceso')) ALTER TABLE PilaProceso DROP CONSTRAINT CK_PilaProceso_pprEstadoProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_PilaTasasInteresMora_ptiTipoInteres')) ALTER TABLE PilaTasasInteresMora DROP CONSTRAINT CK_PilaTasasInteresMora_ptiTipoInteres;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CajaCompensacion_ccfMetodoGeneracionEtiquetas')) ALTER TABLE CajaCompensacion DROP CONSTRAINT CK_CajaCompensacion_ccfMetodoGeneracionEtiquetas;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConsolaEstadoCargueMasivo_cecTipoProcesoMasivo')) ALTER TABLE ConsolaEstadoCargueMasivo DROP CONSTRAINT CK_ConsolaEstadoCargueMasivo_cecTipoProcesoMasivo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ConsolaEstadoCargueMasivo_cecEstadoCargueMasivo')) ALTER TABLE ConsolaEstadoCargueMasivo DROP CONSTRAINT CK_ConsolaEstadoCargueMasivo_cecEstadoCargueMasivo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_HistoriaResultadoValidacion_hrvValidacion')) ALTER TABLE HistoriaResultadoValidacion DROP CONSTRAINT CK_HistoriaResultadoValidacion_hrvValidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_HistoriaResultadoValidacion_hrvResultado')) ALTER TABLE HistoriaResultadoValidacion DROP CONSTRAINT CK_HistoriaResultadoValidacion_hrvResultado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_HistoriaResultadoValidacion_hrvTipoExepcion')) ALTER TABLE HistoriaResultadoValidacion DROP CONSTRAINT CK_HistoriaResultadoValidacion_hrvTipoExepcion;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Requisito_reqEstado')) ALTER TABLE Requisito DROP CONSTRAINT CK_Requisito_reqEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Requisito_reqTipoRequisito')) ALTER TABLE Requisito DROP CONSTRAINT CK_Requisito_reqTipoRequisito;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsClasificacion')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsClasificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsTipoTransaccion')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsEstado')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ValidacionProceso_vapValidacion')) ALTER TABLE ValidacionProceso DROP CONSTRAINT CK_ValidacionProceso_vapValidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ValidacionProceso_vapProceso')) ALTER TABLE ValidacionProceso DROP CONSTRAINT CK_ValidacionProceso_vapProceso;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ValidacionProceso_vapEstadoProceso')) ALTER TABLE ValidacionProceso DROP CONSTRAINT CK_ValidacionProceso_vapEstadoProceso;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_NotificacionEnviada_noeEstadoEnvioNot')) ALTER TABLE NotificacionEnviada DROP CONSTRAINT CK_NotificacionEnviada_noeEstadoEnvioNot;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ListaEspecialRevision_lerTipoIdentificacion')) ALTER TABLE ListaEspecialRevision DROP CONSTRAINT CK_ListaEspecialRevision_lerTipoIdentificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ListaEspecialRevision_lerRazonInclusion')) ALTER TABLE ListaEspecialRevision DROP CONSTRAINT CK_ListaEspecialRevision_lerRazonInclusion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ListaEspecialRevision_lerEstado')) ALTER TABLE ListaEspecialRevision DROP CONSTRAINT CK_ListaEspecialRevision_lerEstado;

