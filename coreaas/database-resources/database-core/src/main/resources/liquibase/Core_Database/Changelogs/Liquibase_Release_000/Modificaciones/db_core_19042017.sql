--liquibase formatted sql

--changeset atoro:01 
--comment: Actualizacion en la tabla ValidacionProceso
UPDATE ValidacionProceso SET vapObjetoValidacion='GENERAL' WHERE vapObjetoValidacion is null;

--changeset jzambrano:02
--comment: Cambio de FK a otra tabla
ALTER TABLE RegistroPersonaInconsistente DROP CONSTRAINT FK_RegistroPersonaInconsistente_rpiCargueMultiple;
EXEC sp_rename 'RegistroPersonaInconsistente.rpiCargueMultiple', 'rpiCargueMultipleSupervivencia', 'COLUMN';
ALTER TABLE RegistroPersonaInconsistente WITH CHECK ADD CONSTRAINT FK_RegistroPersonaInconsistente_rpiCargueMultipleSupervivencia FOREIGN KEY (rpiCargueMultipleSupervivencia) REFERENCES CargueMultipleSupervivencia (cmsId);

--changeset jzambrano:03
--comment: Actualizacion en la tabla LineDefinition
UPDATE LineDefinition SET REQUIRED = '0' WHERE ID= '3'

--changeset flopez:04
--comment: Eliminacion de la tabla SolicitudNovedadAfiliado
DROP TABLE SolicitudNovedadAfiliado

--changeset flopez:05
-- Creacion de la tabla SolicitudNovedadPersona
CREATE TABLE SolicitudNovedadPersona(
	snpId bigint IDENTITY(1,1) NOT NULL,
	snpPersona bigint NOT NULL,
	snpSolicitudNovedad bigint NOT NULL
CONSTRAINT PK_SolicitudNovedadPersona_snpId PRIMARY KEY (snpId)
);

ALTER TABLE SolicitudNovedadPersona WITH CHECK ADD CONSTRAINT FK_SolicitudNovedadPersona_snpPersona FOREIGN KEY (snpPersona) REFERENCES Persona (perId);
ALTER TABLE SolicitudNovedadPersona WITH CHECK ADD CONSTRAINT FK_SolicitudNovedadPersona_snpSolicitudNovedad FOREIGN KEY (snpSolicitudNovedad) REFERENCES SolicitudNovedad (snoId);

--changeset atoro:06
--comment: Actualizacion en la tabla Novedad
UPDATE Novedad SET novRutaCualificada ='com.asopagos.novedades.convertidores.empleador.CrearSucursalNovedad' WHERE novTipotransaccion='AGREGAR_SUCURSAL';
