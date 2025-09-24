--liquibase formatted sql

--changeset clmarin:01
--comment: Eliminacion de campo en la tabla AporteGeneral_aud y creacion de la tabla AporteDetallado_aud
ALTER TABLE AporteGeneral_aud DROP COLUMN apgFormaReconocimientoAporte;
ALTER TABLE AporteDetallado_aud ADD apdFormaReconocimientoAporte varchar (75) NULL;

--changeset clmarin:02
--comment: Se elimina y adiciona campo en la tabla SolicitudAporte_aud
ALTER TABLE SolicitudAporte_aud DROP COLUMN soaObservacionesSupervisor;
ALTER TABLE SolicitudAporte_aud ADD soaTipoSolicitante varchar(13) NULL;

--changeset atoro:03
--comment: Modificacion del tamaño del campo reaEstadoSolicitud para la tabla RegistroEstadoAporte
ALTER TABLE RegistroEstadoAporte_aud ALTER COLUMN reaEstadoSolicitud varchar (25);

--changeset fvasquez:04
--comment: Creacion de la tabla SolicitudCorreccionAporte y Correccion
CREATE TABLE SolicitudCorreccionAporte_aud(
	scaId bigint IDENTITY(1,1) NOT NULL, 
	REV int NOT NULL,
	REVTYPE smallint NULL,
	scaEstadoSolicitud varchar(25) NULL, 
	scaTipoSolicitante varchar(25) NULL, 
	scaObservacionSupervisor varchar(255) NULL,
	scaResultadoSupervisor varchar(10) NULL,
	scaSolicitudGlobal bigint NULL, 
	scaPersona bigint NULL,
	scaAporteGeneral bigint NULL,
);
ALTER TABLE SolicitudCorreccionAporte_aud ADD CONSTRAINT FK_SolicitudCorreccionAporte_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE Correccion_aud(
	corId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	corAporteDetallado bigint NULL,
	corAporteGeneral bigint NULL,
	corSolicitudCorreccionAporte bigint NULL,
);
ALTER TABLE Correccion_aud ADD CONSTRAINT FK_Correccion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);