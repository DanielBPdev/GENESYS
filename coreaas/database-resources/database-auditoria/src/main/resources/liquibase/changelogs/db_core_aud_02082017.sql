--liquibase formatted sql

--changeset atoro:01
--comment: Se elimina la tabla RegistroPilaNovedad y se crea la tabla RegistroNovedadFutura
--Eliminacion de la tabla RegistroPilaNovedad
ALTER TABLE RegistroPilaNovedad_aud DROP CONSTRAINT FK_RegistroPilaNovedad_aud_REV;
DROP TABLE RegistroPilaNovedad_aud;

--Creacion de la tabla RegistroNovedadFutura_aud
CREATE TABLE RegistroNovedadFutura_aud(
	rnfId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	rnfFechaInicio date NULL,
	rnfFechaFin date NULL,
	rnfTipoTransaccion varchar (90) NULL,
	rnfCanalRecepcion varchar(30) NULL,
	rnfComentarios varchar(250) NULL,
	rnfPersona bigint NULL,
);

ALTER TABLE RegistroNovedadFutura_aud WITH CHECK ADD CONSTRAINT FK_RegistroNovedadFutura_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset criparra:02
--comment: Se agrega el campo soaObservacionesSupervisor en la tabla SolicitudAporte
ALTER TABLE SolicitudAporte_aud ADD soaObservacionesSupervisor varchar(150) NULL;

--changeset squintero:03	
--comment: Se modifica el campo rpiCargueMultipleSupervivencia a NULL 
ALTER TABLE RegistroPersonaInconsistente_aud ALTER COLUMN rpiCargueMultipleSupervivencia bigint NULL;

--changeset mosanchez:04
--comment: Se agrega campo en la tabla Constante
ALTER TABLE Constante_aud ADD cnsDescripcion varchar(250) NULL;

--changeset jusanchez:05
--comment: Se modifica tamaño del prmDescripcion campo en la tabla Parametro
ALTER TABLE Parametro_aud ALTER COLUMN prmDescripcion varchar(250) NULL;

--changeset jocampo:06
--comment: Se agrega y elimina campos en la tabla Requisito
ALTER TABLE Requisito_aud DROP COLUMN reqTipoRequisito;
ALTER TABLE Requisito_aud ALTER COLUMN reqDescripcion varchar (200) NOT NULL;