--liquibase formatted sql

--changeset Heinsohn:01
--comment: referential structural creation base line mar 2018

GO
ALTER TABLE [Empleador] ADD  CONSTRAINT [DF_Empleador_empValidarSucursalPila]  DEFAULT ((0)) FOR [empValidarSucursalPila]
GO
ALTER TABLE [AccionCobro1C]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro1C_accId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [AccionCobro1C] CHECK CONSTRAINT [FK_AccionCobro1C_accId]
GO
ALTER TABLE [AccionCobro1D1E]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro1D1E_acdId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [AccionCobro1D1E] CHECK CONSTRAINT [FK_AccionCobro1D1E_acdId]
GO
ALTER TABLE [AccionCobro1F]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro1F_abfId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [AccionCobro1F] CHECK CONSTRAINT [FK_AccionCobro1F_abfId]
GO
ALTER TABLE [AccionCobro2C]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro2C_aocId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [AccionCobro2C] CHECK CONSTRAINT [FK_AccionCobro2C_aocId]
GO
ALTER TABLE [AccionCobro2D]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro2D_aodId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [AccionCobro2D] CHECK CONSTRAINT [FK_AccionCobro2D_aodId]
GO
ALTER TABLE [AccionCobro2F2G]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro2F2G_aofId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [AccionCobro2F2G] CHECK CONSTRAINT [FK_AccionCobro2F2G_aofId]
GO
ALTER TABLE [AccionCobro2H]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobro2H_achId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [AccionCobro2H] CHECK CONSTRAINT [FK_AccionCobro2H_achId]
GO
ALTER TABLE [AccionCobroA]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobroA_acaId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [AccionCobroA] CHECK CONSTRAINT [FK_AccionCobroA_acaId]
GO
ALTER TABLE [AccionCobroB]  WITH CHECK ADD  CONSTRAINT [FK_AccionCobroB_acbId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [AccionCobroB] CHECK CONSTRAINT [FK_AccionCobroB_acbId]
GO
ALTER TABLE [ActaAsignacionFovis]  WITH CHECK ADD  CONSTRAINT [FK_ActaAsignacionFovis_aafSolicitudAsignacion] FOREIGN KEY([aafSolicitudAsignacion])
REFERENCES [SolicitudAsignacion] ([safId])
GO
ALTER TABLE [ActaAsignacionFovis] CHECK CONSTRAINT [FK_ActaAsignacionFovis_aafSolicitudAsignacion]
GO
ALTER TABLE [ActividadCartera]  WITH CHECK ADD  CONSTRAINT [FK_ActividadCartera_acrCicloAportante] FOREIGN KEY([acrCicloAportante])
REFERENCES [CicloAportante] ([capId])
GO
ALTER TABLE [ActividadCartera] CHECK CONSTRAINT [FK_ActividadCartera_acrCicloAportante]
GO
ALTER TABLE [ActividadDocumento]  WITH CHECK ADD  CONSTRAINT [FK_ActividadDocumento_adoActividadCartera] FOREIGN KEY([adoActividadCartera])
REFERENCES [ActividadCartera] ([acrId])
GO
ALTER TABLE [ActividadDocumento] CHECK CONSTRAINT [FK_ActividadDocumento_adoActividadCartera]
GO
ALTER TABLE [ActividadDocumento]  WITH CHECK ADD  CONSTRAINT [FK_ActividadDocumento_adoDocumentoSoporte] FOREIGN KEY([adoDocumentoSoporte])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [ActividadDocumento] CHECK CONSTRAINT [FK_ActividadDocumento_adoDocumentoSoporte]
GO
ALTER TABLE [AdministradorSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_AdministradorSubsidio_asuPersona] FOREIGN KEY([asuPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [AdministradorSubsidio] CHECK CONSTRAINT [FK_AdministradorSubsidio_asuPersona]
GO
ALTER TABLE [AdminSubsidioGrupo]  WITH CHECK ADD  CONSTRAINT [FK_AdminSubsidioGrupo_asgAdministradorSubsidio] FOREIGN KEY([asgAdministradorSubsidio])
REFERENCES [AdministradorSubsidio] ([asuId])
GO
ALTER TABLE [AdminSubsidioGrupo] CHECK CONSTRAINT [FK_AdminSubsidioGrupo_asgAdministradorSubsidio]
GO
ALTER TABLE [AdminSubsidioGrupo]  WITH CHECK ADD  CONSTRAINT [FK_AdminSubsidioGrupo_asgGrupoFamiliar] FOREIGN KEY([asgGrupoFamiliar])
REFERENCES [GrupoFamiliar] ([grfId])
GO
ALTER TABLE [AdminSubsidioGrupo] CHECK CONSTRAINT [FK_AdminSubsidioGrupo_asgGrupoFamiliar]
GO
ALTER TABLE [AdminSubsidioGrupo]  WITH CHECK ADD  CONSTRAINT [FK_AdminSubsidioGrupo_asgMedioDePago] FOREIGN KEY([asgMedioDePago])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [AdminSubsidioGrupo] CHECK CONSTRAINT [FK_AdminSubsidioGrupo_asgMedioDePago]
GO
ALTER TABLE [AdminSubsidioGrupo]  WITH CHECK ADD  CONSTRAINT [FK_AdminSubsidioGrupo_asgRelacionGrupoFamiliar] FOREIGN KEY([asgRelacionGrupoFamiliar])
REFERENCES [RelacionGrupoFamiliar] ([rgfId])
GO
ALTER TABLE [AdminSubsidioGrupo] CHECK CONSTRAINT [FK_AdminSubsidioGrupo_asgRelacionGrupoFamiliar]
GO
ALTER TABLE [Afiliado]  WITH CHECK ADD  CONSTRAINT [FK_Afliado_afiPersona] FOREIGN KEY([afiPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Afiliado] CHECK CONSTRAINT [FK_Afliado_afiPersona]
GO
ALTER TABLE [AgendaCartera]  WITH CHECK ADD  CONSTRAINT [FK_AgendaCartera_agrCicloAportante] FOREIGN KEY([agrCicloAportante])
REFERENCES [CicloAportante] ([capId])
GO
ALTER TABLE [AgendaCartera] CHECK CONSTRAINT [FK_AgendaCartera_agrCicloAportante]
GO
ALTER TABLE [AhorroPrevio]  WITH CHECK ADD  CONSTRAINT [FK_AhorroPrevio_ahpPostulacionFOVIS] FOREIGN KEY([ahpPostulacionFOVIS])
REFERENCES [PostulacionFOVIS] ([pofId])
GO
ALTER TABLE [AhorroPrevio] CHECK CONSTRAINT [FK_AhorroPrevio_ahpPostulacionFOVIS]
GO
ALTER TABLE [AplicacionValidacionSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_AplicacionValidacionSubsidio_avsConjuntoValidacionSubsidio] FOREIGN KEY([avsConjuntoValidacionSubsidio])
REFERENCES [ConjuntoValidacionSubsidio] ([cvsId])
GO
ALTER TABLE [AplicacionValidacionSubsidio] CHECK CONSTRAINT [FK_AplicacionValidacionSubsidio_avsConjuntoValidacionSubsidio]
GO
ALTER TABLE [AplicacionValidacionSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_AplicacionValidacionSubsidio_avsSolicitudLiquidacionSubsidio] FOREIGN KEY([avsSolicitudLiquidacionSubsidio])
REFERENCES [SolicitudLiquidacionSubsidio] ([slsId])
GO
ALTER TABLE [AplicacionValidacionSubsidio] CHECK CONSTRAINT [FK_AplicacionValidacionSubsidio_avsSolicitudLiquidacionSubsidio]
GO
ALTER TABLE [AporteDetallado]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetallado_apdAporteGeneral] FOREIGN KEY([apdAporteGeneral])
REFERENCES [AporteGeneral] ([apgId])
GO
ALTER TABLE [AporteDetallado] CHECK CONSTRAINT [FK_AporteDetallado_apdAporteGeneral]
GO
ALTER TABLE [AporteDetallado]  WITH CHECK ADD  CONSTRAINT [FK_AporteDetallado_apdPersona] FOREIGN KEY([apdPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [AporteDetallado] CHECK CONSTRAINT [FK_AporteDetallado_apdPersona]
GO
ALTER TABLE [AporteGeneral]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_apgCajaCompensacion] FOREIGN KEY([apgCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [AporteGeneral] CHECK CONSTRAINT [FK_AporteGeneral_apgCajaCompensacion]
GO
ALTER TABLE [AporteGeneral]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_apgEmpresa] FOREIGN KEY([apgEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [AporteGeneral] CHECK CONSTRAINT [FK_AporteGeneral_apgEmpresa]
GO
ALTER TABLE [AporteGeneral]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_apgEmpresaTramitadoraAporte] FOREIGN KEY([apgEmpresaTramitadoraAporte])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [AporteGeneral] CHECK CONSTRAINT [FK_AporteGeneral_apgEmpresaTramitadoraAporte]
GO
ALTER TABLE [AporteGeneral]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_apgOperadorInformacion] FOREIGN KEY([apgOperadorInformacion])
REFERENCES [OperadorInformacion] ([oinId])
GO
ALTER TABLE [AporteGeneral] CHECK CONSTRAINT [FK_AporteGeneral_apgOperadorInformacion]
GO
ALTER TABLE [AporteGeneral]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_apgPersona] FOREIGN KEY([apgPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [AporteGeneral] CHECK CONSTRAINT [FK_AporteGeneral_apgPersona]
GO
ALTER TABLE [AporteGeneral]  WITH CHECK ADD  CONSTRAINT [FK_AporteGeneral_apgSucursalEmpresa] FOREIGN KEY([apgSucursalEmpresa])
REFERENCES [SucursalEmpresa] ([sueId])
GO
ALTER TABLE [AporteGeneral] CHECK CONSTRAINT [FK_AporteGeneral_apgSucursalEmpresa]
GO
ALTER TABLE [ArchivoLiquidacionSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_ArchivoLiquidacionSubsidio_alsSolicitudLiquidacionSubsidio] FOREIGN KEY([alsSolicitudLiquidacionSubsidio])
REFERENCES [SolicitudLiquidacionSubsidio] ([slsId])
GO
ALTER TABLE [ArchivoLiquidacionSubsidio] CHECK CONSTRAINT [FK_ArchivoLiquidacionSubsidio_alsSolicitudLiquidacionSubsidio]
GO
ALTER TABLE [AsesorResponsableEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_AsesorResponsableEmpleador_areEmpleador] FOREIGN KEY([areEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [AsesorResponsableEmpleador] CHECK CONSTRAINT [FK_AsesorResponsableEmpleador_areEmpleador]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benAfiliado] FOREIGN KEY([benAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benAfiliado]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benBeneficiarioDetalle] FOREIGN KEY([benBeneficiarioDetalle])
REFERENCES [BeneficiarioDetalle] ([bedId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benBeneficiarioDetalle]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benGradoAcademico] FOREIGN KEY([benGradoAcademico])
REFERENCES [GradoAcademico] ([graId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benGradoAcademico]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benGrupoFamiliar] FOREIGN KEY([benGrupoFamiliar])
REFERENCES [GrupoFamiliar] ([grfId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benGrupoFamiliar]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benPersona] FOREIGN KEY([benPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benPersona]
GO
ALTER TABLE [Beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_Beneficiario_benRolAfiliado] FOREIGN KEY([benRolAfiliado])
REFERENCES [RolAfiliado] ([roaId])
GO
ALTER TABLE [Beneficiario] CHECK CONSTRAINT [FK_Beneficiario_benRolAfiliado]
GO
ALTER TABLE [BeneficiarioDetalle]  WITH CHECK ADD  CONSTRAINT [FK_BeneficiarioDetalle_bedPersonaDetalle] FOREIGN KEY([bedPersonaDetalle])
REFERENCES [PersonaDetalle] ([pedId])
GO
ALTER TABLE [BeneficiarioDetalle] CHECK CONSTRAINT [FK_BeneficiarioDetalle_bedPersonaDetalle]
GO
ALTER TABLE [BeneficioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_BeneficioEmpleador_bemBeneficio] FOREIGN KEY([bemBeneficio])
REFERENCES [Beneficio] ([befId])
GO
ALTER TABLE [BeneficioEmpleador] CHECK CONSTRAINT [FK_BeneficioEmpleador_bemBeneficio]
GO
ALTER TABLE [BeneficioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_BeneficioEmpleador_bemEmpleador] FOREIGN KEY([bemEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [BeneficioEmpleador] CHECK CONSTRAINT [FK_BeneficioEmpleador_bemEmpleador]
GO
ALTER TABLE [BitacoraCartera]  WITH CHECK ADD  CONSTRAINT [FK_BitacoraCartera_bcaPersona] FOREIGN KEY([bcaPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [BitacoraCartera] CHECK CONSTRAINT [FK_BitacoraCartera_bcaPersona]
GO
ALTER TABLE [CajaCompensacion]  WITH CHECK ADD  CONSTRAINT [FK_CajaCompensacion_ccfDepartamento] FOREIGN KEY([ccfDepartamento])
REFERENCES [Departamento] ([depId])
GO
ALTER TABLE [CajaCompensacion] CHECK CONSTRAINT [FK_CajaCompensacion_ccfDepartamento]
GO
ALTER TABLE [CampoArchivoConsumosAnibol]  WITH CHECK ADD  CONSTRAINT [FK_CampoArchivoConsumosAnibol_cacRegistroArchivoConsumosAnibol] FOREIGN KEY([caaRegistroArchivoConsumosAnibol])
REFERENCES [RegistroArchivoConsumosAnibol] ([racId])
GO
ALTER TABLE [CampoArchivoConsumosAnibol] CHECK CONSTRAINT [FK_CampoArchivoConsumosAnibol_cacRegistroArchivoConsumosAnibol]
GO
ALTER TABLE [CampoArchivoRetiroTerceroPagador]  WITH CHECK ADD  CONSTRAINT [FK_CampoArchivoRetiroTerceroPagador_carRegistroArchivoRetiroTerceroPagador] FOREIGN KEY([carRegistroArchivoRetiroTerceroPagador])
REFERENCES [RegistroArchivoRetiroTerceroPagador] ([rarId])
GO
ALTER TABLE [CampoArchivoRetiroTerceroPagador] CHECK CONSTRAINT [FK_CampoArchivoRetiroTerceroPagador_carRegistroArchivoRetiroTerceroPagador]
GO
ALTER TABLE [CargueArchivoCruceFovisAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisAfiliado_cfaCargueArchivoCruceFovis] FOREIGN KEY([cfaCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisAfiliado] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisAfiliado_cfaCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisBeneficiario]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisBeneficiario_cfbCargueArchivoCruceFovis] FOREIGN KEY([cfbCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisBeneficiario] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisBeneficiario_cfbCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisCatAnt]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisCatAnt_cfnCargueArchivoCruceFovis] FOREIGN KEY([cfnCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisCatAnt] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisCatAnt_cfnCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisCatBog]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisCatBog_cfoCargueArchivoCruceFovis] FOREIGN KEY([cfoCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisCatBog] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisCatBog_cfoCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisCatCali]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisCatCali_cflCargueArchivoCruceFovis] FOREIGN KEY([cflCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisCatCali] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisCatCali_cflCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisCatMed]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisCatMed_cfmCargueArchivoCruceFovis] FOREIGN KEY([cfmCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisCatMed] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisCatMed_cfmCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisCedula]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisCedula_cfcCargueArchivoCruceFovis] FOREIGN KEY([cfcCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisCedula] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisCedula_cfcCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisFechasCorte]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisFechasCorte_cffCargueArchivoCruceFovis] FOREIGN KEY([cffCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisFechasCorte] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisFechasCorte_cffCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisIGAC]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisIGAC_cfgCargueArchivoCruceFovis] FOREIGN KEY([cfgCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisIGAC] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisIGAC_cfgCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisNuevoHogar]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisNuevoHogar_cfhCargueArchivoCruceFovis] FOREIGN KEY([cfhCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisNuevoHogar] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisNuevoHogar_cfhCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisReunidos]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisReunidos_cfrCargueArchivoCruceFovis] FOREIGN KEY([cfrCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisReunidos] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisReunidos_cfrCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisSisben]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisSisben_cfsCargueArchivoCruceFovis] FOREIGN KEY([cfsCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisSisben] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisSisben_cfsCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueArchivoCruceFovisUnidos]  WITH CHECK ADD  CONSTRAINT [FK_CargueArchivoCruceFovisUnidos_cfuCargueArchivoCruceFovis] FOREIGN KEY([cfuCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [CargueArchivoCruceFovisUnidos] CHECK CONSTRAINT [FK_CargueArchivoCruceFovisUnidos_cfuCargueArchivoCruceFovis]
GO
ALTER TABLE [CargueMultiple]  WITH CHECK ADD  CONSTRAINT [FK_CargueAfiliacionMultiple_camIdSucursalEmpleador] FOREIGN KEY([camIdSucursalEmpleador])
REFERENCES [SucursalEmpresa] ([sueId])
GO
ALTER TABLE [CargueMultiple] CHECK CONSTRAINT [FK_CargueAfiliacionMultiple_camIdSucursalEmpleador]
GO
ALTER TABLE [CargueMultiple]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_camIdEmpleador] FOREIGN KEY([camIdEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [CargueMultiple] CHECK CONSTRAINT [FK_SucursalEmpresa_camIdEmpleador]
GO
ALTER TABLE [Cartera]  WITH CHECK ADD  CONSTRAINT [FK_Cartera_carPersona] FOREIGN KEY([carPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Cartera] CHECK CONSTRAINT [FK_Cartera_carPersona]
GO
ALTER TABLE [CarteraDependiente]  WITH CHECK ADD  CONSTRAINT [FK_CarteraDependiente_cadCartera] FOREIGN KEY([cadCartera])
REFERENCES [Cartera] ([carId])
GO
ALTER TABLE [CarteraDependiente] CHECK CONSTRAINT [FK_CarteraDependiente_cadCartera]
GO
ALTER TABLE [CarteraDependiente]  WITH CHECK ADD  CONSTRAINT [FK_CarteraDependiente_cadPersona] FOREIGN KEY([cadPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [CarteraDependiente] CHECK CONSTRAINT [FK_CarteraDependiente_cadPersona]
GO
ALTER TABLE [Categoria]  WITH CHECK ADD  CONSTRAINT [FK_Categoria_catIdAfiliado] FOREIGN KEY([catIdAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [Categoria] CHECK CONSTRAINT [FK_Categoria_catIdAfiliado]
GO
ALTER TABLE [Categoria]  WITH CHECK ADD  CONSTRAINT [FK_Categoria_catIdBeneficiario] FOREIGN KEY([catIdBeneficiario])
REFERENCES [Beneficiario] ([benId])
GO
ALTER TABLE [Categoria] CHECK CONSTRAINT [FK_Categoria_catIdBeneficiario]
GO
ALTER TABLE [CicloAportante]  WITH CHECK ADD  CONSTRAINT [FK_CicloAportante_capCicloCartera] FOREIGN KEY([capCicloCartera])
REFERENCES [CicloCartera] ([ccrId])
GO
ALTER TABLE [CicloAportante] CHECK CONSTRAINT [FK_CicloAportante_capCicloCartera]
GO
ALTER TABLE [CicloAportante]  WITH CHECK ADD  CONSTRAINT [FK_CicloAportante_capPersona] FOREIGN KEY([capPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [CicloAportante] CHECK CONSTRAINT [FK_CicloAportante_capPersona]
GO
ALTER TABLE [CicloAsignacion]  WITH CHECK ADD  CONSTRAINT [FK_CicloAsignacion_ciaCicloPredecesor] FOREIGN KEY([ciaCicloPredecesor])
REFERENCES [CicloAsignacion] ([ciaId])
GO
ALTER TABLE [CicloAsignacion] CHECK CONSTRAINT [FK_CicloAsignacion_ciaCicloPredecesor]
GO
ALTER TABLE [CicloModalidad]  WITH CHECK ADD  CONSTRAINT [FK_CicloModalidad_cmoCicloAsignacion] FOREIGN KEY([cmoCicloAsignacion])
REFERENCES [CicloAsignacion] ([ciaId])
GO
ALTER TABLE [CicloModalidad] CHECK CONSTRAINT [FK_CicloModalidad_cmoCicloAsignacion]
GO
ALTER TABLE [Comunicado]  WITH CHECK ADD  CONSTRAINT [FK_Comunicado_comPlantillaComunicado] FOREIGN KEY([comPlantillaComunicado])
REFERENCES [PlantillaComunicado] ([pcoId])
GO
ALTER TABLE [Comunicado] CHECK CONSTRAINT [FK_Comunicado_comPlantillaComunicado]
GO
ALTER TABLE [Comunicado]  WITH CHECK ADD  CONSTRAINT [FK_Comunicado_comSolicitud] FOREIGN KEY([comSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [Comunicado] CHECK CONSTRAINT [FK_Comunicado_comSolicitud]
GO
ALTER TABLE [CondicionEspecialPersona]  WITH CHECK ADD  CONSTRAINT [FK_CondicionEspecialPersona_cepPersona] FOREIGN KEY([cepPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [CondicionEspecialPersona] CHECK CONSTRAINT [FK_CondicionEspecialPersona_cepPersona]
GO
ALTER TABLE [CondicionInvalidez]  WITH CHECK ADD  CONSTRAINT [FK_CondicionInvalidez_coiPersona] FOREIGN KEY([coiPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [CondicionInvalidez] CHECK CONSTRAINT [FK_CondicionInvalidez_coiPersona]
GO
ALTER TABLE [CondicionVisita]  WITH CHECK ADD  CONSTRAINT [FK_CondicionVisita_covVisita] FOREIGN KEY([covVisita])
REFERENCES [Visita] ([visId])
GO
ALTER TABLE [CondicionVisita] CHECK CONSTRAINT [FK_CondicionVisita_covVisita]
GO
ALTER TABLE [ConexionOperadorInformacion]  WITH CHECK ADD  CONSTRAINT [FK_ConexionOperadorInformacion_coiOperadorInformacionCcf] FOREIGN KEY([coiOperadorInformacionCcf])
REFERENCES [OperadorInformacionCcf] ([oicId])
GO
ALTER TABLE [ConexionOperadorInformacion] CHECK CONSTRAINT [FK_ConexionOperadorInformacion_coiOperadorInformacionCcf]
GO
ALTER TABLE [ConvenioPago]  WITH CHECK ADD  CONSTRAINT [FK_ConvenioPago_copPersona] FOREIGN KEY([copPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [ConvenioPago] CHECK CONSTRAINT [FK_ConvenioPago_copPersona]
GO
ALTER TABLE [ConvenioPagoDependiente]  WITH CHECK ADD  CONSTRAINT [FK_ConvenioPagoDependiente_cpdPagoPeriodoConvenio] FOREIGN KEY([cpdPagoPeriodoConvenio])
REFERENCES [PagoPeriodoConvenio] ([ppcId])
GO
ALTER TABLE [ConvenioPagoDependiente] CHECK CONSTRAINT [FK_ConvenioPagoDependiente_cpdPagoPeriodoConvenio]
GO
ALTER TABLE [ConvenioPagoDependiente]  WITH CHECK ADD  CONSTRAINT [FK_ConvenioPagoDependiente_cpdPersona] FOREIGN KEY([cpdPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [ConvenioPagoDependiente] CHECK CONSTRAINT [FK_ConvenioPagoDependiente_cpdPersona]
GO
ALTER TABLE [ConvenioTerceroPagador]  WITH CHECK ADD  CONSTRAINT [FK_ConvenioTerceroPagador_conEmpresa] FOREIGN KEY([conEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [ConvenioTerceroPagador] CHECK CONSTRAINT [FK_ConvenioTerceroPagador_conEmpresa]
GO
ALTER TABLE [Correccion]  WITH CHECK ADD  CONSTRAINT [FK_Correccion_corAporteDetallado] FOREIGN KEY([corAporteDetallado])
REFERENCES [AporteDetallado] ([apdId])
GO
ALTER TABLE [Correccion] CHECK CONSTRAINT [FK_Correccion_corAporteDetallado]
GO
ALTER TABLE [Correccion]  WITH CHECK ADD  CONSTRAINT [FK_Correccion_corAporteGeneral] FOREIGN KEY([corAporteGeneral])
REFERENCES [AporteGeneral] ([apgId])
GO
ALTER TABLE [Correccion] CHECK CONSTRAINT [FK_Correccion_corAporteGeneral]
GO
ALTER TABLE [Correccion]  WITH CHECK ADD  CONSTRAINT [FK_Correccion_corSolicitudCorreccionAporte] FOREIGN KEY([corSolicitudCorreccionAporte])
REFERENCES [SolicitudCorreccionAporte] ([scaId])
GO
ALTER TABLE [Correccion] CHECK CONSTRAINT [FK_Correccion_corSolicitudCorreccionAporte]
GO
ALTER TABLE [Cruce]  WITH CHECK ADD  CONSTRAINT [FK_Cruce_cruCargueArchivoCruceFovis] FOREIGN KEY([cruCargueArchivoCruceFovis])
REFERENCES [CargueArchivoCruceFovis] ([cacId])
GO
ALTER TABLE [Cruce] CHECK CONSTRAINT [FK_Cruce_cruCargueArchivoCruceFovis]
GO
ALTER TABLE [Cruce]  WITH CHECK ADD  CONSTRAINT [FK_Cruce_cruEjecucionProcesoAsincrono] FOREIGN KEY([cruEjecucionProcesoAsincrono])
REFERENCES [EjecucionProcesoAsincrono] ([epsId])
GO
ALTER TABLE [Cruce] CHECK CONSTRAINT [FK_Cruce_cruEjecucionProcesoAsincrono]
GO
ALTER TABLE [Cruce]  WITH CHECK ADD  CONSTRAINT [FK_Cruce_cruPersona] FOREIGN KEY([cruPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Cruce] CHECK CONSTRAINT [FK_Cruce_cruPersona]
GO
ALTER TABLE [CruceDetalle]  WITH CHECK ADD  CONSTRAINT [FK_CruceDetalle_crdCruce] FOREIGN KEY([crdCruce])
REFERENCES [Cruce] ([cruId])
GO
ALTER TABLE [CruceDetalle] CHECK CONSTRAINT [FK_CruceDetalle_crdCruce]
GO
ALTER TABLE [CuentaAdministradorSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_CuentaAdministradorSubsidio_casAdministradorSubsidio] FOREIGN KEY([casAdministradorSubsidio])
REFERENCES [AdministradorSubsidio] ([asuId])
GO
ALTER TABLE [CuentaAdministradorSubsidio] CHECK CONSTRAINT [FK_CuentaAdministradorSubsidio_casAdministradorSubsidio]
GO
ALTER TABLE [CuentaAdministradorSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_CuentaAdministradorSubsidio_casMedioDePago] FOREIGN KEY([casMedioDePago])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [CuentaAdministradorSubsidio] CHECK CONSTRAINT [FK_CuentaAdministradorSubsidio_casMedioDePago]
GO
ALTER TABLE [CuentaAdministradorSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_CuentaAdministradorSubsidio_casSitioDeCobro] FOREIGN KEY([casSitioDeCobro])
REFERENCES [SitioPago] ([sipId])
GO
ALTER TABLE [CuentaAdministradorSubsidio] CHECK CONSTRAINT [FK_CuentaAdministradorSubsidio_casSitioDeCobro]
GO
ALTER TABLE [CuentaAdministradorSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_CuentaAdministradorSubsidio_casSitioDePago] FOREIGN KEY([casSitioDePago])
REFERENCES [SitioPago] ([sipId])
GO
ALTER TABLE [CuentaAdministradorSubsidio] CHECK CONSTRAINT [FK_CuentaAdministradorSubsidio_casSitioDePago]
GO
ALTER TABLE [DatoTemporalSolicitud]  WITH CHECK ADD  CONSTRAINT [FK_DatoTemporalSolicitud_dtsSolicitud] FOREIGN KEY([dtsSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [DatoTemporalSolicitud] CHECK CONSTRAINT [FK_DatoTemporalSolicitud_dtsSolicitud]
GO
ALTER TABLE [DesafiliacionAportante]  WITH CHECK ADD  CONSTRAINT [FK_DesafiliacionAportante_deaSolicitudDesafiliacion] FOREIGN KEY([deaSolicitudDesafiliacion])
REFERENCES [SolicitudDesafiliacion] ([sodId])
GO
ALTER TABLE [DesafiliacionAportante] CHECK CONSTRAINT [FK_DesafiliacionAportante_deaSolicitudDesafiliacion]
GO
ALTER TABLE [DescuentosSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DescuentosSubsidioAsignado_desDetalleSubsidioAsignado] FOREIGN KEY([desDetalleSubsidioAsignado])
REFERENCES [DetalleSubsidioAsignado] ([dsaId])
GO
ALTER TABLE [DescuentosSubsidioAsignado] CHECK CONSTRAINT [FK_DescuentosSubsidioAsignado_desDetalleSubsidioAsignado]
GO
ALTER TABLE [DescuentosSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DescuentosSubsidioAsignado_desEntidadDescuento] FOREIGN KEY([desEntidadDescuento])
REFERENCES [EntidadDescuento] ([endId])
GO
ALTER TABLE [DescuentosSubsidioAsignado] CHECK CONSTRAINT [FK_DescuentosSubsidioAsignado_desEntidadDescuento]
GO
ALTER TABLE [DestinatarioGrupo]  WITH CHECK ADD  CONSTRAINT [FK_DestinatarioGrupo_dgrGrupoPrioridad] FOREIGN KEY([dgrGrupoPrioridad])
REFERENCES [GrupoPrioridad] ([gprId])
GO
ALTER TABLE [DestinatarioGrupo] CHECK CONSTRAINT [FK_DestinatarioGrupo_dgrGrupoPrioridad]
GO
ALTER TABLE [DetalleComunicadoEnviado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleComunicadoEnviado_dceComunicado] FOREIGN KEY([dceComunicado])
REFERENCES [Comunicado] ([comId])
GO
ALTER TABLE [DetalleComunicadoEnviado] CHECK CONSTRAINT [FK_DetalleComunicadoEnviado_dceComunicado]
GO
ALTER TABLE [DetalleDatosRegistroValidacion]  WITH CHECK ADD  CONSTRAINT [FK_DetalleDatosRegistroValidacion_ddrvIdDato] FOREIGN KEY([ddrIdDato])
REFERENCES [DatosRegistroValidacion] ([drvId])
GO
ALTER TABLE [DetalleDatosRegistroValidacion] CHECK CONSTRAINT [FK_DetalleDatosRegistroValidacion_ddrvIdDato]
GO
ALTER TABLE [DetalleSolicitudAnulacionSubsidioCobrado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSolicitudAnulacionSubsidioCobrado_dssCuentaAdministradorSubisdio] FOREIGN KEY([dssCuentaAdministradorSubisdio])
REFERENCES [CuentaAdministradorSubsidio] ([casId])
GO
ALTER TABLE [DetalleSolicitudAnulacionSubsidioCobrado] CHECK CONSTRAINT [FK_DetalleSolicitudAnulacionSubsidioCobrado_dssCuentaAdministradorSubisdio]
GO
ALTER TABLE [DetalleSolicitudAnulacionSubsidioCobrado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSolicitudAnulacionSubsidioCobrado_dssSolicitudAnulacionSubsidioCobrado] FOREIGN KEY([dssSolicitudAnulacionSubsidioCobrado])
REFERENCES [SolicitudAnulacionSubsidioCobrado] ([sasId])
GO
ALTER TABLE [DetalleSolicitudAnulacionSubsidioCobrado] CHECK CONSTRAINT [FK_DetalleSolicitudAnulacionSubsidioCobrado_dssSolicitudAnulacionSubsidioCobrado]
GO
ALTER TABLE [DetalleSolicitudGestionCobro]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSolicitudGestionCobro_dsgDocumentoPrimeraRemision] FOREIGN KEY([dsgDocumentoPrimeraRemision])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [DetalleSolicitudGestionCobro] CHECK CONSTRAINT [FK_DetalleSolicitudGestionCobro_dsgDocumentoPrimeraRemision]
GO
ALTER TABLE [DetalleSolicitudGestionCobro]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSolicitudGestionCobro_dsgDocumentoSegundaRemision] FOREIGN KEY([dsgDocumentoSegundaRemision])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [DetalleSolicitudGestionCobro] CHECK CONSTRAINT [FK_DetalleSolicitudGestionCobro_dsgDocumentoSegundaRemision]
GO
ALTER TABLE [DetalleSolicitudGestionCobro]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSolicitudGestionCobro_dsgSolicitudPrimeraRemision] FOREIGN KEY([dsgSolicitudPrimeraRemision])
REFERENCES [SolicitudGestionCobroFisico] ([sgfId])
GO
ALTER TABLE [DetalleSolicitudGestionCobro] CHECK CONSTRAINT [FK_DetalleSolicitudGestionCobro_dsgSolicitudPrimeraRemision]
GO
ALTER TABLE [DetalleSolicitudGestionCobro]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSolicitudGestionCobro_dsgSolicitudSegundaRemision] FOREIGN KEY([dsgSolicitudSegundaRemision])
REFERENCES [SolicitudGestionCobroFisico] ([sgfId])
GO
ALTER TABLE [DetalleSolicitudGestionCobro] CHECK CONSTRAINT [FK_DetalleSolicitudGestionCobro_dsgSolicitudSegundaRemision]
GO
ALTER TABLE [DetalleSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSubsidioAsignado_dsaAdministradorSubsidio] FOREIGN KEY([dsaAdministradorSubsidio])
REFERENCES [AdministradorSubsidio] ([asuId])
GO
ALTER TABLE [DetalleSubsidioAsignado] CHECK CONSTRAINT [FK_DetalleSubsidioAsignado_dsaAdministradorSubsidio]
GO
ALTER TABLE [DetalleSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSubsidioAsignado_dsaAfiliadoPrincipal] FOREIGN KEY([dsaAfiliadoPrincipal])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [DetalleSubsidioAsignado] CHECK CONSTRAINT [FK_DetalleSubsidioAsignado_dsaAfiliadoPrincipal]
GO
ALTER TABLE [DetalleSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSubsidioAsignado_dsaBeneficiarioDetalle] FOREIGN KEY([dsaBeneficiarioDetalle])
REFERENCES [BeneficiarioDetalle] ([bedId])
GO
ALTER TABLE [DetalleSubsidioAsignado] CHECK CONSTRAINT [FK_DetalleSubsidioAsignado_dsaBeneficiarioDetalle]
GO
ALTER TABLE [DetalleSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSubsidioAsignado_dsaCuentaAdministradorSubsidio] FOREIGN KEY([dsaCuentaAdministradorSubsidio])
REFERENCES [CuentaAdministradorSubsidio] ([casId])
GO
ALTER TABLE [DetalleSubsidioAsignado] CHECK CONSTRAINT [FK_DetalleSubsidioAsignado_dsaCuentaAdministradorSubsidio]
GO
ALTER TABLE [DetalleSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSubsidioAsignado_dsaEmpleador] FOREIGN KEY([dsaEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [DetalleSubsidioAsignado] CHECK CONSTRAINT [FK_DetalleSubsidioAsignado_dsaEmpleador]
GO
ALTER TABLE [DetalleSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSubsidioAsignado_dsaGrupoFamiliar] FOREIGN KEY([dsaGrupoFamiliar])
REFERENCES [GrupoFamiliar] ([grfId])
GO
ALTER TABLE [DetalleSubsidioAsignado] CHECK CONSTRAINT [FK_DetalleSubsidioAsignado_dsaGrupoFamiliar]
GO
ALTER TABLE [DetalleSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSubsidioAsignado_dsaIdRegistroOriginalRelacionado] FOREIGN KEY([dsaIdRegistroOriginalRelacionado])
REFERENCES [DetalleSubsidioAsignado] ([dsaId])
GO
ALTER TABLE [DetalleSubsidioAsignado] CHECK CONSTRAINT [FK_DetalleSubsidioAsignado_dsaIdRegistroOriginalRelacionado]
GO
ALTER TABLE [DetalleSubsidioAsignado]  WITH CHECK ADD  CONSTRAINT [FK_DetalleSubsidioAsignado_dsaSolicitudLiquidacionSubsidio] FOREIGN KEY([dsaSolicitudLiquidacionSubsidio])
REFERENCES [SolicitudLiquidacionSubsidio] ([slsId])
GO
ALTER TABLE [DetalleSubsidioAsignado] CHECK CONSTRAINT [FK_DetalleSubsidioAsignado_dsaSolicitudLiquidacionSubsidio]
GO
ALTER TABLE [DevolucionAporte]  WITH CHECK ADD  CONSTRAINT [FK_DevolucionAporte_dapCajaCompensacion] FOREIGN KEY([dapCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [DevolucionAporte] CHECK CONSTRAINT [FK_DevolucionAporte_dapCajaCompensacion]
GO
ALTER TABLE [DevolucionAporte]  WITH CHECK ADD  CONSTRAINT [FK_DevolucionAporte_dapMedioPago] FOREIGN KEY([dapMedioPago])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [DevolucionAporte] CHECK CONSTRAINT [FK_DevolucionAporte_dapMedioPago]
GO
ALTER TABLE [DevolucionAporte]  WITH CHECK ADD  CONSTRAINT [FK_DevolucionAporte_dapSedeCajaCompensacion] FOREIGN KEY([dapSedeCajaCompensacion])
REFERENCES [SedeCajaCompensacion] ([sccfId])
GO
ALTER TABLE [DevolucionAporte] CHECK CONSTRAINT [FK_DevolucionAporte_dapSedeCajaCompensacion]
GO
ALTER TABLE [DevolucionAporteDetalle]  WITH CHECK ADD  CONSTRAINT [FK_DevolucionAporteDetalle_dadDevolucionAporte] FOREIGN KEY([dadDevolucionAporte])
REFERENCES [DevolucionAporte] ([dapId])
GO
ALTER TABLE [DevolucionAporteDetalle] CHECK CONSTRAINT [FK_DevolucionAporteDetalle_dadDevolucionAporte]
GO
ALTER TABLE [DevolucionAporteDetalle]  WITH CHECK ADD  CONSTRAINT [FK_DevolucionAporteDetalle_dadMovimientoAporte] FOREIGN KEY([dadMovimientoAporte])
REFERENCES [MovimientoAporte] ([moaId])
GO
ALTER TABLE [DevolucionAporteDetalle] CHECK CONSTRAINT [FK_DevolucionAporteDetalle_dadMovimientoAporte]
GO
ALTER TABLE [DiferenciasCargueActualizacion]  WITH CHECK ADD  CONSTRAINT [FK_DiferenciasCargueActualizacion_dicCargueArchivoActualizacion] FOREIGN KEY([dicCargueArchivoActualizacion])
REFERENCES [CargueArchivoActualizacion] ([caaId])
GO
ALTER TABLE [DiferenciasCargueActualizacion] CHECK CONSTRAINT [FK_DiferenciasCargueActualizacion_dicCargueArchivoActualizacion]
GO
ALTER TABLE [DocumentoBitacora]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoBitacora_dbiBitacoraCartera] FOREIGN KEY([dbiBitacoraCartera])
REFERENCES [BitacoraCartera] ([bcaId])
GO
ALTER TABLE [DocumentoBitacora] CHECK CONSTRAINT [FK_DocumentoBitacora_dbiBitacoraCartera]
GO
ALTER TABLE [DocumentoBitacora]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoBitacora_dbiDocumentoSoporte] FOREIGN KEY([dbiDocumentoSoporte])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [DocumentoBitacora] CHECK CONSTRAINT [FK_DocumentoBitacora_dbiDocumentoSoporte]
GO
ALTER TABLE [DocumentoCartera]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoCartera_dcaCartera] FOREIGN KEY([dcaCartera])
REFERENCES [Cartera] ([carId])
GO
ALTER TABLE [DocumentoCartera] CHECK CONSTRAINT [FK_DocumentoCartera_dcaCartera]
GO
ALTER TABLE [DocumentoCartera]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoCartera_dcaDocumentoSoporte] FOREIGN KEY([dcaDocumentoSoporte])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [DocumentoCartera] CHECK CONSTRAINT [FK_DocumentoCartera_dcaDocumentoSoporte]
GO
ALTER TABLE [DocumentoDesafiliacion]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoDesafiliacion_dodDocumentoSoporte] FOREIGN KEY([dodDocumentoSoporte])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [DocumentoDesafiliacion] CHECK CONSTRAINT [FK_DocumentoDesafiliacion_dodDocumentoSoporte]
GO
ALTER TABLE [DocumentoDesafiliacion]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoDesafiliacion_dodSolicitudDesafiliacion] FOREIGN KEY([dodSolicitudDesafiliacion])
REFERENCES [SolicitudDesafiliacion] ([sodId])
GO
ALTER TABLE [DocumentoDesafiliacion] CHECK CONSTRAINT [FK_DocumentoDesafiliacion_dodSolicitudDesafiliacion]
GO
ALTER TABLE [DocumentoEntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoEntidadPagadora_dpgEntidadPagadora] FOREIGN KEY([dpgEntidadPagadora])
REFERENCES [EntidadPagadora] ([epaId])
GO
ALTER TABLE [DocumentoEntidadPagadora] CHECK CONSTRAINT [FK_DocumentoEntidadPagadora_dpgEntidadPagadora]
GO
ALTER TABLE [DocumentoSoporteConvenio]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporteConvenio_dscConvenioTerceroPagador] FOREIGN KEY([dscConvenioTerceroPagador])
REFERENCES [ConvenioTerceroPagador] ([conId])
GO
ALTER TABLE [DocumentoSoporteConvenio] CHECK CONSTRAINT [FK_DocumentoSoporteConvenio_dscConvenioTerceroPagador]
GO
ALTER TABLE [DocumentoSoporteOferente]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporteOferente_dsoDocumentoSoporte] FOREIGN KEY([dsoDocumentoSoporte])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [DocumentoSoporteOferente] CHECK CONSTRAINT [FK_DocumentoSoporteOferente_dsoDocumentoSoporte]
GO
ALTER TABLE [DocumentoSoporteOferente]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporteOferente_dsoOferente] FOREIGN KEY([dsoOferente])
REFERENCES [Oferente] ([ofeId])
GO
ALTER TABLE [DocumentoSoporteOferente] CHECK CONSTRAINT [FK_DocumentoSoporteOferente_dsoOferente]
GO
ALTER TABLE [DocumentoSoporteProyectoVivienda]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporteProyectoVivienda_dspDocumentoSoporte] FOREIGN KEY([dspDocumentoSoporte])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [DocumentoSoporteProyectoVivienda] CHECK CONSTRAINT [FK_DocumentoSoporteProyectoVivienda_dspDocumentoSoporte]
GO
ALTER TABLE [DocumentoSoporteProyectoVivienda]  WITH CHECK ADD  CONSTRAINT [FK_DocumentoSoporteProyectoVivienda_dspProyectoSolucionVivienda] FOREIGN KEY([dspProyectoSolucionVivienda])
REFERENCES [ProyectoSolucionVivienda] ([psvId])
GO
ALTER TABLE [DocumentoSoporteProyectoVivienda] CHECK CONSTRAINT [FK_DocumentoSoporteProyectoVivienda_dspProyectoSolucionVivienda]
GO
ALTER TABLE [EjecucionProgramada]  WITH CHECK ADD  CONSTRAINT [FK_EjecucionProgramada_ejpCajaCompensacion] FOREIGN KEY([ejpCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [EjecucionProgramada] CHECK CONSTRAINT [FK_EjecucionProgramada_ejpCajaCompensacion]
GO
ALTER TABLE [Empleador]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_empEmpresa] FOREIGN KEY([empEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [Empleador] CHECK CONSTRAINT [FK_Empleador_empEmpresa]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_Empresa_empUbicacionRepresentanteLegal] FOREIGN KEY([empUbicacionRepresentanteLegal])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_Empresa_empUbicacionRepresentanteLegal]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_Empresa_empUbicacionRepresentanteLegalSuplente] FOREIGN KEY([empUbicacionRepresentanteLegalSuplente])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_Empresa_empUbicacionRepresentanteLegalSuplente]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_empresa_empUltimaCajaCompensacion] FOREIGN KEY([empUltimaCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_empresa_empUltimaCajaCompensacion]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empArl] FOREIGN KEY([empArl])
REFERENCES [ARL] ([arlId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empArl]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empCodigoCIIU] FOREIGN KEY([empCodigoCIIU])
REFERENCES [CodigoCIIU] ([ciiId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empCodigoCIIU]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empPersona] FOREIGN KEY([empPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empPersona]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empRepresentanteLegal] FOREIGN KEY([empRepresentanteLegal])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empRepresentanteLegal]
GO
ALTER TABLE [Empresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_empRepresentanteLegalSuplente] FOREIGN KEY([empRepresentanteLegalSuplente])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Empresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_empRepresentanteLegalSuplente]
GO
ALTER TABLE [EntidadDescuento]  WITH CHECK ADD  CONSTRAINT [FK_EntidadDescuento_endEmpresa] FOREIGN KEY([endEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [EntidadDescuento] CHECK CONSTRAINT [FK_EntidadDescuento_endEmpresa]
GO
ALTER TABLE [EntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_Empleador_epaEmpresa] FOREIGN KEY([epaEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [EntidadPagadora] CHECK CONSTRAINT [FK_Empleador_epaEmpresa]
GO
ALTER TABLE [EntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_EntidadPagadora_epaSucursalPagadora] FOREIGN KEY([epaSucursalPagadora])
REFERENCES [SucursalEmpresa] ([sueId])
GO
ALTER TABLE [EntidadPagadora] CHECK CONSTRAINT [FK_EntidadPagadora_epaSucursalPagadora]
GO
ALTER TABLE [EscalamientoSolicitud]  WITH CHECK ADD  CONSTRAINT [FK_EscalamientoSolicitud_esoSolicitud] FOREIGN KEY([esoSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [EscalamientoSolicitud] CHECK CONSTRAINT [FK_EscalamientoSolicitud_esoSolicitud]
GO
ALTER TABLE [EstadoCondicionValidacionSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_EstadoCondicionValidacionSubsidio_ecvAplicacionValidacionSubsidio] FOREIGN KEY([ecvAplicacionValidacionSubsidio])
REFERENCES [AplicacionValidacionSubsidio] ([avsId])
GO
ALTER TABLE [EstadoCondicionValidacionSubsidio] CHECK CONSTRAINT [FK_EstadoCondicionValidacionSubsidio_ecvAplicacionValidacionSubsidio]
GO
ALTER TABLE [ExclusionCartera]  WITH CHECK ADD  CONSTRAINT [FK_ExclusionCartera_excPersona] FOREIGN KEY([excPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [ExclusionCartera] CHECK CONSTRAINT [FK_ExclusionCartera_excPersona]
GO
ALTER TABLE [ExpulsionSubsanada]  WITH CHECK ADD  CONSTRAINT [FK_ExpulsionSubsanada_exsEmpleador] FOREIGN KEY([exsEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [ExpulsionSubsanada] CHECK CONSTRAINT [FK_ExpulsionSubsanada_exsEmpleador]
GO
ALTER TABLE [ExpulsionSubsanada]  WITH CHECK ADD  CONSTRAINT [FK_ExpulsionSubsanada_exsRolAfiliado] FOREIGN KEY([exsRolAfiliado])
REFERENCES [RolAfiliado] ([roaId])
GO
ALTER TABLE [ExpulsionSubsanada] CHECK CONSTRAINT [FK_ExpulsionSubsanada_exsRolAfiliado]
GO
ALTER TABLE [FieldCatalog]  WITH CHECK ADD  CONSTRAINT [FK_FieldCatalog_fieldGCatalog_id] FOREIGN KEY([fieldGCatalog_id])
REFERENCES [FieldGenericCatalog] ([id])
GO
ALTER TABLE [FieldCatalog] CHECK CONSTRAINT [FK_FieldCatalog_fieldGCatalog_id]
GO
ALTER TABLE [FieldCatalog]  WITH CHECK ADD  CONSTRAINT [FK_FieldCatalog_idLineCatalog] FOREIGN KEY([idLineCatalog])
REFERENCES [LineCatalog] ([id])
GO
ALTER TABLE [FieldCatalog] CHECK CONSTRAINT [FK_FieldCatalog_idLineCatalog]
GO
ALTER TABLE [FieldDefinition]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinition_fieldCatalog_id] FOREIGN KEY([fieldCatalog_id])
REFERENCES [FieldCatalog] ([id])
GO
ALTER TABLE [FieldDefinition] CHECK CONSTRAINT [FK_FieldDefinition_fieldCatalog_id]
GO
ALTER TABLE [FieldDefinition]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinition_lineDefinition_id] FOREIGN KEY([lineDefinition_id])
REFERENCES [LineDefinition] ([id])
GO
ALTER TABLE [FieldDefinition] CHECK CONSTRAINT [FK_FieldDefinition_lineDefinition_id]
GO
ALTER TABLE [FieldDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinitionLoad_fieldLoadCatalog_id] FOREIGN KEY([fieldLoadCatalog_id])
REFERENCES [FieldLoadCatalog] ([id])
GO
ALTER TABLE [FieldDefinitionLoad] CHECK CONSTRAINT [FK_FieldDefinitionLoad_fieldLoadCatalog_id]
GO
ALTER TABLE [FieldDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_FieldDefinitionLoad_lineDefinition_id] FOREIGN KEY([lineDefinition_id])
REFERENCES [LineDefinitionLoad] ([id])
GO
ALTER TABLE [FieldDefinitionLoad] CHECK CONSTRAINT [FK_FieldDefinitionLoad_lineDefinition_id]
GO
ALTER TABLE [FileDefinition]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinition_fileDefinitionType_id] FOREIGN KEY([fileDefinitionType_id])
REFERENCES [FileDefinitionType] ([id])
GO
ALTER TABLE [FileDefinition] CHECK CONSTRAINT [FK_FileDefinition_fileDefinitionType_id]
GO
ALTER TABLE [FileDefinition]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinition_fileLocation_id] FOREIGN KEY([fileLocation_id])
REFERENCES [FileLocationCommon] ([id])
GO
ALTER TABLE [FileDefinition] CHECK CONSTRAINT [FK_FileDefinition_fileLocation_id]
GO
ALTER TABLE [FileDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_FileDefinitionLoad_fileDefinitionType_id] FOREIGN KEY([fileDefinitionType_id])
REFERENCES [FileDefinitionLoadType] ([id])
GO
ALTER TABLE [FileDefinitionLoad] CHECK CONSTRAINT [FK_FileDefinitionLoad_fileDefinitionType_id]
GO
ALTER TABLE [FileGenerated]  WITH CHECK ADD  CONSTRAINT [FK_FileGenerated_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinition] ([id])
GO
ALTER TABLE [FileGenerated] CHECK CONSTRAINT [FK_FileGenerated_fileDefinition_id]
GO
ALTER TABLE [FileGeneratedLog]  WITH CHECK ADD  CONSTRAINT [FK_FileGeneratedLog_fileGenerated_id] FOREIGN KEY([fileGenerated_id])
REFERENCES [FileGenerated] ([id])
GO
ALTER TABLE [FileGeneratedLog] CHECK CONSTRAINT [FK_FileGeneratedLog_fileGenerated_id]
GO
ALTER TABLE [FileLoaded]  WITH CHECK ADD  CONSTRAINT [FK_FileLoaded_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinitionLoad] ([id])
GO
ALTER TABLE [FileLoaded] CHECK CONSTRAINT [FK_FileLoaded_fileDefinition_id]
GO
ALTER TABLE [FileLoadedLog]  WITH CHECK ADD  CONSTRAINT [FK_FileLoadedLog_fileLoaded_id] FOREIGN KEY([fileLoaded_id])
REFERENCES [FileLoaded] ([id])
GO
ALTER TABLE [FileLoadedLog] CHECK CONSTRAINT [FK_FileLoadedLog_fileLoaded_id]
GO
ALTER TABLE [GestionNotiNoEnviadas]  WITH CHECK ADD  CONSTRAINT [FK_GestionNotiNoEnviadas_gneEmpresa] FOREIGN KEY([gneEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [GestionNotiNoEnviadas] CHECK CONSTRAINT [FK_GestionNotiNoEnviadas_gneEmpresa]
GO
ALTER TABLE [GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeatureDefinition_fieldDefinition_id] FOREIGN KEY([fieldDefinition_id])
REFERENCES [FieldDefinition] ([id])
GO
ALTER TABLE [GraphicFeatureDefinition] CHECK CONSTRAINT [FK_GraphicFeatureDefinition_fieldDefinition_id]
GO
ALTER TABLE [GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeatureDefinition_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinition] ([id])
GO
ALTER TABLE [GraphicFeatureDefinition] CHECK CONSTRAINT [FK_GraphicFeatureDefinition_fileDefinition_id]
GO
ALTER TABLE [GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeatureDefinition_graphicFeature_id] FOREIGN KEY([graphicFeature_id])
REFERENCES [GraphicFeature] ([id])
GO
ALTER TABLE [GraphicFeatureDefinition] CHECK CONSTRAINT [FK_GraphicFeatureDefinition_graphicFeature_id]
GO
ALTER TABLE [GraphicFeatureDefinition]  WITH CHECK ADD  CONSTRAINT [FK_GraphicFeatureDefinition_lineDefinition_id] FOREIGN KEY([lineDefinition_id])
REFERENCES [LineDefinition] ([id])
GO
ALTER TABLE [GraphicFeatureDefinition] CHECK CONSTRAINT [FK_GraphicFeatureDefinition_lineDefinition_id]
GO
ALTER TABLE [GroupFC_FieldGC]  WITH CHECK ADD  CONSTRAINT [FK_GroupFC_FieldGC_fieldGenericCatalogs_id] FOREIGN KEY([fieldGenericCatalogs_id])
REFERENCES [FieldGenericCatalog] ([id])
GO
ALTER TABLE [GroupFC_FieldGC] CHECK CONSTRAINT [FK_GroupFC_FieldGC_fieldGenericCatalogs_id]
GO
ALTER TABLE [GroupFC_FieldGC]  WITH CHECK ADD  CONSTRAINT [FK_GroupFC_FieldGC_groupFieldCatalogs_id] FOREIGN KEY([groupFieldCatalogs_id])
REFERENCES [GroupFieldCatalogs] ([id])
GO
ALTER TABLE [GroupFC_FieldGC] CHECK CONSTRAINT [FK_GroupFC_FieldGC_groupFieldCatalogs_id]
GO
ALTER TABLE [GrupoFamiliar]  WITH CHECK ADD  CONSTRAINT [FK_GrupoFamiliar_grfAfiliado] FOREIGN KEY([grfAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [GrupoFamiliar] CHECK CONSTRAINT [FK_GrupoFamiliar_grfAfiliado]
GO
ALTER TABLE [GrupoFamiliar]  WITH CHECK ADD  CONSTRAINT [FK_GrupoFamiliar_grfUbicacion] FOREIGN KEY([grfUbicacion])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [GrupoFamiliar] CHECK CONSTRAINT [FK_GrupoFamiliar_grfUbicacion]
GO
ALTER TABLE [GrupoRequisito]  WITH CHECK ADD  CONSTRAINT [FK_GrupoRequisito_grqRequisitoCajaCompensacion] FOREIGN KEY([grqRequisitoCajaClasificacion])
REFERENCES [RequisitoCajaClasificacion] ([rtsId])
GO
ALTER TABLE [GrupoRequisito] CHECK CONSTRAINT [FK_GrupoRequisito_grqRequisitoCajaCompensacion]
GO
ALTER TABLE [HistoriaResultadoValidacion]  WITH CHECK ADD  CONSTRAINT [FK_HistoriaResultadoValidacion_hrvIdDatosRegistro] FOREIGN KEY([hrvIdDatosRegistro])
REFERENCES [DatosRegistroValidacion] ([drvId])
GO
ALTER TABLE [HistoriaResultadoValidacion] CHECK CONSTRAINT [FK_HistoriaResultadoValidacion_hrvIdDatosRegistro]
GO
ALTER TABLE [InformacionFaltanteAportante]  WITH CHECK ADD  CONSTRAINT [FK_InformacionFaltanteAportante_ifaSolicitud] FOREIGN KEY([ifaSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [InformacionFaltanteAportante] CHECK CONSTRAINT [FK_InformacionFaltanteAportante_ifaSolicitud]
GO
ALTER TABLE [Infraestructura]  WITH CHECK ADD  CONSTRAINT [FK_Infraestructura_infTipoInfraestructura] FOREIGN KEY([infTipoInfraestructura])
REFERENCES [TipoInfraestructura] ([tifId])
GO
ALTER TABLE [Infraestructura] CHECK CONSTRAINT [FK_Infraestructura_infTipoInfraestructura]
GO
ALTER TABLE [Infraestructura]  WITH CHECK ADD  CONSTRAINT [FK_Infraestructura_infTipoTenencia] FOREIGN KEY([infTipoTenencia])
REFERENCES [TipoTenencia] ([tenId])
GO
ALTER TABLE [Infraestructura] CHECK CONSTRAINT [FK_Infraestructura_infTipoTenencia]
GO
ALTER TABLE [IntegranteHogar]  WITH CHECK ADD  CONSTRAINT [FK_IntegranteHogar_inhJefeHogar] FOREIGN KEY([inhJefeHogar])
REFERENCES [JefeHogar] ([jehId])
GO
ALTER TABLE [IntegranteHogar] CHECK CONSTRAINT [FK_IntegranteHogar_inhJefeHogar]
GO
ALTER TABLE [IntegranteHogar]  WITH CHECK ADD  CONSTRAINT [FK_IntegranteHogar_inhPersona] FOREIGN KEY([inhPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [IntegranteHogar] CHECK CONSTRAINT [FK_IntegranteHogar_inhPersona]
GO
ALTER TABLE [IntentoAfiliacion]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliacion_iafSolicitud] FOREIGN KEY([iafSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [IntentoAfiliacion] CHECK CONSTRAINT [FK_IntentoAfiliacion_iafSolicitud]
GO
ALTER TABLE [IntentoAfiliRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliRequisito_iarIntentoAfiliacion] FOREIGN KEY([iarIntentoAfiliacion])
REFERENCES [IntentoAfiliacion] ([iafId])
GO
ALTER TABLE [IntentoAfiliRequisito] CHECK CONSTRAINT [FK_IntentoAfiliRequisito_iarIntentoAfiliacion]
GO
ALTER TABLE [IntentoAfiliRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoAfiliRequisito_iarRequisito] FOREIGN KEY([iarRequisito])
REFERENCES [Requisito] ([reqId])
GO
ALTER TABLE [IntentoAfiliRequisito] CHECK CONSTRAINT [FK_IntentoAfiliRequisito_iarRequisito]
GO
ALTER TABLE [IntentoLegalizacionDesembolso]  WITH CHECK ADD  CONSTRAINT [FK_IntentoLegalizacionDesembolso_ildSolicitud] FOREIGN KEY([ildSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [IntentoLegalizacionDesembolso] CHECK CONSTRAINT [FK_IntentoLegalizacionDesembolso_ildSolicitud]
GO
ALTER TABLE [IntentoLegalizacionDesembolsoRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoLegalizacionDesembolsoRequisito_ilrIntentoLegalizacionDesembolso] FOREIGN KEY([ilrIntentoLegalizacionDesembolso])
REFERENCES [IntentoLegalizacionDesembolso] ([ildId])
GO
ALTER TABLE [IntentoLegalizacionDesembolsoRequisito] CHECK CONSTRAINT [FK_IntentoLegalizacionDesembolsoRequisito_ilrIntentoLegalizacionDesembolso]
GO
ALTER TABLE [IntentoLegalizacionDesembolsoRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoLegalizacionDesembolsoRequisito_ilrRequisito] FOREIGN KEY([ilrRequisito])
REFERENCES [Requisito] ([reqId])
GO
ALTER TABLE [IntentoLegalizacionDesembolsoRequisito] CHECK CONSTRAINT [FK_IntentoLegalizacionDesembolsoRequisito_ilrRequisito]
GO
ALTER TABLE [IntentoNovedad]  WITH CHECK ADD  CONSTRAINT [FK_IntentoNovedad_inoSolicitud] FOREIGN KEY([inoSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [IntentoNovedad] CHECK CONSTRAINT [FK_IntentoNovedad_inoSolicitud]
GO
ALTER TABLE [IntentoNoveRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoNoveRequisito_inrIntentoNovedad] FOREIGN KEY([inrIntentoNovedad])
REFERENCES [IntentoNovedad] ([inoId])
GO
ALTER TABLE [IntentoNoveRequisito] CHECK CONSTRAINT [FK_IntentoNoveRequisito_inrIntentoNovedad]
GO
ALTER TABLE [IntentoPostulacion]  WITH CHECK ADD  CONSTRAINT [FK_IntentoPostulacion_ipoSolicitud] FOREIGN KEY([ipoSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [IntentoPostulacion] CHECK CONSTRAINT [FK_IntentoPostulacion_ipoSolicitud]
GO
ALTER TABLE [IntentoPostulacionRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoPostulacionRequisito_iprIntentoPostulacion] FOREIGN KEY([iprIntentoPostulacion])
REFERENCES [IntentoPostulacion] ([ipoId])
GO
ALTER TABLE [IntentoPostulacionRequisito] CHECK CONSTRAINT [FK_IntentoPostulacionRequisito_iprIntentoPostulacion]
GO
ALTER TABLE [IntentoPostulacionRequisito]  WITH CHECK ADD  CONSTRAINT [FK_IntentoPostulacionRequisito_iprRequisito] FOREIGN KEY([iprRequisito])
REFERENCES [Requisito] ([reqId])
GO
ALTER TABLE [IntentoPostulacionRequisito] CHECK CONSTRAINT [FK_IntentoPostulacionRequisito_iprRequisito]
GO
ALTER TABLE [ItemChequeo]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeo_ichPersona] FOREIGN KEY([ichPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [ItemChequeo] CHECK CONSTRAINT [FK_ItemChequeo_ichPersona]
GO
ALTER TABLE [ItemChequeo]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeo_ichRequisito] FOREIGN KEY([ichRequisito])
REFERENCES [Requisito] ([reqId])
GO
ALTER TABLE [ItemChequeo] CHECK CONSTRAINT [FK_ItemChequeo_ichRequisito]
GO
ALTER TABLE [ItemChequeo]  WITH CHECK ADD  CONSTRAINT [FK_ItemChequeo_ichSolicitud] FOREIGN KEY([ichSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [ItemChequeo] CHECK CONSTRAINT [FK_ItemChequeo_ichSolicitud]
GO
ALTER TABLE [JefeHogar]  WITH CHECK ADD  CONSTRAINT [FK_JefeHogar_jehAfiliado] FOREIGN KEY([jehAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [JefeHogar] CHECK CONSTRAINT [FK_JefeHogar_jehAfiliado]
GO
ALTER TABLE [LegalizacionDesembolso]  WITH CHECK ADD  CONSTRAINT [FK_LegalizacionDesembolso_lgdVisita] FOREIGN KEY([lgdVisita])
REFERENCES [Visita] ([visId])
GO
ALTER TABLE [LegalizacionDesembolso] CHECK CONSTRAINT [FK_LegalizacionDesembolso_lgdVisita]
GO
ALTER TABLE [Licencia]  WITH CHECK ADD  CONSTRAINT [FK_Licencia_licProyectoSolucionVivienda] FOREIGN KEY([licProyectoSolucionVivienda])
REFERENCES [ProyectoSolucionVivienda] ([psvId])
GO
ALTER TABLE [Licencia] CHECK CONSTRAINT [FK_Licencia_licProyectoSolucionVivienda]
GO
ALTER TABLE [LicenciaDetalle]  WITH CHECK ADD  CONSTRAINT [FK_LicenciaDetalle_lidLicencia] FOREIGN KEY([lidLicencia])
REFERENCES [Licencia] ([licId])
GO
ALTER TABLE [LicenciaDetalle] CHECK CONSTRAINT [FK_LicenciaDetalle_lidLicencia]
GO
ALTER TABLE [LineaCobro]  WITH CHECK ADD  CONSTRAINT [FK_LineaCobro_lcoId] FOREIGN KEY([pgcId])
REFERENCES [ParametrizacionGestionCobro] ([pgcId])
GO
ALTER TABLE [LineaCobro] CHECK CONSTRAINT [FK_LineaCobro_lcoId]
GO
ALTER TABLE [LineDefinition]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinition_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinition] ([id])
GO
ALTER TABLE [LineDefinition] CHECK CONSTRAINT [FK_LineDefinition_fileDefinition_id]
GO
ALTER TABLE [LineDefinition]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinition_lineCatalog_id] FOREIGN KEY([lineCatalog_id])
REFERENCES [LineCatalog] ([id])
GO
ALTER TABLE [LineDefinition] CHECK CONSTRAINT [FK_LineDefinition_lineCatalog_id]
GO
ALTER TABLE [LineDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinitionLoad_fileDefinition_id] FOREIGN KEY([fileDefinition_id])
REFERENCES [FileDefinitionLoad] ([id])
GO
ALTER TABLE [LineDefinitionLoad] CHECK CONSTRAINT [FK_LineDefinitionLoad_fileDefinition_id]
GO
ALTER TABLE [LineDefinitionLoad]  WITH CHECK ADD  CONSTRAINT [FK_LineDefinitionLoad_lineLoadCatalog_id] FOREIGN KEY([lineLoadCatalog_id])
REFERENCES [LineLoadCatalog] ([id])
GO
ALTER TABLE [LineDefinitionLoad] CHECK CONSTRAINT [FK_LineDefinitionLoad_lineLoadCatalog_id]
GO
ALTER TABLE [ListaEspecialRevision]  WITH CHECK ADD  CONSTRAINT [FK_ListaEspecialRevision_lerCajaCompensacion] FOREIGN KEY([lerCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [ListaEspecialRevision] CHECK CONSTRAINT [FK_ListaEspecialRevision_lerCajaCompensacion]
GO
ALTER TABLE [MedioCheque]  WITH CHECK ADD  CONSTRAINT [FK_MedioCheque_mecId] FOREIGN KEY([mdpId])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [MedioCheque] CHECK CONSTRAINT [FK_MedioCheque_mecId]
GO
ALTER TABLE [MedioConsignacion]  WITH CHECK ADD  CONSTRAINT [FK_MedioConsignacion_mcoBanco] FOREIGN KEY([mcoBanco])
REFERENCES [Banco] ([banId])
GO
ALTER TABLE [MedioConsignacion] CHECK CONSTRAINT [FK_MedioConsignacion_mcoBanco]
GO
ALTER TABLE [MedioConsignacion]  WITH CHECK ADD  CONSTRAINT [FK_MedioConsignacion_mcoId] FOREIGN KEY([mdpId])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [MedioConsignacion] CHECK CONSTRAINT [FK_MedioConsignacion_mcoId]
GO
ALTER TABLE [MedioEfectivo]  WITH CHECK ADD  CONSTRAINT [FK_MedioEfectivo_mefId] FOREIGN KEY([mdpId])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [MedioEfectivo] CHECK CONSTRAINT [FK_MedioEfectivo_mefId]
GO
ALTER TABLE [MedioEfectivo]  WITH CHECK ADD  CONSTRAINT [FK_MedioEfectivo_mefSedeCajaCompensacion] FOREIGN KEY([mefSedeCajaCompensacion])
REFERENCES [SedeCajaCompensacion] ([sccfId])
GO
ALTER TABLE [MedioEfectivo] CHECK CONSTRAINT [FK_MedioEfectivo_mefSedeCajaCompensacion]
GO
ALTER TABLE [MedioEfectivo]  WITH CHECK ADD  CONSTRAINT [FK_MedioEfectivo_mefSitioPago] FOREIGN KEY([mefSitioPago])
REFERENCES [SitioPago] ([sipId])
GO
ALTER TABLE [MedioEfectivo] CHECK CONSTRAINT [FK_MedioEfectivo_mefSitioPago]
GO
ALTER TABLE [MedioPagoPersona]  WITH CHECK ADD  CONSTRAINT [FK_MedioPagoPersona_mppMedioPago] FOREIGN KEY([mppMedioPago])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [MedioPagoPersona] CHECK CONSTRAINT [FK_MedioPagoPersona_mppMedioPago]
GO
ALTER TABLE [MedioPagoPersona]  WITH CHECK ADD  CONSTRAINT [FK_MedioPagoPersona_mppPersona] FOREIGN KEY([mppPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [MedioPagoPersona] CHECK CONSTRAINT [FK_MedioPagoPersona_mppPersona]
GO
ALTER TABLE [MedioPagoProyectoVivienda]  WITH CHECK ADD  CONSTRAINT [FK_MedioPagoProyectoVivienda_mprMedioDePago] FOREIGN KEY([mprMedioDePago])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [MedioPagoProyectoVivienda] CHECK CONSTRAINT [FK_MedioPagoProyectoVivienda_mprMedioDePago]
GO
ALTER TABLE [MedioPagoProyectoVivienda]  WITH CHECK ADD  CONSTRAINT [FK_MedioPagoProyectoVivienda_mprProyectoSolucionVivienda] FOREIGN KEY([mprProyectoSolucionVivienda])
REFERENCES [ProyectoSolucionVivienda] ([psvId])
GO
ALTER TABLE [MedioPagoProyectoVivienda] CHECK CONSTRAINT [FK_MedioPagoProyectoVivienda_mprProyectoSolucionVivienda]
GO
ALTER TABLE [MediosPagoCCF]  WITH CHECK ADD  CONSTRAINT [FK_MediosPagoCCF_mpcCajaCompensacion] FOREIGN KEY([mpcCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [MediosPagoCCF] CHECK CONSTRAINT [FK_MediosPagoCCF_mpcCajaCompensacion]
GO
ALTER TABLE [MedioTarjeta]  WITH CHECK ADD  CONSTRAINT [FK_MedioTarjeta_mtrId] FOREIGN KEY([mdpId])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [MedioTarjeta] CHECK CONSTRAINT [FK_MedioTarjeta_mtrId]
GO
ALTER TABLE [MedioTransferencia]  WITH CHECK ADD  CONSTRAINT [FK_MedioTransferencia_metBanco] FOREIGN KEY([metBanco])
REFERENCES [Banco] ([banId])
GO
ALTER TABLE [MedioTransferencia] CHECK CONSTRAINT [FK_MedioTransferencia_metBanco]
GO
ALTER TABLE [MedioTransferencia]  WITH CHECK ADD  CONSTRAINT [FK_MedioTransferencia_metId] FOREIGN KEY([mdpId])
REFERENCES [MedioDePago] ([mdpId])
GO
ALTER TABLE [MedioTransferencia] CHECK CONSTRAINT [FK_MedioTransferencia_metId]
GO
ALTER TABLE [MotivoNoGestionCobro]  WITH CHECK ADD  CONSTRAINT [FK_MotivoNoGestionCobro_mgcCartera] FOREIGN KEY([mgcCartera])
REFERENCES [Cartera] ([carId])
GO
ALTER TABLE [MotivoNoGestionCobro] CHECK CONSTRAINT [FK_MotivoNoGestionCobro_mgcCartera]
GO
ALTER TABLE [MovimientoAjusteAporte]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoCorregido] FOREIGN KEY([maaAporteDetalladoCorregido])
REFERENCES [AporteDetallado] ([apdId])
GO
ALTER TABLE [MovimientoAjusteAporte] CHECK CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoCorregido]
GO
ALTER TABLE [MovimientoAjusteAporte]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoOriginal] FOREIGN KEY([maaAporteDetalladoOriginal])
REFERENCES [AporteDetallado] ([apdId])
GO
ALTER TABLE [MovimientoAjusteAporte] CHECK CONSTRAINT [FK_MovimientoAjusteAporte_maaAporteDetalladoOriginal]
GO
ALTER TABLE [MovimientoAporte]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAporte_moaAporteDetallado] FOREIGN KEY([moaAporteDetallado])
REFERENCES [AporteDetallado] ([apdId])
GO
ALTER TABLE [MovimientoAporte] CHECK CONSTRAINT [FK_MovimientoAporte_moaAporteDetallado]
GO
ALTER TABLE [MovimientoAporte]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoAporte_moaAporteGeneral] FOREIGN KEY([moaAporteGeneral])
REFERENCES [AporteGeneral] ([apgId])
GO
ALTER TABLE [MovimientoAporte] CHECK CONSTRAINT [FK_MovimientoAporte_moaAporteGeneral]
GO
ALTER TABLE [Municipio]  WITH CHECK ADD  CONSTRAINT [FK_Municipio_munDepartamento] FOREIGN KEY([munDepartamento])
REFERENCES [Departamento] ([depId])
GO
ALTER TABLE [Municipio] CHECK CONSTRAINT [FK_Municipio_munDepartamento]
GO
ALTER TABLE [NotificacionDestinatario]  WITH CHECK ADD  CONSTRAINT [FK_NotDestinatario_nodNotEnviada] FOREIGN KEY([nodNotEnviada])
REFERENCES [NotificacionEnviada] ([noeId])
GO
ALTER TABLE [NotificacionDestinatario] CHECK CONSTRAINT [FK_NotDestinatario_nodNotEnviada]
GO
ALTER TABLE [NotificacionPersonal]  WITH CHECK ADD  CONSTRAINT [FK_NotificacionPersonal_ntpPersona] FOREIGN KEY([ntpPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [NotificacionPersonal] CHECK CONSTRAINT [FK_NotificacionPersonal_ntpPersona]
GO
ALTER TABLE [NotificacionPersonalDocumento]  WITH CHECK ADD  CONSTRAINT [FK_NotificacionPersonalDocumento_ntdDocumentoSoporte] FOREIGN KEY([ntdDocumentoSoporte])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [NotificacionPersonalDocumento] CHECK CONSTRAINT [FK_NotificacionPersonalDocumento_ntdDocumentoSoporte]
GO
ALTER TABLE [NotificacionPersonalDocumento]  WITH CHECK ADD  CONSTRAINT [FK_NotificacionPersonalDocumento_ntdNotificacionPersonal] FOREIGN KEY([ntdNotificacionPersonal])
REFERENCES [NotificacionPersonal] ([ntpId])
GO
ALTER TABLE [NotificacionPersonalDocumento] CHECK CONSTRAINT [FK_NotificacionPersonalDocumento_ntdNotificacionPersonal]
GO
ALTER TABLE [NovedadDetalle]  WITH CHECK ADD  CONSTRAINT [FK_NovedadDetalle_nopSolicitudNovedad] FOREIGN KEY([nopSolicitudNovedad])
REFERENCES [SolicitudNovedad] ([snoId])
GO
ALTER TABLE [NovedadDetalle] CHECK CONSTRAINT [FK_NovedadDetalle_nopSolicitudNovedad]
GO
ALTER TABLE [Oferente]  WITH CHECK ADD  CONSTRAINT [FK_Oferente_ofeBanco] FOREIGN KEY([ofeBanco])
REFERENCES [Banco] ([banId])
GO
ALTER TABLE [Oferente] CHECK CONSTRAINT [FK_Oferente_ofeBanco]
GO
ALTER TABLE [Oferente]  WITH CHECK ADD  CONSTRAINT [FK_Oferente_ofeEmpresa] FOREIGN KEY([ofeEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [Oferente] CHECK CONSTRAINT [FK_Oferente_ofeEmpresa]
GO
ALTER TABLE [Oferente]  WITH CHECK ADD  CONSTRAINT [FK_Oferente_ofePersona] FOREIGN KEY([ofePersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [Oferente] CHECK CONSTRAINT [FK_Oferente_ofePersona]
GO
ALTER TABLE [OperadorInformacionCcf]  WITH CHECK ADD  CONSTRAINT [FK_OperadorInformacionCcf_oicCajaCompensacion] FOREIGN KEY([oicCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [OperadorInformacionCcf] CHECK CONSTRAINT [FK_OperadorInformacionCcf_oicCajaCompensacion]
GO
ALTER TABLE [OperadorInformacionCcf]  WITH CHECK ADD  CONSTRAINT [FK_OperadorInformacionCcf_oicOperadorInformacion] FOREIGN KEY([oicOperadorInformacion])
REFERENCES [OperadorInformacion] ([oinId])
GO
ALTER TABLE [OperadorInformacionCcf] CHECK CONSTRAINT [FK_OperadorInformacionCcf_oicOperadorInformacion]
GO
ALTER TABLE [PagoPeriodoConvenio]  WITH CHECK ADD  CONSTRAINT [FK_PagoPeridoConvenio_ppcConvenioPago] FOREIGN KEY([ppcConvenioPago])
REFERENCES [ConvenioPago] ([copId])
GO
ALTER TABLE [PagoPeriodoConvenio] CHECK CONSTRAINT [FK_PagoPeridoConvenio_ppcConvenioPago]
GO
ALTER TABLE [ParametrizacionCriterioGestionCobro]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionCriterioGestionCobro_pacId] FOREIGN KEY([pacId])
REFERENCES [ParametrizacionCartera] ([pacId])
GO
ALTER TABLE [ParametrizacionCriterioGestionCobro] CHECK CONSTRAINT [FK_ParametrizacionCriterioGestionCobro_pacId]
GO
ALTER TABLE [ParametrizacionFiscalizacion]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionFiscalizacion_pafId] FOREIGN KEY([pacId])
REFERENCES [ParametrizacionCartera] ([pacId])
GO
ALTER TABLE [ParametrizacionFiscalizacion] CHECK CONSTRAINT [FK_ParametrizacionFiscalizacion_pafId]
GO
ALTER TABLE [ParametrizacionMetodoAsignacion]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion] FOREIGN KEY([pmaSedeCajaCompensacion])
REFERENCES [SedeCajaCompensacion] ([sccfId])
GO
ALTER TABLE [ParametrizacionMetodoAsignacion] CHECK CONSTRAINT [FK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion]
GO
ALTER TABLE [ParametrizacionPreventiva]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionPreventiva_pacId] FOREIGN KEY([pacId])
REFERENCES [ParametrizacionCartera] ([pacId])
GO
ALTER TABLE [ParametrizacionPreventiva] CHECK CONSTRAINT [FK_ParametrizacionPreventiva_pacId]
GO
ALTER TABLE [ParametrizacionSubsidioAjuste]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionSubsidioAjuste_psaPeriodoLiquidacion] FOREIGN KEY([psaPeriodoLiquidacion])
REFERENCES [PeriodoLiquidacion] ([pelId])
GO
ALTER TABLE [ParametrizacionSubsidioAjuste] CHECK CONSTRAINT [FK_ParametrizacionSubsidioAjuste_psaPeriodoLiquidacion]
GO
ALTER TABLE [ParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ParamValue_parameter_id] FOREIGN KEY([parameter_id])
REFERENCES [parameter] ([id])
GO
ALTER TABLE [ParamValue] CHECK CONSTRAINT [FK_ParamValue_parameter_id]
GO
ALTER TABLE [PeriodoBeneficio]  WITH CHECK ADD  CONSTRAINT [FK_PeriodoBeneficio_pbeBeneficio] FOREIGN KEY([pbeBeneficio])
REFERENCES [Beneficio] ([befId])
GO
ALTER TABLE [PeriodoBeneficio] CHECK CONSTRAINT [FK_PeriodoBeneficio_pbeBeneficio]
GO
ALTER TABLE [PeriodoExclusionMora]  WITH CHECK ADD  CONSTRAINT [FK_PeriodoExclusionMora_pemExclusionCartera] FOREIGN KEY([pemExclusionCartera])
REFERENCES [ExclusionCartera] ([excId])
GO
ALTER TABLE [PeriodoExclusionMora] CHECK CONSTRAINT [FK_PeriodoExclusionMora_pemExclusionCartera]
GO
ALTER TABLE [PeriodoLiquidacion]  WITH CHECK ADD  CONSTRAINT [FK_PeriodoLiquidacion_pelPeriodo] FOREIGN KEY([pelPeriodo])
REFERENCES [Periodo] ([priId])
GO
ALTER TABLE [PeriodoLiquidacion] CHECK CONSTRAINT [FK_PeriodoLiquidacion_pelPeriodo]
GO
ALTER TABLE [PeriodoLiquidacion]  WITH CHECK ADD  CONSTRAINT [FK_PeriodoLiquidacion_pelSolicitudLiquidacionSubsidio] FOREIGN KEY([pelSolicitudLiquidacionSubsidio])
REFERENCES [SolicitudLiquidacionSubsidio] ([slsId])
GO
ALTER TABLE [PeriodoLiquidacion] CHECK CONSTRAINT [FK_PeriodoLiquidacion_pelSolicitudLiquidacionSubsidio]
GO
ALTER TABLE [Persona]  WITH CHECK ADD  CONSTRAINT [FK_Persona_perUbicacionPrincipal] FOREIGN KEY([perUbicacionPrincipal])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [Persona] CHECK CONSTRAINT [FK_Persona_perUbicacionPrincipal]
GO
ALTER TABLE [PersonaDetalle]  WITH CHECK ADD  CONSTRAINT [FK_PersonaDetalle_pedGradoAcademico] FOREIGN KEY([pedGradoAcademico])
REFERENCES [GradoAcademico] ([graId])
GO
ALTER TABLE [PersonaDetalle] CHECK CONSTRAINT [FK_PersonaDetalle_pedGradoAcademico]
GO
ALTER TABLE [PersonaDetalle]  WITH CHECK ADD  CONSTRAINT [FK_PersonaDetalle_pedOcupacionProfesion] FOREIGN KEY([pedOcupacionProfesion])
REFERENCES [OcupacionProfesion] ([ocuId])
GO
ALTER TABLE [PersonaDetalle] CHECK CONSTRAINT [FK_PersonaDetalle_pedOcupacionProfesion]
GO
ALTER TABLE [PersonaDetalle]  WITH CHECK ADD  CONSTRAINT [FK_PersonaDetalle_pedPersona] FOREIGN KEY([pedPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [PersonaDetalle] CHECK CONSTRAINT [FK_PersonaDetalle_pedPersona]
GO
ALTER TABLE [PersonaLiquidacionEspecifica]  WITH CHECK ADD  CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleAfiliadoPrincipal] FOREIGN KEY([pleAfiliadoPrincipal])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [PersonaLiquidacionEspecifica] CHECK CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleAfiliadoPrincipal]
GO
ALTER TABLE [PersonaLiquidacionEspecifica]  WITH CHECK ADD  CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleBeneficiarioDetalle] FOREIGN KEY([pleBeneficiarioDetalle])
REFERENCES [BeneficiarioDetalle] ([bedId])
GO
ALTER TABLE [PersonaLiquidacionEspecifica] CHECK CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleBeneficiarioDetalle]
GO
ALTER TABLE [PersonaLiquidacionEspecifica]  WITH CHECK ADD  CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleEmpleador] FOREIGN KEY([pleEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [PersonaLiquidacionEspecifica] CHECK CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleEmpleador]
GO
ALTER TABLE [PersonaLiquidacionEspecifica]  WITH CHECK ADD  CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleGrupoFamiliar] FOREIGN KEY([pleGrupoFamiliar])
REFERENCES [GrupoFamiliar] ([grfId])
GO
ALTER TABLE [PersonaLiquidacionEspecifica] CHECK CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleGrupoFamiliar]
GO
ALTER TABLE [PersonaLiquidacionEspecifica]  WITH CHECK ADD  CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleSolicitudLiquidacionSubsidio] FOREIGN KEY([pleSolicitudLiquidacionSubsidio])
REFERENCES [SolicitudLiquidacionSubsidio] ([slsId])
GO
ALTER TABLE [PersonaLiquidacionEspecifica] CHECK CONSTRAINT [FK_PersonaLiquidacionEspecifica_pleSolicitudLiquidacionSubsidio]
GO
ALTER TABLE [PostulacionFOVIS]  WITH CHECK ADD  CONSTRAINT [FK_PostulacionFOVIS_pofCicloAsignacion] FOREIGN KEY([pofCicloAsignacion])
REFERENCES [CicloAsignacion] ([ciaId])
GO
ALTER TABLE [PostulacionFOVIS] CHECK CONSTRAINT [FK_PostulacionFOVIS_pofCicloAsignacion]
GO
ALTER TABLE [PostulacionFOVIS]  WITH CHECK ADD  CONSTRAINT [FK_PostulacionFOVIS_pofJefeHogar] FOREIGN KEY([pofJefeHogar])
REFERENCES [JefeHogar] ([jehId])
GO
ALTER TABLE [PostulacionFOVIS] CHECK CONSTRAINT [FK_PostulacionFOVIS_pofJefeHogar]
GO
ALTER TABLE [PostulacionFOVIS]  WITH CHECK ADD  CONSTRAINT [FK_PostulacionFOVIS_pofProyectoSolucionVivienda] FOREIGN KEY([pofProyectoSolucionVivienda])
REFERENCES [ProyectoSolucionVivienda] ([psvId])
GO
ALTER TABLE [PostulacionFOVIS] CHECK CONSTRAINT [FK_PostulacionFOVIS_pofProyectoSolucionVivienda]
GO
ALTER TABLE [PostulacionFOVIS]  WITH CHECK ADD  CONSTRAINT [FK_PostulacionFOVIS_pofSolicitudAsignacion] FOREIGN KEY([pofSolicitudAsignacion])
REFERENCES [SolicitudAsignacion] ([safId])
GO
ALTER TABLE [PostulacionFOVIS] CHECK CONSTRAINT [FK_PostulacionFOVIS_pofSolicitudAsignacion]
GO
ALTER TABLE [PrioridadDestinatario]  WITH CHECK ADD  CONSTRAINT [FK_PrioridadDestinatario_prdDestinatarioComunicado] FOREIGN KEY([prdDestinatarioComunicado])
REFERENCES [DestinatarioComunicado] ([dcoId])
GO
ALTER TABLE [PrioridadDestinatario] CHECK CONSTRAINT [FK_PrioridadDestinatario_prdDestinatarioComunicado]
GO
ALTER TABLE [PrioridadDestinatario]  WITH CHECK ADD  CONSTRAINT [FK_PrioridadDestinatario_prdGrupoPrioridad] FOREIGN KEY([prdGrupoPrioridad])
REFERENCES [GrupoPrioridad] ([gprId])
GO
ALTER TABLE [PrioridadDestinatario] CHECK CONSTRAINT [FK_PrioridadDestinatario_prdGrupoPrioridad]
GO
ALTER TABLE [ProcessorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorDefinition_processorCatalog_id] FOREIGN KEY([processorCatalog_id])
REFERENCES [ProcessorCatalog] ([id])
GO
ALTER TABLE [ProcessorDefinition] CHECK CONSTRAINT [FK_ProcessorDefinition_processorCatalog_id]
GO
ALTER TABLE [ProcessorParameter]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParameter_processorCatalog_id] FOREIGN KEY([processorCatalog_id])
REFERENCES [ProcessorCatalog] ([id])
GO
ALTER TABLE [ProcessorParameter] CHECK CONSTRAINT [FK_ProcessorParameter_processorCatalog_id]
GO
ALTER TABLE [ProcessorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParamValue_processorDefinition_id] FOREIGN KEY([processorDefinition_id])
REFERENCES [ProcessorDefinition] ([id])
GO
ALTER TABLE [ProcessorParamValue] CHECK CONSTRAINT [FK_ProcessorParamValue_processorDefinition_id]
GO
ALTER TABLE [ProcessorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ProcessorParamValue_processorParameter_id] FOREIGN KEY([processorParameter_id])
REFERENCES [ProcessorParameter] ([id])
GO
ALTER TABLE [ProcessorParamValue] CHECK CONSTRAINT [FK_ProcessorParamValue_processorParameter_id]
GO
ALTER TABLE [ProductoNoConforme]  WITH CHECK ADD  CONSTRAINT [FK_ProductoNoConforme_pncBeneficiario] FOREIGN KEY([pncBeneficiario])
REFERENCES [Beneficiario] ([benId])
GO
ALTER TABLE [ProductoNoConforme] CHECK CONSTRAINT [FK_ProductoNoConforme_pncBeneficiario]
GO
ALTER TABLE [ProductoNoConforme]  WITH CHECK ADD  CONSTRAINT [FK_ProductoNoConforme_pncSolicitud] FOREIGN KEY([pncSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [ProductoNoConforme] CHECK CONSTRAINT [FK_ProductoNoConforme_pncSolicitud]
GO
ALTER TABLE [ProyectoSolucionVivienda]  WITH CHECK ADD  CONSTRAINT [FK_ProyectoSolucionVivienda_psvOferente] FOREIGN KEY([psvOferente])
REFERENCES [Oferente] ([ofeId])
GO
ALTER TABLE [ProyectoSolucionVivienda] CHECK CONSTRAINT [FK_ProyectoSolucionVivienda_psvOferente]
GO
ALTER TABLE [ProyectoSolucionVivienda]  WITH CHECK ADD  CONSTRAINT [FK_ProyectoSolucionVivienda_psvUbicacionProyecto] FOREIGN KEY([psvUbicacionProyecto])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [ProyectoSolucionVivienda] CHECK CONSTRAINT [FK_ProyectoSolucionVivienda_psvUbicacionProyecto]
GO
ALTER TABLE [ProyectoSolucionVivienda]  WITH CHECK ADD  CONSTRAINT [FK_ProyectoSolucionVivienda_psvUbicacionVivienda] FOREIGN KEY([psvUbicacionVivienda])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [ProyectoSolucionVivienda] CHECK CONSTRAINT [FK_ProyectoSolucionVivienda_psvUbicacionVivienda]
GO
ALTER TABLE [RecursoComplementario]  WITH CHECK ADD  CONSTRAINT [FK_RecursoComplementario_recPostulacionFOVIS] FOREIGN KEY([recPostulacionFOVIS])
REFERENCES [PostulacionFOVIS] ([pofId])
GO
ALTER TABLE [RecursoComplementario] CHECK CONSTRAINT [FK_RecursoComplementario_recPostulacionFOVIS]
GO
ALTER TABLE [RegistroArchivoConsumosAnibol]  WITH CHECK ADD  CONSTRAINT [FK_RegistroArchivoConsumosAnibol_racArchivoConsumosAnibol] FOREIGN KEY([racArchivoConsumosAnibol])
REFERENCES [ArchivoConsumosAnibol] ([acnId])
GO
ALTER TABLE [RegistroArchivoConsumosAnibol] CHECK CONSTRAINT [FK_RegistroArchivoConsumosAnibol_racArchivoConsumosAnibol]
GO
ALTER TABLE [RegistroArchivoConsumosAnibol]  WITH CHECK ADD  CONSTRAINT [FK_RegistroArchivoConsumosAnibol_racCuentaAdministradorSubsidio] FOREIGN KEY([racCuentaAdministradorSubsidio])
REFERENCES [CuentaAdministradorSubsidio] ([casId])
GO
ALTER TABLE [RegistroArchivoConsumosAnibol] CHECK CONSTRAINT [FK_RegistroArchivoConsumosAnibol_racCuentaAdministradorSubsidio]
GO
ALTER TABLE [RegistroArchivoRetiroTerceroPagador]  WITH CHECK ADD  CONSTRAINT [FK_RegistroArchivoRetiroTerceroPagador_rarArchivoRetiroTerceroPagador] FOREIGN KEY([rarArchivoRetiroTerceroPagador])
REFERENCES [ArchivoRetiroTerceroPagador] ([arrId])
GO
ALTER TABLE [RegistroArchivoRetiroTerceroPagador] CHECK CONSTRAINT [FK_RegistroArchivoRetiroTerceroPagador_rarArchivoRetiroTerceroPagador]
GO
ALTER TABLE [RegistroEstadoAporte]  WITH CHECK ADD  CONSTRAINT [FK_RegistroEstadoAporte_reaComunicado] FOREIGN KEY([reaComunicado])
REFERENCES [Comunicado] ([comId])
GO
ALTER TABLE [RegistroEstadoAporte] CHECK CONSTRAINT [FK_RegistroEstadoAporte_reaComunicado]
GO
ALTER TABLE [RegistroEstadoAporte]  WITH CHECK ADD  CONSTRAINT [FK_RegistroEstadoAporte_reaSolicitud] FOREIGN KEY([reaSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [RegistroEstadoAporte] CHECK CONSTRAINT [FK_RegistroEstadoAporte_reaSolicitud]
GO
ALTER TABLE [RegistroNovedadFutura]  WITH CHECK ADD  CONSTRAINT [FK_RegistroNovedadFutura_rnfEmpleador] FOREIGN KEY([rnfEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [RegistroNovedadFutura] CHECK CONSTRAINT [FK_RegistroNovedadFutura_rnfEmpleador]
GO
ALTER TABLE [RegistroNovedadFutura]  WITH CHECK ADD  CONSTRAINT [FK_RegistroNovedadFutura_rnfPersona] FOREIGN KEY([rnfPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [RegistroNovedadFutura] CHECK CONSTRAINT [FK_RegistroNovedadFutura_rnfPersona]
GO
ALTER TABLE [RegistroOperacionTransaccionSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_RegistroOperacionTransaccionSubsidio_rotAdministradorSubsidio] FOREIGN KEY([rotAdministradorSubsidio])
REFERENCES [AdministradorSubsidio] ([asuId])
GO
ALTER TABLE [RegistroOperacionTransaccionSubsidio] CHECK CONSTRAINT [FK_RegistroOperacionTransaccionSubsidio_rotAdministradorSubsidio]
GO
ALTER TABLE [RegistroPersonaInconsistente]  WITH CHECK ADD  CONSTRAINT [FK_RegistroPersonaInconsistente_rpiCargueMultipleSupervivencia] FOREIGN KEY([rpiCargueMultipleSupervivencia])
REFERENCES [CargueMultipleSupervivencia] ([cmsId])
GO
ALTER TABLE [RegistroPersonaInconsistente] CHECK CONSTRAINT [FK_RegistroPersonaInconsistente_rpiCargueMultipleSupervivencia]
GO
ALTER TABLE [RegistroPersonaInconsistente]  WITH CHECK ADD  CONSTRAINT [FK_RegistroPersonaInconsistente_rpiPersona] FOREIGN KEY([rpiPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [RegistroPersonaInconsistente] CHECK CONSTRAINT [FK_RegistroPersonaInconsistente_rpiPersona]
GO
ALTER TABLE [RequisitoCajaClasificacion]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoCajaClasificacion_rtsCajaCompensacion] FOREIGN KEY([rtsCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [RequisitoCajaClasificacion] CHECK CONSTRAINT [FK_RequisitoCajaClasificacion_rtsCajaCompensacion]
GO
ALTER TABLE [RequisitoCajaClasificacion]  WITH CHECK ADD  CONSTRAINT [FK_RequisitoTipoSolicitante_rtsRequisito] FOREIGN KEY([rtsRequisito])
REFERENCES [Requisito] ([reqId])
GO
ALTER TABLE [RequisitoCajaClasificacion] CHECK CONSTRAINT [FK_RequisitoTipoSolicitante_rtsRequisito]
GO
ALTER TABLE [RetiroPersonaAutorizadaCobroSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_RetiroPersonaAutorizadaCobroSubsidio_rpaCuentaAdministradorSubsidio] FOREIGN KEY([rpaCuentaAdministradorSubsidio])
REFERENCES [CuentaAdministradorSubsidio] ([casId])
GO
ALTER TABLE [RetiroPersonaAutorizadaCobroSubsidio] CHECK CONSTRAINT [FK_RetiroPersonaAutorizadaCobroSubsidio_rpaCuentaAdministradorSubsidio]
GO
ALTER TABLE [RetiroPersonaAutorizadaCobroSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_RetiroPersonaAutorizadaCobroSubsidio_rpaDocumentoSoporte] FOREIGN KEY([rpaDocumentoSoporte])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [RetiroPersonaAutorizadaCobroSubsidio] CHECK CONSTRAINT [FK_RetiroPersonaAutorizadaCobroSubsidio_rpaDocumentoSoporte]
GO
ALTER TABLE [RetiroPersonaAutorizadaCobroSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_RetiroPersonaAutorizadaCobroSubsidio_rpaPersonaAutorizada] FOREIGN KEY([rpaPersonaAutorizada])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [RetiroPersonaAutorizadaCobroSubsidio] CHECK CONSTRAINT [FK_RetiroPersonaAutorizadaCobroSubsidio_rpaPersonaAutorizada]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaAfiliado] FOREIGN KEY([roaAfiliado])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaAfiliado]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaEmpleador] FOREIGN KEY([roaEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaEmpleador]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaPagadorAportes] FOREIGN KEY([roaPagadorAportes])
REFERENCES [EntidadPagadora] ([epaId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaPagadorAportes]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaPagadorPension] FOREIGN KEY([roaPagadorPension])
REFERENCES [AFP] ([afpId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaPagadorPension]
GO
ALTER TABLE [RolAfiliado]  WITH CHECK ADD  CONSTRAINT [FK_RolAfiliado_roaSucursalEmpleador] FOREIGN KEY([roaSucursalEmpleador])
REFERENCES [SucursalEmpresa] ([sueId])
GO
ALTER TABLE [RolAfiliado] CHECK CONSTRAINT [FK_RolAfiliado_roaSucursalEmpleador]
GO
ALTER TABLE [RolContactoEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_rceEmpleador] FOREIGN KEY([rceEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [RolContactoEmpleador] CHECK CONSTRAINT [FK_RolContactoEmpleador_rceEmpleador]
GO
ALTER TABLE [RolContactoEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_rcePersona] FOREIGN KEY([rcePersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [RolContactoEmpleador] CHECK CONSTRAINT [FK_RolContactoEmpleador_rcePersona]
GO
ALTER TABLE [RolContactoEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_RolContactoEmpleador_rceUbicacion] FOREIGN KEY([rceUbicacion])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [RolContactoEmpleador] CHECK CONSTRAINT [FK_RolContactoEmpleador_rceUbicacion]
GO
ALTER TABLE [SitioPago]  WITH CHECK ADD  CONSTRAINT [FK_SitioPago_sipInfraestructura] FOREIGN KEY([sipInfraestructura])
REFERENCES [Infraestructura] ([infId])
GO
ALTER TABLE [SitioPago] CHECK CONSTRAINT [FK_SitioPago_sipInfraestructura]
GO
ALTER TABLE [SocioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_semConyugue] FOREIGN KEY([semConyugue])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SocioEmpleador] CHECK CONSTRAINT [FK_SocioEmpleador_semConyugue]
GO
ALTER TABLE [SocioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_semEmpleador] FOREIGN KEY([semEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [SocioEmpleador] CHECK CONSTRAINT [FK_SocioEmpleador_semEmpleador]
GO
ALTER TABLE [SocioEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SocioEmpleador_semPersona] FOREIGN KEY([semPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SocioEmpleador] CHECK CONSTRAINT [FK_SocioEmpleador_semPersona]
GO
ALTER TABLE [Solicitud]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_solCajaCorrespondencia] FOREIGN KEY([solCajaCorrespondencia])
REFERENCES [CajaCorrespondencia] ([ccoId])
GO
ALTER TABLE [Solicitud] CHECK CONSTRAINT [FK_Solicitud_solCajaCorrespondencia]
GO
ALTER TABLE [Solicitud]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_solCargaAfiliacionMultipleEmpleador] FOREIGN KEY([solCargaAfiliacionMultipleEmpleador])
REFERENCES [CargueMultiple] ([camId])
GO
ALTER TABLE [Solicitud] CHECK CONSTRAINT [FK_Solicitud_solCargaAfiliacionMultipleEmpleador]
GO
ALTER TABLE [Solicitud]  WITH CHECK ADD  CONSTRAINT [FK_Solicitud_solDiferenciasCargueActualizacion] FOREIGN KEY([solDiferenciasCargueActualizacion])
REFERENCES [DiferenciasCargueActualizacion] ([dicId])
GO
ALTER TABLE [Solicitud] CHECK CONSTRAINT [FK_Solicitud_solDiferenciasCargueActualizacion]
GO
ALTER TABLE [SolicitudAfiliaciEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeEmpleador] FOREIGN KEY([saeEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [SolicitudAfiliaciEmpleador] CHECK CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeEmpleador]
GO
ALTER TABLE [SolicitudAfiliaciEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeSolicitudGlobal] FOREIGN KEY([saeSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudAfiliaciEmpleador] CHECK CONSTRAINT [FK_SolicitudAfiliaciEmpleador_saeSolicitudGlobal]
GO
ALTER TABLE [SolicitudAfiliacionPersona]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliacionPersona_sapRolAfiliado] FOREIGN KEY([sapRolAfiliado])
REFERENCES [RolAfiliado] ([roaId])
GO
ALTER TABLE [SolicitudAfiliacionPersona] CHECK CONSTRAINT [FK_SolicitudAfiliacionPersona_sapRolAfiliado]
GO
ALTER TABLE [SolicitudAfiliacionPersona]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAfiliacionPersona_sapSolicitudGlobal] FOREIGN KEY([sapSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudAfiliacionPersona] CHECK CONSTRAINT [FK_SolicitudAfiliacionPersona_sapSolicitudGlobal]
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_sanPersona] FOREIGN KEY([sanPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis] CHECK CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_sanPersona]
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_sanPostulacionFovis] FOREIGN KEY([sanPostulacionFovis])
REFERENCES [PostulacionFOVIS] ([pofId])
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis] CHECK CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_sanPostulacionFovis]
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_sanSolicitudGlobal] FOREIGN KEY([sanSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis] CHECK CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_sanSolicitudGlobal]
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_sanSolicitudNovedad] FOREIGN KEY([sanSolicitudNovedad])
REFERENCES [SolicitudNovedad] ([snoId])
GO
ALTER TABLE [SolicitudAnalisisNovedadFovis] CHECK CONSTRAINT [FK_SolicitudAnalisisNovedadFovis_sanSolicitudNovedad]
GO
ALTER TABLE [SolicitudAporte]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAporte_soaAporteGeneral] FOREIGN KEY([soaAporteGeneral])
REFERENCES [AporteGeneral] ([apgId])
GO
ALTER TABLE [SolicitudAporte] CHECK CONSTRAINT [FK_SolicitudAporte_soaAporteGeneral]
GO
ALTER TABLE [SolicitudAporte]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAporte_soaSolicitudGlobal] FOREIGN KEY([soaSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudAporte] CHECK CONSTRAINT [FK_SolicitudAporte_soaSolicitudGlobal]
GO
ALTER TABLE [SolicitudAsignacion]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAsignacion_safCicloAsignacion] FOREIGN KEY([safCicloAsignacion])
REFERENCES [CicloAsignacion] ([ciaId])
GO
ALTER TABLE [SolicitudAsignacion] CHECK CONSTRAINT [FK_SolicitudAsignacion_safCicloAsignacion]
GO
ALTER TABLE [SolicitudAsignacion]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAsignacion_safSolicitudGlobal] FOREIGN KEY([safSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudAsignacion] CHECK CONSTRAINT [FK_SolicitudAsignacion_safSolicitudGlobal]
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_soaRolAfiliado] FOREIGN KEY([soaRolAfiliado])
REFERENCES [RolAfiliado] ([roaId])
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora] CHECK CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_soaRolAfiliado]
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_soaSolicitudGlobal] FOREIGN KEY([soaSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudAsociacionPersonaEntidadPagadora] CHECK CONSTRAINT [FK_SolicitudAsociacionPersonaEntidadPagadora_soaSolicitudGlobal]
GO
ALTER TABLE [SolicitudCorreccionAporte]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudCorreccionAporte_scaAporteGeneral] FOREIGN KEY([scaAporteGeneral])
REFERENCES [AporteGeneral] ([apgId])
GO
ALTER TABLE [SolicitudCorreccionAporte] CHECK CONSTRAINT [FK_SolicitudCorreccionAporte_scaAporteGeneral]
GO
ALTER TABLE [SolicitudCorreccionAporte]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudCorreccionAporte_scaPersona] FOREIGN KEY([scaPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SolicitudCorreccionAporte] CHECK CONSTRAINT [FK_SolicitudCorreccionAporte_scaPersona]
GO
ALTER TABLE [SolicitudCorreccionAporte]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudCorreccionAporte_scaSolicitudGlobal] FOREIGN KEY([scaSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudCorreccionAporte] CHECK CONSTRAINT [FK_SolicitudCorreccionAporte_scaSolicitudGlobal]
GO
ALTER TABLE [SolicitudDesafiliacion]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudDesafiliacion_sodSolicitudGlobal] FOREIGN KEY([sodSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudDesafiliacion] CHECK CONSTRAINT [FK_SolicitudDesafiliacion_sodSolicitudGlobal]
GO
ALTER TABLE [SolicitudDevolucionAporte]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudDevolucionAporte_sdaDevolucionAporte] FOREIGN KEY([sdaDevolucionAporte])
REFERENCES [DevolucionAporte] ([dapId])
GO
ALTER TABLE [SolicitudDevolucionAporte] CHECK CONSTRAINT [FK_SolicitudDevolucionAporte_sdaDevolucionAporte]
GO
ALTER TABLE [SolicitudDevolucionAporte]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudDevolucionAporte_sdaPersona] FOREIGN KEY([sdaPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SolicitudDevolucionAporte] CHECK CONSTRAINT [FK_SolicitudDevolucionAporte_sdaPersona]
GO
ALTER TABLE [SolicitudDevolucionAporte]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudDevolucionAporte_sdaSolicitudGlobal] FOREIGN KEY([sdaSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudDevolucionAporte] CHECK CONSTRAINT [FK_SolicitudDevolucionAporte_sdaSolicitudGlobal]
GO
ALTER TABLE [SolicitudFiscalizacion]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudFiscalizacion_sfiCicloAportante] FOREIGN KEY([sfiCicloAportante])
REFERENCES [CicloAportante] ([capId])
GO
ALTER TABLE [SolicitudFiscalizacion] CHECK CONSTRAINT [FK_SolicitudFiscalizacion_sfiCicloAportante]
GO
ALTER TABLE [SolicitudFiscalizacion]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudFiscalizacion_sfiSolicitudGlobal] FOREIGN KEY([sfiSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudFiscalizacion] CHECK CONSTRAINT [FK_SolicitudFiscalizacion_sfiSolicitudGlobal]
GO
ALTER TABLE [SolicitudGestionCobroElectronico]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCobroElectronico_sgeSolicitud] FOREIGN KEY([sgeSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudGestionCobroElectronico] CHECK CONSTRAINT [FK_SolicitudGestionCobroElectronico_sgeSolicitud]
GO
ALTER TABLE [SolicitudGestionCobroFisico]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCobroFisico_sgfDocumentoSoporte] FOREIGN KEY([sgfDocumentoSoporte])
REFERENCES [DocumentoSoporte] ([dosId])
GO
ALTER TABLE [SolicitudGestionCobroFisico] CHECK CONSTRAINT [FK_SolicitudGestionCobroFisico_sgfDocumentoSoporte]
GO
ALTER TABLE [SolicitudGestionCobroFisico]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCobroFisico_sgfSolicitud] FOREIGN KEY([sgfSolicitud])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudGestionCobroFisico] CHECK CONSTRAINT [FK_SolicitudGestionCobroFisico_sgfSolicitud]
GO
ALTER TABLE [SolicitudGestionCobroManual]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCobroManual_scmCicloAportante] FOREIGN KEY([scmCicloAportante])
REFERENCES [CicloAportante] ([capId])
GO
ALTER TABLE [SolicitudGestionCobroManual] CHECK CONSTRAINT [FK_SolicitudGestionCobroManual_scmCicloAportante]
GO
ALTER TABLE [SolicitudGestionCobroManual]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCobroManual_scmSolicitudGlobal] FOREIGN KEY([scmSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudGestionCobroManual] CHECK CONSTRAINT [FK_SolicitudGestionCobroManual_scmSolicitudGlobal]
GO
ALTER TABLE [SolicitudGestionCruce]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudGestionCruce_sgcSolicitudPostulacion] FOREIGN KEY([sgcSolicitudPostulacion])
REFERENCES [SolicitudPostulacion] ([spoId])
GO
ALTER TABLE [SolicitudGestionCruce] CHECK CONSTRAINT [FK_SolicitudGestionCruce_sgcSolicitudPostulacion]
GO
ALTER TABLE [SolicitudLegalizacionDesembolso]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudLegalizacionDesembolso_sldLegalizacionDesembolso] FOREIGN KEY([sldLegalizacionDesembolso])
REFERENCES [LegalizacionDesembolso] ([lgdId])
GO
ALTER TABLE [SolicitudLegalizacionDesembolso] CHECK CONSTRAINT [FK_SolicitudLegalizacionDesembolso_sldLegalizacionDesembolso]
GO
ALTER TABLE [SolicitudLegalizacionDesembolso]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudLegalizacionDesembolso_sldPostulacionFOVIS] FOREIGN KEY([sldPostulacionFOVIS])
REFERENCES [PostulacionFOVIS] ([pofId])
GO
ALTER TABLE [SolicitudLegalizacionDesembolso] CHECK CONSTRAINT [FK_SolicitudLegalizacionDesembolso_sldPostulacionFOVIS]
GO
ALTER TABLE [SolicitudLegalizacionDesembolso]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudLegalizacionDesembolso_sldSolicitudGlobal] FOREIGN KEY([sldSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudLegalizacionDesembolso] CHECK CONSTRAINT [FK_SolicitudLegalizacionDesembolso_sldSolicitudGlobal]
GO
ALTER TABLE [SolicitudLiquidacionSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudLiquidacionSubsidio_plsSolicitudGlobal] FOREIGN KEY([slsSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudLiquidacionSubsidio] CHECK CONSTRAINT [FK_SolicitudLiquidacionSubsidio_plsSolicitudGlobal]
GO
ALTER TABLE [SolicitudNovedad]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedad_snoNovedad] FOREIGN KEY([snoNovedad])
REFERENCES [ParametrizacionNovedad] ([novId])
GO
ALTER TABLE [SolicitudNovedad] CHECK CONSTRAINT [FK_SolicitudNovedad_snoNovedad]
GO
ALTER TABLE [SolicitudNovedad]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedad_snoSolicitudGlobal] FOREIGN KEY([snoSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudNovedad] CHECK CONSTRAINT [FK_SolicitudNovedad_snoSolicitudGlobal]
GO
ALTER TABLE [SolicitudNovedadEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadEmpleador_sneIdEmpleador] FOREIGN KEY([sneIdEmpleador])
REFERENCES [Empleador] ([empId])
GO
ALTER TABLE [SolicitudNovedadEmpleador] CHECK CONSTRAINT [FK_SolicitudNovedadEmpleador_sneIdEmpleador]
GO
ALTER TABLE [SolicitudNovedadEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadEmpleador_sneIdSolicitudNovedad] FOREIGN KEY([sneIdSolicitudNovedad])
REFERENCES [SolicitudNovedad] ([snoId])
GO
ALTER TABLE [SolicitudNovedadEmpleador] CHECK CONSTRAINT [FK_SolicitudNovedadEmpleador_sneIdSolicitudNovedad]
GO
ALTER TABLE [SolicitudNovedadFovis]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadFovis_snfParametrizacionNovedad] FOREIGN KEY([snfParametrizacionNovedad])
REFERENCES [ParametrizacionNovedad] ([novId])
GO
ALTER TABLE [SolicitudNovedadFovis] CHECK CONSTRAINT [FK_SolicitudNovedadFovis_snfParametrizacionNovedad]
GO
ALTER TABLE [SolicitudNovedadFovis]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadFovis_snfSolicitudGlobal] FOREIGN KEY([snfSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudNovedadFovis] CHECK CONSTRAINT [FK_SolicitudNovedadFovis_snfSolicitudGlobal]
GO
ALTER TABLE [SolicitudNovedadPersona]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersona_snpBeneficiario] FOREIGN KEY([snpBeneficiario])
REFERENCES [Beneficiario] ([benId])
GO
ALTER TABLE [SolicitudNovedadPersona] CHECK CONSTRAINT [FK_SolicitudNovedadPersona_snpBeneficiario]
GO
ALTER TABLE [SolicitudNovedadPersona]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersona_snpPersona] FOREIGN KEY([snpPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SolicitudNovedadPersona] CHECK CONSTRAINT [FK_SolicitudNovedadPersona_snpPersona]
GO
ALTER TABLE [SolicitudNovedadPersona]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersona_snpRolAfiliado] FOREIGN KEY([snpRolAfiliado])
REFERENCES [RolAfiliado] ([roaId])
GO
ALTER TABLE [SolicitudNovedadPersona] CHECK CONSTRAINT [FK_SolicitudNovedadPersona_snpRolAfiliado]
GO
ALTER TABLE [SolicitudNovedadPersona]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersona_snpSolicitudNovedad] FOREIGN KEY([snpSolicitudNovedad])
REFERENCES [SolicitudNovedad] ([snoId])
GO
ALTER TABLE [SolicitudNovedadPersona] CHECK CONSTRAINT [FK_SolicitudNovedadPersona_snpSolicitudNovedad]
GO
ALTER TABLE [SolicitudNovedadPersonaFovis]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersonaFovis_spfPersona] FOREIGN KEY([spfPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SolicitudNovedadPersonaFovis] CHECK CONSTRAINT [FK_SolicitudNovedadPersonaFovis_spfPersona]
GO
ALTER TABLE [SolicitudNovedadPersonaFovis]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersonaFovis_spfPostulacionFovis] FOREIGN KEY([spfPostulacionFovis])
REFERENCES [PostulacionFOVIS] ([pofId])
GO
ALTER TABLE [SolicitudNovedadPersonaFovis] CHECK CONSTRAINT [FK_SolicitudNovedadPersonaFovis_spfPostulacionFovis]
GO
ALTER TABLE [SolicitudNovedadPersonaFovis]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPersonaFovis_spfSolicitudNovedadFovis] FOREIGN KEY([spfSolicitudNovedadFovis])
REFERENCES [SolicitudNovedadFovis] ([snfId])
GO
ALTER TABLE [SolicitudNovedadPersonaFovis] CHECK CONSTRAINT [FK_SolicitudNovedadPersonaFovis_spfSolicitudNovedadFovis]
GO
ALTER TABLE [SolicitudNovedadPila]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudNovedadPila_spiSolicitudNovedad] FOREIGN KEY([spiSolicitudNovedad])
REFERENCES [SolicitudNovedad] ([snoId])
GO
ALTER TABLE [SolicitudNovedadPila] CHECK CONSTRAINT [FK_SolicitudNovedadPila_spiSolicitudNovedad]
GO
ALTER TABLE [SolicitudPostulacion]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudPostulacion_spoPostulacionFOVIS] FOREIGN KEY([spoPostulacionFOVIS])
REFERENCES [PostulacionFOVIS] ([pofId])
GO
ALTER TABLE [SolicitudPostulacion] CHECK CONSTRAINT [FK_SolicitudPostulacion_spoPostulacionFOVIS]
GO
ALTER TABLE [SolicitudPostulacion]  WITH CHECK ADD  CONSTRAINT [FK_SolicitudPostulacion_spoSolicitudGlobal] FOREIGN KEY([spoSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudPostulacion] CHECK CONSTRAINT [FK_SolicitudPostulacion_spoSolicitudGlobal]
GO
ALTER TABLE [SolicitudPreventiva]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionPreventiva_sprPersona] FOREIGN KEY([sprPersona])
REFERENCES [Persona] ([perId])
GO
ALTER TABLE [SolicitudPreventiva] CHECK CONSTRAINT [FK_ParametrizacionPreventiva_sprPersona]
GO
ALTER TABLE [SolicitudPreventiva]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionPreventiva_sprSolicitudGlobal] FOREIGN KEY([sprSolicitudGlobal])
REFERENCES [Solicitud] ([solId])
GO
ALTER TABLE [SolicitudPreventiva] CHECK CONSTRAINT [FK_ParametrizacionPreventiva_sprSolicitudGlobal]
GO
ALTER TABLE [SucursalEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_sueCodigoCIIU] FOREIGN KEY([sueCodigoCIIU])
REFERENCES [CodigoCIIU] ([ciiId])
GO
ALTER TABLE [SucursalEmpresa] CHECK CONSTRAINT [FK_SucursalEmpresa_sueCodigoCIIU]
GO
ALTER TABLE [SucursalEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_sueEmpresa] FOREIGN KEY([sueEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [SucursalEmpresa] CHECK CONSTRAINT [FK_SucursalEmpresa_sueEmpresa]
GO
ALTER TABLE [SucursalEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_SucursalEmpresa_sueUbicacion] FOREIGN KEY([sueUbicacion])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [SucursalEmpresa] CHECK CONSTRAINT [FK_SucursalEmpresa_sueUbicacion]
GO
ALTER TABLE [SucursaRolContactEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursaRolContactEmpleador_srcRolContactoEmpleador] FOREIGN KEY([srcRolContactoEmpleador])
REFERENCES [RolContactoEmpleador] ([rceId])
GO
ALTER TABLE [SucursaRolContactEmpleador] CHECK CONSTRAINT [FK_SucursaRolContactEmpleador_srcRolContactoEmpleador]
GO
ALTER TABLE [SucursaRolContactEmpleador]  WITH CHECK ADD  CONSTRAINT [FK_SucursaRolContactEmpleador_srcSucursalEmpleador] FOREIGN KEY([srcSucursalEmpleador])
REFERENCES [SucursalEmpresa] ([sueId])
GO
ALTER TABLE [SucursaRolContactEmpleador] CHECK CONSTRAINT [FK_SucursaRolContactEmpleador_srcSucursalEmpleador]
GO
ALTER TABLE [Tarjeta]  WITH CHECK ADD  CONSTRAINT [FK_Tarjeta_afiPersona] FOREIGN KEY([afiPersona])
REFERENCES [Afiliado] ([afiId])
GO
ALTER TABLE [Tarjeta] CHECK CONSTRAINT [FK_Tarjeta_afiPersona]
GO
ALTER TABLE [TransaccionesFallidasSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_TransaccionesFallidasSubsidio_tfsCuentaAdministradorSubsidio] FOREIGN KEY([tfsCuentaAdministradorSubsidio])
REFERENCES [CuentaAdministradorSubsidio] ([casId])
GO
ALTER TABLE [TransaccionesFallidasSubsidio] CHECK CONSTRAINT [FK_TransaccionesFallidasSubsidio_tfsCuentaAdministradorSubsidio]
GO
ALTER TABLE [TransaccionFallidaOperacTransacSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_TransaccionFallidaOperacTransacSubsidio_tfoRegistroOperacionTransaccionSubsidio] FOREIGN KEY([tfoRegistroOperacionTransaccionSubsidio])
REFERENCES [RegistroOperacionTransaccionSubsidio] ([rotId])
GO
ALTER TABLE [TransaccionFallidaOperacTransacSubsidio] CHECK CONSTRAINT [FK_TransaccionFallidaOperacTransacSubsidio_tfoRegistroOperacionTransaccionSubsidio]
GO
ALTER TABLE [TransaccionFallidaOperacTransacSubsidio]  WITH CHECK ADD  CONSTRAINT [FK_TransaccionFallidaOperacTransacSubsidio_tfoTransaccionesFallidasSubsidio] FOREIGN KEY([tfoTransaccionesFallidasSubsidio])
REFERENCES [TransaccionesFallidasSubsidio] ([tfsId])
GO
ALTER TABLE [TransaccionFallidaOperacTransacSubsidio] CHECK CONSTRAINT [FK_TransaccionFallidaOperacTransacSubsidio_tfoTransaccionesFallidasSubsidio]
GO
ALTER TABLE [Ubicacion]  WITH CHECK ADD  CONSTRAINT [FK_Ubicacion_ubiMunicipio] FOREIGN KEY([ubiMunicipio])
REFERENCES [Municipio] ([munId])
GO
ALTER TABLE [Ubicacion] CHECK CONSTRAINT [FK_Ubicacion_ubiMunicipio]
GO
ALTER TABLE [UbicacionEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_ubeEmpresa] FOREIGN KEY([ubeEmpresa])
REFERENCES [Empresa] ([empId])
GO
ALTER TABLE [UbicacionEmpresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_ubeEmpresa]
GO
ALTER TABLE [UbicacionEmpresa]  WITH CHECK ADD  CONSTRAINT [FK_UbicacionEmpresa_ubeUbicacion] FOREIGN KEY([ubeUbicacion])
REFERENCES [Ubicacion] ([ubiId])
GO
ALTER TABLE [UbicacionEmpresa] CHECK CONSTRAINT [FK_UbicacionEmpresa_ubeUbicacion]
GO
ALTER TABLE [ValidatorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorDefinition_fieldDefinition_id] FOREIGN KEY([fieldDefinition_id])
REFERENCES [FieldDefinitionLoad] ([id])
GO
ALTER TABLE [ValidatorDefinition] CHECK CONSTRAINT [FK_ValidatorDefinition_fieldDefinition_id]
GO
ALTER TABLE [ValidatorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorDefinition_fileDefinitionLoad_id] FOREIGN KEY([fileDefinitionLoad_id])
REFERENCES [FileDefinitionLoad] ([id])
GO
ALTER TABLE [ValidatorDefinition] CHECK CONSTRAINT [FK_ValidatorDefinition_fileDefinitionLoad_id]
GO
ALTER TABLE [ValidatorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorDefinition_lineDefinition_id] FOREIGN KEY([lineDefinition_id])
REFERENCES [LineDefinitionLoad] ([id])
GO
ALTER TABLE [ValidatorDefinition] CHECK CONSTRAINT [FK_ValidatorDefinition_lineDefinition_id]
GO
ALTER TABLE [ValidatorDefinition]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorDefinition_validatorCatalog_id] FOREIGN KEY([validatorCatalog_id])
REFERENCES [ValidatorCatalog] ([id])
GO
ALTER TABLE [ValidatorDefinition] CHECK CONSTRAINT [FK_ValidatorDefinition_validatorCatalog_id]
GO
ALTER TABLE [ValidatorParameter]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorParameter_validatorCatalog_id] FOREIGN KEY([validatorCatalog_id])
REFERENCES [ValidatorCatalog] ([id])
GO
ALTER TABLE [ValidatorParameter] CHECK CONSTRAINT [FK_ValidatorParameter_validatorCatalog_id]
GO
ALTER TABLE [ValidatorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorParamValue_validatorDefinition_id] FOREIGN KEY([validatorDefinition_id])
REFERENCES [ValidatorDefinition] ([id])
GO
ALTER TABLE [ValidatorParamValue] CHECK CONSTRAINT [FK_ValidatorParamValue_validatorDefinition_id]
GO
ALTER TABLE [ValidatorParamValue]  WITH CHECK ADD  CONSTRAINT [FK_ValidatorParamValue_validatorParameter_id] FOREIGN KEY([validatorParameter_id])
REFERENCES [ValidatorParameter] ([id])
GO
ALTER TABLE [ValidatorParamValue] CHECK CONSTRAINT [FK_ValidatorParamValue_validatorParameter_id]
GO
ALTER TABLE [VariableComunicado]  WITH CHECK ADD  CONSTRAINT [FK_VariableComunicado_vcoPlantillaComunicado] FOREIGN KEY([vcoPlantillaComunicado])
REFERENCES [PlantillaComunicado] ([pcoId])
GO
ALTER TABLE [VariableComunicado] CHECK CONSTRAINT [FK_VariableComunicado_vcoPlantillaComunicado]
GO