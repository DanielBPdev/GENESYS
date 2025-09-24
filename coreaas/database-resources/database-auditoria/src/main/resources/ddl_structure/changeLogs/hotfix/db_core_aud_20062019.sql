--liquibase formatted sql

--changeset dsuesca:01
--comment:

CREATE NONCLUSTERED INDEX IX_Banco_REV
ON Banco_aud (REV) WITH (ONLINE = ON)
CREATE NONCLUSTERED INDEX IX_MedioDePago_REV
ON MedioDePago_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_MedioEfectivo_REV
ON MedioEfectivo_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_MedioTarjeta_REV
ON MedioTarjeta_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_MedioTransferencia_REV
ON MedioTransferencia_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_Persona_REV
ON Persona_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_Afiliado_REV
ON Afiliado_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_GrupoFamiliar_REV
ON GrupoFamiliar_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_CodigoCIIU_REV
ON CodigoCIIU_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_Empresa_REV
ON Empresa_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_SucursalEmpresa_REV
ON SucursalEmpresa_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_Empleador_REV
ON Empleador_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_RolAfiliado_REV
ON RolAfiliado_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_AdministradorSubsidio_REV
ON AdministradorSubsidio_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_AdminSubsidioGrupo_REV
ON AdminSubsidioGrupo_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_Solicitud_REV
ON Solicitud_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_ParametrizacionNovedad_REV
ON ParametrizacionNovedad_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_SolicitudNovedad_REV
ON SolicitudNovedad_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_SolicitudNovedadPersona_REV
ON SolicitudNovedadPersona_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_NovedadDetalle_REV
ON NovedadDetalle_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_AporteGeneral_REV
ON AporteGeneral_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_AporteDetallado_REV
ON AporteDetallado_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_SocioEmpleador_REV
ON SocioEmpleador_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_PersonaDetalle_REV
ON PersonaDetalle_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_BeneficiarioDetalle_REV
ON BeneficiarioDetalle_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_Beneficiario_REV
ON Beneficiario_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_BeneficioEmpleador_REV
ON BeneficioEmpleador_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_CondicionInvalidez_REV
ON CondicionInvalidez_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_SolicitudNovedadEmpleador_REV
ON SolicitudNovedadEmpleador_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_CertificadoEscolarBeneficiario_REV
ON CertificadoEscolarBeneficiario_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_CargueBloqueoCuotaMonetaria_REV
ON CargueBloqueoCuotaMonetaria_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_BloqueoBeneficiarioCuotaMonetaria_REV
ON BloqueoBeneficiarioCuotaMonetaria_aud (REV) WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_RevisionEntidad_reeRevision 
ON RevisionEntidad (reeRevision)WITH (ONLINE = ON);
CREATE NONCLUSTERED INDEX IX_Revision_reeRevision ON 
Revision(revTimeStamp)WITH (ONLINE = ON);