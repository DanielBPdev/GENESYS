--liquibase formatted sql

--changeset jzambrano:01
--comment: Creacion de la tabla RegistroPersonaInconsistente
CREATE TABLE RegistroPersonaInconsistente(
	rpiId bigint IDENTITY(1,1) NOT NULL,
	rpiCargueMultiple bigint NOT NULL,
	rpiPersona bigint NOT NULL,
	rpiCanalContacto varchar(15) NULL,
	rpiFechaIngreso date NULL,
	rpiEstadoGestion varchar(20) NULL,
	rpiObservaciones varchar(255) NULL,
	rpiTipoInconsistencia varchar(42) NULL
CONSTRAINT PK_RegistroPersonaInconsistente_rpiId PRIMARY KEY (rpiId)
);

ALTER TABLE RegistroPersonaInconsistente WITH CHECK ADD CONSTRAINT FK_RegistroPersonaInconsistente_rpiPersona FOREIGN KEY (rpiPersona) REFERENCES Persona (perId);
ALTER TABLE RegistroPersonaInconsistente WITH CHECK ADD CONSTRAINT FK_RegistroPersonaInconsistente_rpiCargueMultiple FOREIGN KEY (rpiCargueMultiple) REFERENCES CargueMultiple (camId);

--changeset atoro:02
--comment: Cambio de nombre del pk en NovedadPila
ALTER TABLE NovedadPila DROP CONSTRAINT PK_CondicionInvalidez_nopId;
ALTER TABLE NovedadPila ADD CONSTRAINT PK_NovedadPila_nopId PRIMARY KEY (nopId); 

--changeset atoro:03
--comment: Cambio de nombre del pk en PersonaDetalle
ALTER TABLE PersonaDetalle DROP CONSTRAINT PK_Persona_pedId;
ALTER TABLE PersonaDetalle ADD CONSTRAINT PK_PersonaDetalle_pedId PRIMARY KEY (pedId); 

--changeset atoro:04
--comment: Se agrega nuevo campo a la tabla SolicitudNovedad
ALTER TABLE SolicitudNovedad ADD snoCargaMultiple bit NULL

