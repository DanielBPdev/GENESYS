--liquibase formatted sql

--changeset Heinsohn:01
--comment: referential structural creation base line mar 2018

GO
ALTER TABLE [AccionCobro1C_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro1C_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobro1C_aud] CHECK CONSTRAINT [FK_AccionCobro1C_aud_REV]
GO
ALTER TABLE [AccionCobro1D1E_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro1D1E_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobro1D1E_aud] CHECK CONSTRAINT [FK_AccionCobro1D1E_aud_REV]
GO
ALTER TABLE [AccionCobro1F_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro1F_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobro1F_aud] CHECK CONSTRAINT [FK_AccionCobro1F_aud_REV]
GO
ALTER TABLE [AccionCobro2C_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro2C_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobro2C_aud] CHECK CONSTRAINT [FK_AccionCobro2C_aud_REV]
GO
ALTER TABLE [AccionCobro2D_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro2D_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobro2D_aud] CHECK CONSTRAINT [FK_AccionCobro2D_aud_REV]
GO
ALTER TABLE [AccionCobro2E_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro2E_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobro2E_aud] CHECK CONSTRAINT [FK_AccionCobro2E_aud_REV]
GO
ALTER TABLE [AccionCobro2F2G_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro2F2G_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobro2F2G_aud] CHECK CONSTRAINT [FK_AccionCobro2F2G_aud_REV]
GO
ALTER TABLE [AccionCobro2H_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro2H_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobro2H_aud] CHECK CONSTRAINT [FK_AccionCobro2H_aud_REV]
GO
ALTER TABLE [AccionCobroA_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobroA_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobroA_aud] CHECK CONSTRAINT [FK_AccionCobroA_aud_REV]
GO
ALTER TABLE [AccionCobroB_aud]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobroB_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AccionCobroB_aud] CHECK CONSTRAINT [FK_AccionCobroB_aud_REV]
GO
ALTER TABLE [ActaAsignacionFovis_aud]  WITH CHECK ADD  CONSTRAINT [FK_ActaAsignacionFovis_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ActaAsignacionFovis_aud] CHECK CONSTRAINT [FK_ActaAsignacionFovis_aud_REV]
GO
ALTER TABLE [ActividadCartera_aud]  WITH CHECK ADD  CONSTRAINT [FK_ActividadFiscalizacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ActividadCartera_aud] CHECK CONSTRAINT [FK_ActividadFiscalizacion_aud_REV]
GO
ALTER TABLE [ActividadDocumento_aud]  WITH CHECK ADD  CONSTRAINT [FK_ActividadDocumento_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ActividadDocumento_aud] CHECK CONSTRAINT [FK_ActividadDocumento_aud_REV]
GO
ALTER TABLE [AdministradorSubsidio_aud]  WITH CHECK ADD  CONSTRAINT [FK_AdministradorSubsidio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AdministradorSubsidio_aud] CHECK CONSTRAINT [FK_AdministradorSubsidio_aud_REV]
GO
ALTER TABLE [AdminSubsidioGrupo_aud]  WITH CHECK ADD  CONSTRAINT [FK_AdminSubsidioGrupo_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AdminSubsidioGrupo_aud] CHECK CONSTRAINT [FK_AdminSubsidioGrupo_aud_REV]
GO
ALTER TABLE [Afiliado_aud]  WITH CHECK ADD  CONSTRAINT [FK_Afiliado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Afiliado_aud] CHECK CONSTRAINT [FK_Afiliado_aud_REV]
GO
ALTER TABLE [AFP_aud]  WITH CHECK ADD  CONSTRAINT [FK_AFP_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AFP_aud] CHECK CONSTRAINT [FK_AFP_aud_REV]
GO
ALTER TABLE [AgendaCartera_aud]  WITH CHECK ADD  CONSTRAINT [FK_AgendaFiscalizacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AgendaCartera_aud] CHECK CONSTRAINT [FK_AgendaFiscalizacion_aud_REV]
GO
ALTER TABLE [AhorroPrevio_aud]  WITH CHECK ADD  CONSTRAINT [FK_AhorroPrevio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AhorroPrevio_aud] CHECK CONSTRAINT [FK_AhorroPrevio_aud_REV]
GO
ALTER TABLE [AporteDetallado_aud]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetallado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AporteDetallado_aud] CHECK CONSTRAINT [FK_AporteDetallado_aud_REV]
GO
ALTER TABLE [AporteGeneral_aud]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AporteGeneral_aud] CHECK CONSTRAINT [FK_AporteGeneral_aud_REV]
GO
ALTER TABLE [AreaCajaCompensacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_AreaCajaCompensacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AreaCajaCompensacion_aud] CHECK CONSTRAINT [FK_AreaCajaCompensacion_aud_REV]
GO
ALTER TABLE [ARL_aud]  WITH CHECK ADD  CONSTRAINT [FK_ARL_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ARL_aud] CHECK CONSTRAINT [FK_ARL_aud_REV]
GO
ALTER TABLE [AsesorResponsableEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_AsesorResponsableEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [AsesorResponsableEmpleador_aud] CHECK CONSTRAINT [FK_AsesorResponsableEmpleador_aud_REV]
GO
ALTER TABLE [Banco_aud]  WITH CHECK ADD  CONSTRAINT [FK_Banco_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Banco_aud] CHECK CONSTRAINT [FK_Banco_aud_REV]
GO
ALTER TABLE [Beneficiario_aud]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Beneficiario_aud] CHECK CONSTRAINT [FK_Beneficiario_aud_REV]
GO
ALTER TABLE [BeneficiarioDetalle_aud]  WITH CHECK ADD  CONSTRAINT [FK_BeneficiarioDetalle_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [BeneficiarioDetalle_aud] CHECK CONSTRAINT [FK_BeneficiarioDetalle_aud_REV]
GO
ALTER TABLE [Beneficio_aud]  WITH CHECK ADD  CONSTRAINT [FK_Beneficio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Beneficio_aud] CHECK CONSTRAINT [FK_Beneficio_aud_REV]
GO
ALTER TABLE [BeneficioEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_BeneficioEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [BeneficioEmpleador_aud] CHECK CONSTRAINT [FK_BeneficioEmpleador_aud_REV]
GO
ALTER TABLE [BitacoraCartera_aud]  WITH CHECK ADD  CONSTRAINT [FK_BitacoraCartera_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [BitacoraCartera_aud] CHECK CONSTRAINT [FK_BitacoraCartera_aud_REV]
GO
ALTER TABLE [CajaCompensacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_CajaCompensacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CajaCompensacion_aud] CHECK CONSTRAINT [FK_CajaCompensacion_aud_REV]
GO
ALTER TABLE [CajaCorrespondencia_aud]  WITH CHECK ADD  CONSTRAINT [FK_CajaCorrespondencia_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CajaCorrespondencia_aud] CHECK CONSTRAINT [FK_CajaCorrespondencia_aud_REV]
GO
ALTER TABLE [CargueArchivoActualizacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoActualizacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CargueArchivoActualizacion_aud] CHECK CONSTRAINT [FK_CargueArchivoActualizacion_aud_REV]
GO
ALTER TABLE [CargueArchivoCruceFovis_aud]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovis_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CargueArchivoCruceFovis_aud] CHECK CONSTRAINT [FK_CargueArchivoCruceFovis_aud_REV]
GO
ALTER TABLE [CargueMultiple_aud]  WITH CHECK ADD  CONSTRAINT [FK_CargueMultiple_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CargueMultiple_aud] CHECK CONSTRAINT [FK_CargueMultiple_aud_REV]
GO
ALTER TABLE [CargueMultipleSupervivencia_aud]  WITH CHECK ADD  CONSTRAINT [FK_CargueMultipleSupervivencia_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CargueMultipleSupervivencia_aud] CHECK CONSTRAINT [FK_CargueMultipleSupervivencia_aud_REV]
GO
ALTER TABLE [Cartera_aud]  WITH CHECK ADD  CONSTRAINT [FK_Cartera_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Cartera_aud] CHECK CONSTRAINT [FK_Cartera_aud_REV]
GO
ALTER TABLE [CarteraDependiente_aud]  WITH CHECK ADD  CONSTRAINT [FK_CarteraDependiente_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CarteraDependiente_aud] CHECK CONSTRAINT [FK_CarteraDependiente_aud_REV]
GO
ALTER TABLE [Categoria_aud]  WITH CHECK ADD  CONSTRAINT [FK_Categoria_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Categoria_aud] CHECK CONSTRAINT [FK_Categoria_aud_REV]
GO
ALTER TABLE [CicloAportante_aud]  WITH CHECK ADD  CONSTRAINT [FK_CicloAportante_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CicloAportante_aud] CHECK CONSTRAINT [FK_CicloAportante_aud_REV]
GO
ALTER TABLE [CicloAsignacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_CicloAsignacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CicloAsignacion_aud] CHECK CONSTRAINT [FK_CicloAsignacion_aud_REV]
GO
ALTER TABLE [CicloCartera_aud]  WITH CHECK ADD  CONSTRAINT [FK_CicloFiscalizacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CicloCartera_aud] CHECK CONSTRAINT [FK_CicloFiscalizacion_aud_REV]
GO
ALTER TABLE [CicloModalidad_aud]  WITH CHECK ADD  CONSTRAINT [FK_CicloModalidad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CicloModalidad_aud] CHECK CONSTRAINT [FK_CicloModalidad_aud_REV]
GO
ALTER TABLE [CodigoCIIU_aud]  WITH CHECK ADD  CONSTRAINT [FK_CodigoCIIU_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CodigoCIIU_aud] CHECK CONSTRAINT [FK_CodigoCIIU_aud_REV]
GO
ALTER TABLE [Comunicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_Comunicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Comunicado_aud] CHECK CONSTRAINT [FK_Comunicado_aud_REV]
GO
ALTER TABLE [CondicionInvalidez_aud]  WITH CHECK ADD  CONSTRAINT [FK_CondicionInvalidez_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CondicionInvalidez_aud] CHECK CONSTRAINT [FK_CondicionInvalidez_aud_REV]
GO
ALTER TABLE [CondicionVisita_aud]  WITH CHECK ADD  CONSTRAINT [FK_CondicionVisita_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CondicionVisita_aud] CHECK CONSTRAINT [FK_CondicionVisita_aud_REV]
GO
ALTER TABLE [ConexionOperadorInformacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_ConexionOperadorInformacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ConexionOperadorInformacion_aud] CHECK CONSTRAINT [FK_ConexionOperadorInformacion_aud_REV]
GO
ALTER TABLE [ConsolaEstadoCargueMasivo_aud]  WITH CHECK ADD  CONSTRAINT [FK_ConsolaEstadoCargueMasivo_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ConsolaEstadoCargueMasivo_aud] CHECK CONSTRAINT [FK_ConsolaEstadoCargueMasivo_aud_REV]
GO
ALTER TABLE [Constante_aud]  WITH CHECK ADD  CONSTRAINT [FK_Constante_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Constante_aud] CHECK CONSTRAINT [FK_Constante_aud_REV]
GO
ALTER TABLE [ConvenioPago_aud]  WITH CHECK ADD  CONSTRAINT [FK_ConvenioPago_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ConvenioPago_aud] CHECK CONSTRAINT [FK_ConvenioPago_aud_REV]
GO
ALTER TABLE [ConvenioPagoDependiente_aud]  WITH CHECK ADD  CONSTRAINT [FK_ConvenioPagoDependiente_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ConvenioPagoDependiente_aud] CHECK CONSTRAINT [FK_ConvenioPagoDependiente_aud_REV]
GO
ALTER TABLE [Correccion_aud]  WITH CHECK ADD  CONSTRAINT [FK_Correccion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Correccion_aud] CHECK CONSTRAINT [FK_Correccion_aud_REV]
GO
ALTER TABLE [Cruce_aud]  WITH CHECK ADD  CONSTRAINT [FK_Cruce_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Cruce_aud] CHECK CONSTRAINT [FK_Cruce_aud_REV]
GO
ALTER TABLE [CruceDetalle_aud]  WITH CHECK ADD  CONSTRAINT [FK_CruceDetalle_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CruceDetalle_aud] CHECK CONSTRAINT [FK_CruceDetalle_aud_REV]
GO
ALTER TABLE [CuentaAdministradorSubsidio_aud]  WITH CHECK ADD  CONSTRAINT [FK_CuentaAdministradorSubsidio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [CuentaAdministradorSubsidio_aud] CHECK CONSTRAINT [FK_CuentaAdministradorSubsidio_aud_REV]
GO
ALTER TABLE [Departamento_aud]  WITH CHECK ADD  CONSTRAINT [FK_Departamento_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Departamento_aud] CHECK CONSTRAINT [FK_Departamento_aud_REV]
GO
ALTER TABLE [DesafiliacionAportante_aud]  WITH CHECK ADD  CONSTRAINT [FK_DesafiliacionAportante_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DesafiliacionAportante_aud] CHECK CONSTRAINT [FK_DesafiliacionAportante_aud_REV]
GO
ALTER TABLE [DestinatarioComunicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_DestinatarioComunicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DestinatarioComunicado_aud] CHECK CONSTRAINT [FK_DestinatarioComunicado_aud_REV]
GO
ALTER TABLE [DestinatarioGrupo_aud]  WITH CHECK ADD  CONSTRAINT [FK_DestinatarioGrupo_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DestinatarioGrupo_aud] CHECK CONSTRAINT [FK_DestinatarioGrupo_aud_REV]
GO
ALTER TABLE [DetalleComunicadoEnviado_aud]  WITH CHECK ADD  CONSTRAINT [FK_DetalleComunicadoEnviado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DetalleComunicadoEnviado_aud] CHECK CONSTRAINT [FK_DetalleComunicadoEnviado_aud_REV]
GO
ALTER TABLE [DetalleSolicitudGestionCobro_aud]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSolicitudGestionCobro_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DetalleSolicitudGestionCobro_aud] CHECK CONSTRAINT [FK_DetalleSolicitudGestionCobro_aud_REV]
GO
ALTER TABLE [DetalleSubsidioAsignado_aud]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSubsidioAsignado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DetalleSubsidioAsignado_aud] CHECK CONSTRAINT [FK_DetalleSubsidioAsignado_aud_REV]
GO
ALTER TABLE [DevolucionAporte_aud]  WITH CHECK ADD  CONSTRAINT [FK_DevolucionAporte_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DevolucionAporte_aud] CHECK CONSTRAINT [FK_DevolucionAporte_aud_REV]
GO
ALTER TABLE [DevolucionAporteDetalle_aud]  WITH CHECK ADD  CONSTRAINT [FK_DevolucionAporteDetalle_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DevolucionAporteDetalle_aud] CHECK CONSTRAINT [FK_DevolucionAporteDetalle_aud_REV]
GO
ALTER TABLE [DiasFestivos_aud]  WITH CHECK ADD  CONSTRAINT [FK_DiasFestivos_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DiasFestivos_aud] CHECK CONSTRAINT [FK_DiasFestivos_aud_REV]
GO
ALTER TABLE [DiferenciasCargueActualizacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_DiferenciasCargueActualizacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DiferenciasCargueActualizacion_aud] CHECK CONSTRAINT [FK_DiferenciasCargueActualizacion_aud_REV]
GO
ALTER TABLE [DocumentoAdministracionEstadoSolicitud_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoAdministracionEstadoSolicitud_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoAdministracionEstadoSolicitud_aud] CHECK CONSTRAINT [FK_DocumentoAdministracionEstadoSolicitud_aud_REV]
GO
ALTER TABLE [DocumentoBitacora_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoBitacora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoBitacora_aud] CHECK CONSTRAINT [FK_DocumentoBitacora_aud_REV]
GO
ALTER TABLE [DocumentoCartera_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoCartera_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoCartera_aud] CHECK CONSTRAINT [FK_DocumentoCartera_aud_REV]
GO
ALTER TABLE [DocumentoDesafiliacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoDesafiliacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoDesafiliacion_aud] CHECK CONSTRAINT [FK_DocumentoDesafiliacion_aud_REV]
GO
ALTER TABLE [DocumentoEntidadPagadora_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoEntidadPagadora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoEntidadPagadora_aud] CHECK CONSTRAINT [FK_DocumentoEntidadPagadora_aud_REV]
GO
ALTER TABLE [DocumentoSoporte_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporte_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoSoporte_aud] CHECK CONSTRAINT [FK_DocumentoSoporte_aud_REV]
GO
ALTER TABLE [DocumentoSoporteOferente_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporteOferente_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoSoporteOferente_aud] CHECK CONSTRAINT [FK_DocumentoSoporteOferente_aud_REV]
GO
ALTER TABLE [DocumentoSoporteProyectoVivienda_aud]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporteProyectoVivienda_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [DocumentoSoporteProyectoVivienda_aud] CHECK CONSTRAINT [FK_DocumentoSoporteProyectoVivienda_aud_REV]
GO
ALTER TABLE [EjecucionProcesoAsincrono_aud]  WITH CHECK ADD  CONSTRAINT [FK_EjecucionProcesoAsincrono_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [EjecucionProcesoAsincrono_aud] CHECK CONSTRAINT [FK_EjecucionProcesoAsincrono_aud_REV]
GO
ALTER TABLE [EjecucionProgramada_aud]  WITH CHECK ADD  CONSTRAINT [FK_EjecucionProgramada_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [EjecucionProgramada_aud] CHECK CONSTRAINT [FK_EjecucionProgramada_aud_REV]
GO
ALTER TABLE [ElementoDireccion_aud]  WITH CHECK ADD  CONSTRAINT [FK_ElementoDireccion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ElementoDireccion_aud] CHECK CONSTRAINT [FK_ElementoDireccion_aud_REV]
GO
ALTER TABLE [Empleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Empleador_aud] CHECK CONSTRAINT [FK_Empleador_aud_REV]
GO
ALTER TABLE [Empresa_aud]  WITH CHECK ADD  CONSTRAINT [FK_Empresa_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Empresa_aud] CHECK CONSTRAINT [FK_Empresa_aud_REV]
GO
ALTER TABLE [EntidadDescuento_aud]  WITH CHECK ADD  CONSTRAINT [FK_EntidadDescuento_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [EntidadDescuento_aud] CHECK CONSTRAINT [FK_EntidadDescuento_aud_REV]
GO
ALTER TABLE [EntidadPagadora_aud]  WITH CHECK ADD  CONSTRAINT [FK_EntidadPagadora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [EntidadPagadora_aud] CHECK CONSTRAINT [FK_EntidadPagadora_aud_REV]
GO
ALTER TABLE [EscalamientoSolicitud_aud]  WITH CHECK ADD  CONSTRAINT [FK_EscalamientoSolicitud_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [EscalamientoSolicitud_aud] CHECK CONSTRAINT [FK_EscalamientoSolicitud_aud_REV]
GO
ALTER TABLE [EtiquetaCorrespondenciaRadicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_EtiquetaCorrespondenciaRadicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [EtiquetaCorrespondenciaRadicado_aud] CHECK CONSTRAINT [FK_EtiquetaCorrespondenciaRadicado_aud_REV]
GO
ALTER TABLE [ExclusionCartera_aud]  WITH CHECK ADD  CONSTRAINT [FK_ExclusionCartera_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ExclusionCartera_aud] CHECK CONSTRAINT [FK_ExclusionCartera_aud_REV]
GO
ALTER TABLE [ExpulsionSubsanada_aud]  WITH CHECK ADD  CONSTRAINT [FK_ExpulsionSubsanada_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ExpulsionSubsanada_aud] CHECK CONSTRAINT [FK_ExpulsionSubsanada_aud_REV]
GO
ALTER TABLE [FormaPagoModalidad_aud]  WITH CHECK ADD  CONSTRAINT [FK_FormaPagoModalidad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [FormaPagoModalidad_aud] CHECK CONSTRAINT [FK_FormaPagoModalidad_aud_REV]
GO
ALTER TABLE [GestionNotiNoEnviadas_aud]  WITH CHECK ADD  CONSTRAINT [FK_GestionNotiNoEnviadas_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GestionNotiNoEnviadas_aud] CHECK CONSTRAINT [FK_GestionNotiNoEnviadas_aud_REV]
GO
ALTER TABLE [GlosaComentarioNovedad_aud]  WITH CHECK ADD  CONSTRAINT [FK_GlosaComentarioNovedad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GlosaComentarioNovedad_aud] CHECK CONSTRAINT [FK_GlosaComentarioNovedad_aud_REV]
GO
ALTER TABLE [GradoAcademico_aud]  WITH CHECK ADD  CONSTRAINT [FK_GradoAcademico_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GradoAcademico_aud] CHECK CONSTRAINT [FK_GradoAcademico_aud_REV]
GO
ALTER TABLE [GrupoFamiliar_aud]  WITH CHECK ADD  CONSTRAINT [FK_GrupoFamiliar_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GrupoFamiliar_aud] CHECK CONSTRAINT [FK_GrupoFamiliar_aud_REV]
GO
ALTER TABLE [GrupoPrioridad_aud]  WITH CHECK ADD  CONSTRAINT [FK_GrupoPrioridad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GrupoPrioridad_aud] CHECK CONSTRAINT [FK_GrupoPrioridad_aud_REV]
GO
ALTER TABLE [GrupoRequisito_aud]  WITH CHECK ADD  CONSTRAINT [FK_GrupoRequisito_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [GrupoRequisito_aud] CHECK CONSTRAINT [FK_GrupoRequisito_aud_REV]
GO
ALTER TABLE [HistoriaResultadoValidacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_HistoriaResultadoValidacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [HistoriaResultadoValidacion_aud] CHECK CONSTRAINT [FK_HistoriaResultadoValidacion_aud_REV]
GO
ALTER TABLE [InformacionFaltanteAportante_aud]  WITH CHECK ADD  CONSTRAINT [FK_InformacionFaltanteAportante_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [InformacionFaltanteAportante_aud] CHECK CONSTRAINT [FK_InformacionFaltanteAportante_aud_REV]
GO
ALTER TABLE [Infraestructura_aud]  WITH CHECK ADD  CONSTRAINT [FK_Infraestructura_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Infraestructura_aud] CHECK CONSTRAINT [FK_Infraestructura_aud_REV]
GO
ALTER TABLE [InhabilidadSubsidioFovis_aud]  WITH CHECK ADD  CONSTRAINT [FK_InhabilidadSubsidioFovis_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [InhabilidadSubsidioFovis_aud] CHECK CONSTRAINT [FK_InhabilidadSubsidioFovis_aud_REV]
GO
ALTER TABLE [IntegranteHogar_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntegranteHogar_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntegranteHogar_aud] CHECK CONSTRAINT [FK_IntegranteHogar_aud_REV]
GO
ALTER TABLE [IntentoAfiliacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoAfiliacion_aud] CHECK CONSTRAINT [FK_IntentoAfiliacion_aud_REV]
GO
ALTER TABLE [IntentoAfiliRequisito_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliRequisito_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoAfiliRequisito_aud] CHECK CONSTRAINT [FK_IntentoAfiliRequisito_aud_REV]
GO
ALTER TABLE [IntentoLegalizacionDesembolso_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoLegalizacionDesembolso_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoLegalizacionDesembolso_aud] CHECK CONSTRAINT [FK_IntentoLegalizacionDesembolso_aud_REV]
GO
ALTER TABLE [IntentoLegalizacionDesembolsoRequisito_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoLegalizacionDesembolsoRequisito_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoLegalizacionDesembolsoRequisito_aud] CHECK CONSTRAINT [FK_IntentoLegalizacionDesembolsoRequisito_aud_REV]
GO
ALTER TABLE [IntentoNovedad_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoNovedad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoNovedad_aud] CHECK CONSTRAINT [FK_IntentoNovedad_aud_REV]
GO
ALTER TABLE [IntentoNoveRequisito_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoNoveRequisito_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoNoveRequisito_aud] CHECK CONSTRAINT [FK_IntentoNoveRequisito_aud_REV]
GO
ALTER TABLE [IntentoPostulacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoPostulacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoPostulacion_aud] CHECK CONSTRAINT [FK_IntentoPostulacion_aud_REV]
GO
ALTER TABLE [IntentoPostulacionRequisito_aud]  WITH CHECK ADD  CONSTRAINT [FK_IntentoPostulacionRequisito_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [IntentoPostulacionRequisito_aud] CHECK CONSTRAINT [FK_IntentoPostulacionRequisito_aud_REV]
GO
ALTER TABLE [ItemChequeo_aud]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeo_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ItemChequeo_aud] CHECK CONSTRAINT [FK_ItemChequeo_aud_REV]
GO
ALTER TABLE [JefeHogar_aud]  WITH CHECK ADD  CONSTRAINT [FK_JefeHogar_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [JefeHogar_aud] CHECK CONSTRAINT [FK_JefeHogar_aud_REV]
GO
ALTER TABLE [LegalizacionDesembolso_aud]  WITH CHECK ADD  CONSTRAINT [FK_LegalizacionDesembolso_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [LegalizacionDesembolso_aud] CHECK CONSTRAINT [FK_LegalizacionDesembolso_aud_REV]
GO
ALTER TABLE [Licencia_aud]  WITH CHECK ADD  CONSTRAINT [FK_Licencia_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Licencia_aud] CHECK CONSTRAINT [FK_Licencia_aud_REV]
GO
ALTER TABLE [LicenciaDetalle_aud]  WITH CHECK ADD  CONSTRAINT [FK_LicenciaDetalle_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [LicenciaDetalle_aud] CHECK CONSTRAINT [FK_LicenciaDetalle_aud_REV]
GO
ALTER TABLE [LineaCobro_aud]  WITH CHECK ADD  CONSTRAINT [FK_LineaCobro_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [LineaCobro_aud] CHECK CONSTRAINT [FK_LineaCobro_aud_REV]
GO
ALTER TABLE [ListaEspecialRevision_aud]  WITH CHECK ADD  CONSTRAINT [FK_ListaEspecialRevision_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ListaEspecialRevision_aud] CHECK CONSTRAINT [FK_ListaEspecialRevision_aud_REV]
GO
ALTER TABLE [MedioCheque_aud]  WITH CHECK ADD  CONSTRAINT [FK_MedioCheque_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MedioCheque_aud] CHECK CONSTRAINT [FK_MedioCheque_aud_REV]
GO
ALTER TABLE [MedioConsignacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_MedioConsignacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MedioConsignacion_aud] CHECK CONSTRAINT [FK_MedioConsignacion_aud_REV]
GO
ALTER TABLE [MedioDePago_aud]  WITH CHECK ADD  CONSTRAINT [FK_MedioDePago_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MedioDePago_aud] CHECK CONSTRAINT [FK_MedioDePago_aud_REV]
GO
ALTER TABLE [MedioEfectivo_aud]  WITH CHECK ADD  CONSTRAINT [FK_MedioEfectivo_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MedioEfectivo_aud] CHECK CONSTRAINT [FK_MedioEfectivo_aud_REV]
GO
ALTER TABLE [MedioPagoPersona_aud]  WITH CHECK ADD  CONSTRAINT [FK_MedioPagoPersona_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MedioPagoPersona_aud] CHECK CONSTRAINT [FK_MedioPagoPersona_aud_REV]
GO
ALTER TABLE [MedioPagoProyectoVivienda_aud]  WITH CHECK ADD  CONSTRAINT [FK_MedioPagoProyectoVivienda_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MedioPagoProyectoVivienda_aud] CHECK CONSTRAINT [FK_MedioPagoProyectoVivienda_aud_REV]
GO
ALTER TABLE [MediosPagoCCF_aud]  WITH CHECK ADD  CONSTRAINT [FK_MediosPagoCCF_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MediosPagoCCF_aud] CHECK CONSTRAINT [FK_MediosPagoCCF_aud_REV]
GO
ALTER TABLE [MedioTarjeta_aud]  WITH CHECK ADD  CONSTRAINT [FK_MedioTarjeta_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MedioTarjeta_aud] CHECK CONSTRAINT [FK_MedioTarjeta_aud_REV]
GO
ALTER TABLE [MedioTransferencia_aud]  WITH CHECK ADD  CONSTRAINT [FK_MedioTransferencia_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MedioTransferencia_aud] CHECK CONSTRAINT [FK_MedioTransferencia_aud_REV]
GO
ALTER TABLE [MotivoNoGestionCobro_aud]  WITH CHECK ADD  CONSTRAINT [FK_MotivoNoGestionCobro_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MotivoNoGestionCobro_aud] CHECK CONSTRAINT [FK_MotivoNoGestionCobro_aud_REV]
GO
ALTER TABLE [MovimientoAjusteAporte_aud]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAjusteAporte_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MovimientoAjusteAporte_aud] CHECK CONSTRAINT [FK_MovimientoAjusteAporte_aud_REV]
GO
ALTER TABLE [MovimientoAporte_aud]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAporte_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [MovimientoAporte_aud] CHECK CONSTRAINT [FK_MovimientoAporte_aud_REV]
GO
ALTER TABLE [Municipio_aud]  WITH CHECK ADD  CONSTRAINT [FK_Municipio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Municipio_aud] CHECK CONSTRAINT [FK_Municipio_aud_REV]
GO
ALTER TABLE [NotificacionDestinatario_aud]  WITH CHECK ADD  CONSTRAINT [FK_NotificacionDestinatario_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [NotificacionDestinatario_aud] CHECK CONSTRAINT [FK_NotificacionDestinatario_aud_REV]
GO
ALTER TABLE [NotificacionEnviada_aud]  WITH CHECK ADD  CONSTRAINT [FK_NotificacionEnviada_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [NotificacionEnviada_aud] CHECK CONSTRAINT [FK_NotificacionEnviada_aud_REV]
GO
ALTER TABLE [NotificacionPersonal_aud]  WITH CHECK ADD  CONSTRAINT [FK_NotificacionPersonal_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [NotificacionPersonal_aud] CHECK CONSTRAINT [FK_NotificacionPersonal_aud_REV]
GO
ALTER TABLE [NotificacionPersonalDocumento_aud]  WITH CHECK ADD  CONSTRAINT [FK_NotificacionPersonalDocumento_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [NotificacionPersonalDocumento_aud] CHECK CONSTRAINT [FK_NotificacionPersonalDocumento_aud_REV]
GO
ALTER TABLE [NovedadDetalle_aud]  WITH CHECK ADD  CONSTRAINT [FK_NovedadPila_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [NovedadDetalle_aud] CHECK CONSTRAINT [FK_NovedadPila_aud_REV]
GO
ALTER TABLE [OcupacionProfesion_aud]  WITH CHECK ADD  CONSTRAINT [FK_OcupacionProfesion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [OcupacionProfesion_aud] CHECK CONSTRAINT [FK_OcupacionProfesion_aud_REV]
GO
ALTER TABLE [Oferente_aud]  WITH CHECK ADD  CONSTRAINT [FK_Oferente_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Oferente_aud] CHECK CONSTRAINT [FK_Oferente_aud_REV]
GO
ALTER TABLE [OperadorInformacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_OperadorInformacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [OperadorInformacion_aud] CHECK CONSTRAINT [FK_OperadorInformacion_aud_REV]
GO
ALTER TABLE [OperadorInformacionCcf_aud]  WITH CHECK ADD  CONSTRAINT [FK_OperadorInformacionCcf_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [OperadorInformacionCcf_aud] CHECK CONSTRAINT [FK_OperadorInformacionCcf_aud_REV]
GO
ALTER TABLE [PagoPeriodoConvenio_aud]  WITH CHECK ADD  CONSTRAINT [FK_PagoPeriodoConvenio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PagoPeriodoConvenio_aud] CHECK CONSTRAINT [FK_PagoPeriodoConvenio_aud_REV]
GO
ALTER TABLE [ParametrizacionCartera_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionCartera_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionCartera_aud] CHECK CONSTRAINT [FK_ParametrizacionCartera_aud_REV]
GO
ALTER TABLE [ParametrizacionCondicionesSubsidio_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionCondicionesSubsidio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionCondicionesSubsidio_aud] CHECK CONSTRAINT [FK_ParametrizacionCondicionesSubsidio_aud_REV]
GO
ALTER TABLE [ParametrizacionConveniosPago_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionConveniosPago_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionConveniosPago_aud] CHECK CONSTRAINT [FK_ParametrizacionConveniosPago_aud_REV]
GO
ALTER TABLE [ParametrizacionCriterioGestionCobro_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionCriterioGestionCobro_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionCriterioGestionCobro_aud] CHECK CONSTRAINT [FK_ParametrizacionCriterioGestionCobro_aud_REV]
GO
ALTER TABLE [ParametrizacionDesafiliacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionDesafiliacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionDesafiliacion_aud] CHECK CONSTRAINT [FK_ParametrizacionDesafiliacion_aud_REV]
GO
ALTER TABLE [ParametrizacionEjecucionProgramada_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionEjecucionProgramada_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionEjecucionProgramada_aud] CHECK CONSTRAINT [FK_ParametrizacionEjecucionProgramada_aud_REV]
GO
ALTER TABLE [ParametrizacionExclusiones_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionExclusiones_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionExclusiones_aud] CHECK CONSTRAINT [FK_ParametrizacionExclusiones_aud_REV]
GO
ALTER TABLE [ParametrizacionFiscalizacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionFiscalizacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionFiscalizacion_aud] CHECK CONSTRAINT [FK_ParametrizacionFiscalizacion_aud_REV]
GO
ALTER TABLE [ParametrizacionFOVIS_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionFOVIS_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionFOVIS_aud] CHECK CONSTRAINT [FK_ParametrizacionFOVIS_aud_REV]
GO
ALTER TABLE [ParametrizacionGestionCobro_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionGestionCobro_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionGestionCobro_aud] CHECK CONSTRAINT [FK_ParametrizacionGestionCobro_aud_REV]
GO
ALTER TABLE [ParametrizacionLiquidacionSubsidio_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionLiquidacionSubsidio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionLiquidacionSubsidio_aud] CHECK CONSTRAINT [FK_ParametrizacionLiquidacionSubsidio_aud_REV]
GO
ALTER TABLE [ParametrizacionMetodoAsignacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionMetodoAsignacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionMetodoAsignacion_aud] CHECK CONSTRAINT [FK_ParametrizacionMetodoAsignacion_aud_REV]
GO
ALTER TABLE [ParametrizacionModalidad_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionModalidad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionModalidad_aud] CHECK CONSTRAINT [FK_ParametrizacionModalidad_aud_REV]
GO
ALTER TABLE [ParametrizacionNovedad_aud]  WITH CHECK ADD  CONSTRAINT [FK_Novedad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionNovedad_aud] CHECK CONSTRAINT [FK_Novedad_aud_REV]
GO
ALTER TABLE [ParametrizacionPreventiva_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionPreventiva_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizacionPreventiva_aud] CHECK CONSTRAINT [FK_ParametrizacionPreventiva_aud_REV]
GO
ALTER TABLE [ParametrizaEnvioComunicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizaEnvioComunicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ParametrizaEnvioComunicado_aud] CHECK CONSTRAINT [FK_ParametrizaEnvioComunicado_aud_REV]
GO
ALTER TABLE [Parametro_aud]  WITH CHECK ADD  CONSTRAINT [FK_Parametro_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Parametro_aud] CHECK CONSTRAINT [FK_Parametro_aud_REV]
GO
ALTER TABLE [Periodo_aud]  WITH CHECK ADD  CONSTRAINT [FK_Periodo_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Periodo_aud] CHECK CONSTRAINT [FK_Periodo_aud_REV]
GO
ALTER TABLE [PeriodoBeneficio_aud]  WITH CHECK ADD  CONSTRAINT [FK_PeriodoBeneficio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PeriodoBeneficio_aud] CHECK CONSTRAINT [FK_PeriodoBeneficio_aud_REV]
GO
ALTER TABLE [PeriodoExclusionMora_aud]  WITH CHECK ADD  CONSTRAINT [FK_PeriodoExclusionMora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PeriodoExclusionMora_aud] CHECK CONSTRAINT [FK_PeriodoExclusionMora_aud_REV]
GO
ALTER TABLE [PeriodoLiquidacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_PeriodoLiquidacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PeriodoLiquidacion_aud] CHECK CONSTRAINT [FK_PeriodoLiquidacion_aud_REV]
GO
ALTER TABLE [Persona_aud]  WITH CHECK ADD  CONSTRAINT [FK_Persona_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Persona_aud] CHECK CONSTRAINT [FK_Persona_aud_REV]
GO
ALTER TABLE [PersonaDetalle_aud]  WITH CHECK ADD  CONSTRAINT [FK_PersonaDetalle_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PersonaDetalle_aud] CHECK CONSTRAINT [FK_PersonaDetalle_aud_REV]
GO
ALTER TABLE [PlantillaComunicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_PlantillaComunicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PlantillaComunicado_aud] CHECK CONSTRAINT [FK_PlantillaComunicado_aud_REV]
GO
ALTER TABLE [PostulacionFOVIS_aud]  WITH CHECK ADD  CONSTRAINT [FK_PostulacionFOVIS_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PostulacionFOVIS_aud] CHECK CONSTRAINT [FK_PostulacionFOVIS_aud_REV]
GO
ALTER TABLE [PrioridadDestinatario_aud]  WITH CHECK ADD  CONSTRAINT [FK_PrioridadDestinatario_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [PrioridadDestinatario_aud] CHECK CONSTRAINT [FK_PrioridadDestinatario_aud_REV]
GO
ALTER TABLE [ProductoNoConforme_aud]  WITH CHECK ADD  CONSTRAINT [FK_ProductoNoConforme_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ProductoNoConforme_aud] CHECK CONSTRAINT [FK_ProductoNoConforme_aud_REV]
GO
ALTER TABLE [ProyectoSolucionVivienda_aud]  WITH CHECK ADD  CONSTRAINT [FK_ProyectoSolucionVivienda_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ProyectoSolucionVivienda_aud] CHECK CONSTRAINT [FK_ProyectoSolucionVivienda_aud_REV]
GO
ALTER TABLE [RangoTopeValorSFV_aud]  WITH CHECK ADD  CONSTRAINT [FK_RangoTopeValorSFV_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RangoTopeValorSFV_aud] CHECK CONSTRAINT [FK_RangoTopeValorSFV_aud_REV]
GO
ALTER TABLE [RecursoComplementario_aud]  WITH CHECK ADD  CONSTRAINT [FK_RecursoComplementario_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RecursoComplementario_aud] CHECK CONSTRAINT [FK_RecursoComplementario_aud_REV]
GO
ALTER TABLE [RegistroEstadoAporte_aud]  WITH CHECK ADD  CONSTRAINT [FK_RegistroEstadoAporte_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RegistroEstadoAporte_aud] CHECK CONSTRAINT [FK_RegistroEstadoAporte_aud_REV]
GO
ALTER TABLE [RegistroNovedadFutura_aud]  WITH CHECK ADD  CONSTRAINT [FK_RegistroNovedadFutura_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RegistroNovedadFutura_aud] CHECK CONSTRAINT [FK_RegistroNovedadFutura_aud_REV]
GO
ALTER TABLE [RegistroPersonaInconsistente_aud]  WITH CHECK ADD  CONSTRAINT [FK_RegistroPersonaInconsistente_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RegistroPersonaInconsistente_aud] CHECK CONSTRAINT [FK_RegistroPersonaInconsistente_aud_REV]
GO
ALTER TABLE [RelacionGrupoFamiliar_aud]  WITH CHECK ADD  CONSTRAINT [FK_RelacionGrupoFamiliar_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RelacionGrupoFamiliar_aud] CHECK CONSTRAINT [FK_RelacionGrupoFamiliar_aud_REV]
GO
ALTER TABLE [Requisito_aud]  WITH CHECK ADD  CONSTRAINT [FK_Requisito_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Requisito_aud] CHECK CONSTRAINT [FK_Requisito_aud_REV]
GO
ALTER TABLE [RequisitoCajaClasificacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoCajaClasificacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RequisitoCajaClasificacion_aud] CHECK CONSTRAINT [FK_RequisitoCajaClasificacion_aud_REV]
GO
ALTER TABLE [ResultadoEjecucionProgramada_aud]  WITH CHECK ADD  CONSTRAINT [FK_ResultadoEjecucionProgramada_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ResultadoEjecucionProgramada_aud] CHECK CONSTRAINT [FK_ResultadoEjecucionProgramada_aud_REV]
GO
ALTER TABLE [RevisionEntidad]  WITH CHECK ADD  CONSTRAINT [FK_RevisionEntidad_reeRevision] FOREIGN KEY([reeRevision])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RevisionEntidad] CHECK CONSTRAINT [FK_RevisionEntidad_reeRevision]
GO
ALTER TABLE [RolAfiliado_aud]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RolAfiliado_aud] CHECK CONSTRAINT [FK_RolAfiliado_aud_REV]
GO
ALTER TABLE [RolContactoEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [RolContactoEmpleador_aud] CHECK CONSTRAINT [FK_RolContactoEmpleador_aud_REV]
GO
ALTER TABLE [SedeCajaCompensacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_SedeCajaCompensacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SedeCajaCompensacion_aud] CHECK CONSTRAINT [FK_SedeCajaCompensacion_aud_REV]
GO
ALTER TABLE [SitioPago_aud]  WITH CHECK ADD  CONSTRAINT [FK_SitioPago_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SitioPago_aud] CHECK CONSTRAINT [FK_SitioPago_aud_REV]
GO
ALTER TABLE [SocioEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SocioEmpleador_aud] CHECK CONSTRAINT [FK_SocioEmpleador_aud_REV]
GO
ALTER TABLE [Solicitud_aud]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Solicitud_aud] CHECK CONSTRAINT [FK_Solicitud_aud_REV]
GO
ALTER TABLE [SolicitudAfiliaciEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliaciEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudAfiliaciEmpleador_aud] CHECK CONSTRAINT [FK_SolicitudAfiliaciEmpleador_aud_REV]
GO
ALTER TABLE [SolicitudAfiliacionPersona_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliacionPersona_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudAfiliacionPersona_aud] CHECK CONSTRAINT [FK_SolicitudAfiliacionPersona_aud_REV]
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis_aud] CHECK CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_aud_REV]
GO
ALTER TABLE [SolicitudAporte_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAporte_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudAporte_aud] CHECK CONSTRAINT [FK_SolicitudAporte_aud_REV]
GO
ALTER TABLE [SolicitudAsignacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAsignacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudAsignacion_aud] CHECK CONSTRAINT [FK_SolicitudAsignacion_aud_REV]
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora_aud] CHECK CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_aud_REV]
GO
ALTER TABLE [SolicitudCorreccionAporte_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudCorreccionAporte_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudCorreccionAporte_aud] CHECK CONSTRAINT [FK_SolicitudCorreccionAporte_aud_REV]
GO
ALTER TABLE [SolicitudDesafiliacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudDesafiliacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudDesafiliacion_aud] CHECK CONSTRAINT [FK_SolicitudDesafiliacion_aud_REV]
GO
ALTER TABLE [SolicitudDevolucionAporte_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudDevolucionAporte_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudDevolucionAporte_aud] CHECK CONSTRAINT [FK_SolicitudDevolucionAporte_aud_REV]
GO
ALTER TABLE [SolicitudFiscalizacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudFiscalizacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudFiscalizacion_aud] CHECK CONSTRAINT [FK_SolicitudFiscalizacion_aud_REV]
GO
ALTER TABLE [SolicitudGestionCobroElectronico_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCobroElectronico_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudGestionCobroElectronico_aud] CHECK CONSTRAINT [FK_SolicitudGestionCobroElectronico_aud_REV]
GO
ALTER TABLE [SolicitudGestionCobroFisico_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCobroFisico_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudGestionCobroFisico_aud] CHECK CONSTRAINT [FK_SolicitudGestionCobroFisico_aud_REV]
GO
ALTER TABLE [SolicitudGestionCobroManual_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCobroManual_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudGestionCobroManual_aud] CHECK CONSTRAINT [FK_SolicitudGestionCobroManual_aud_REV]
GO
ALTER TABLE [SolicitudGestionCruce_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCruce_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudGestionCruce_aud] CHECK CONSTRAINT [FK_SolicitudGestionCruce_aud_REV]
GO
ALTER TABLE [SolicitudLegalizacionDesembolso_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudLegalizacionDesembolso_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudLegalizacionDesembolso_aud] CHECK CONSTRAINT [FK_SolicitudLegalizacionDesembolso_aud_REV]
GO
ALTER TABLE [SolicitudLiquidacionSubsidio_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudLiquidacionSubsidio_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudLiquidacionSubsidio_aud] CHECK CONSTRAINT [FK_SolicitudLiquidacionSubsidio_aud_REV]
GO
ALTER TABLE [SolicitudNovedad_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedad_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudNovedad_aud] CHECK CONSTRAINT [FK_SolicitudNovedad_aud_REV]
GO
ALTER TABLE [SolicitudNovedadEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudNovedadEmpleador_aud] CHECK CONSTRAINT [FK_SolicitudNovedadEmpleador_aud_REV]
GO
ALTER TABLE [SolicitudNovedadFovis_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadFovis_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudNovedadFovis_aud] CHECK CONSTRAINT [FK_SolicitudNovedadFovis_aud_REV]
GO
ALTER TABLE [SolicitudNovedadPersona_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersona_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudNovedadPersona_aud] CHECK CONSTRAINT [FK_SolicitudNovedadPersona_aud_REV]
GO
ALTER TABLE [SolicitudNovedadPersonaFovis_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersonaFovis_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudNovedadPersonaFovis_aud] CHECK CONSTRAINT [FK_SolicitudNovedadPersonaFovis_aud_REV]
GO
ALTER TABLE [SolicitudNovedadPila_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPila_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudNovedadPila_aud] CHECK CONSTRAINT [FK_SolicitudNovedadPila_aud_REV]
GO
ALTER TABLE [SolicitudPostulacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudPostulacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudPostulacion_aud] CHECK CONSTRAINT [FK_SolicitudPostulacion_aud_REV]
GO
ALTER TABLE [SolicitudPreventiva_aud]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudPreventiva_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SolicitudPreventiva_aud] CHECK CONSTRAINT [FK_SolicitudPreventiva_aud_REV]
GO
ALTER TABLE [SucursalEmpresa_aud]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SucursalEmpresa_aud] CHECK CONSTRAINT [FK_SucursalEmpresa_aud_REV]
GO
ALTER TABLE [SucursaRolContactEmpleador_aud]  WITH CHECK ADD  CONSTRAINT [FK_SucursaRolContactEmpleador_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [SucursaRolContactEmpleador_aud] CHECK CONSTRAINT [FK_SucursaRolContactEmpleador_aud_REV]
GO
ALTER TABLE [Tarjeta_aud]  WITH CHECK ADD  CONSTRAINT [FK_Tarjeta_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Tarjeta_aud] CHECK CONSTRAINT [FK_Tarjeta_aud_REV]
GO
ALTER TABLE [TasasInteresMora_aud]  WITH CHECK ADD  CONSTRAINT [FK_TasasInteresMora_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [TasasInteresMora_aud] CHECK CONSTRAINT [FK_TasasInteresMora_aud_REV]
GO
ALTER TABLE [TipoInfraestructura_aud]  WITH CHECK ADD  CONSTRAINT [FK_TipoInfraestructura_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [TipoInfraestructura_aud] CHECK CONSTRAINT [FK_TipoInfraestructura_aud_REV]
GO
ALTER TABLE [TipoTenencia_aud]  WITH CHECK ADD  CONSTRAINT [FK_TipoTenencia_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [TipoTenencia_aud] CHECK CONSTRAINT [FK_TipoTenencia_aud_REV]
GO
ALTER TABLE [TipoVia_aud]  WITH CHECK ADD  CONSTRAINT [FK_TipoVia_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [TipoVia_aud] CHECK CONSTRAINT [FK_TipoVia_aud_REV]
GO
ALTER TABLE [Ubicacion_aud]  WITH CHECK ADD  CONSTRAINT [FK_Ubicacion_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Ubicacion_aud] CHECK CONSTRAINT [FK_Ubicacion_aud_REV]
GO
ALTER TABLE [UbicacionEmpresa_aud]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [UbicacionEmpresa_aud] CHECK CONSTRAINT [FK_UbicacionEmpresa_aud_REV]
GO
ALTER TABLE [ValidacionProceso_aud]  WITH CHECK ADD  CONSTRAINT [FK_ValidacionProceso_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [ValidacionProceso_aud] CHECK CONSTRAINT [FK_ValidacionProceso_aud_REV]
GO
ALTER TABLE [VariableComunicado_aud]  WITH CHECK ADD  CONSTRAINT [FK_VariableComunicado_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [VariableComunicado_aud] CHECK CONSTRAINT [FK_VariableComunicado_aud_REV]
GO
ALTER TABLE [Visita_aud]  WITH CHECK ADD  CONSTRAINT [FK_Visita_aud_REV] FOREIGN KEY([REV])
REFERENCES [Revision] ([revId])
GO
ALTER TABLE [Visita_aud] CHECK CONSTRAINT [FK_Visita_aud_REV]
GO
