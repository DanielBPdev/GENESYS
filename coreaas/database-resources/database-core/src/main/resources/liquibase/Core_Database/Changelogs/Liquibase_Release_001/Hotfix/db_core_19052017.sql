--liquibase formatted sql

--changeset atoro:01
--comment: Se resuelve incidencia	
UPDATE ValidacionProceso SET vapValidacion='VALIDACION_ESTADO_AFILIACION_ACTIVO' WHERE vapbloque='NOVEDAD_TRASLADO_TRABAJADORES_ENTRE_SUCURSALES' AND vapValidacion='VALIDACION_ESTADO_AFILIACION';
UPDATE ValidacionProceso SET vapValidacion='VALIDACION_ESTADO_AFILIACION_ACTIVO' WHERE vapbloque='NOVEDAD_SUSTITUCION_PATRONAL' AND vapValidacion='VALIDACION_ESTADO_AFILIACION';
UPDATE ValidacionProceso SET vapValidacion='VALIDACION_ESTADO_AFILIACION_ACTIVO' WHERE vapbloque='NOVEDAD_DESAFILIACION' AND vapValidacion='VALIDACION_ESTADO_AFILIACION';

--changeset atoro:02
--comment: Se resuelve incidencia	
ALTER TABLE SolicitudNovedadPersona ADD snpRolAfiliado bigint NULL;
ALTER TABLE SolicitudNovedadPersona ADD snpBeneficiario bigint NULL;
ALTER TABLE SolicitudNovedadPersona ADD CONSTRAINT FK_SolicitudNovedadPersona_snpRolAfiliado FOREIGN KEY (snpRolAfiliado) REFERENCES RolAfiliado(roaId);
ALTER TABLE SolicitudNovedadPersona ADD CONSTRAINT FK_SolicitudNovedadPersona_snpBeneficiario FOREIGN KEY (snpBeneficiario) REFERENCES Beneficiario(benId);
