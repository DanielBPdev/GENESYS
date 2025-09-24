--liquibase formatted sql

--changeset alquintero:01
--comment: Creacion tabla SolicitudVerificacionFovis_aud
CREATE TABLE SolicitudVerificacionFovis_aud(
	svfId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	svfSolicitudGlobal bigint NOT NULL,
	svfPostulacionFOVIS bigint NULL,
	svfEstadoSolicitud varchar(42) NULL,
	svfTipo varchar(15) NULL,
	svfObservaciones varchar(500) NULL
);

--changeset alquintero:02
--comment: Relaciones tabla SolicitudVerificacionFovis_aud
ALTER TABLE SolicitudVerificacionFovis_aud WITH CHECK ADD CONSTRAINT FK_SolicitudVerificacionFovis_aud_REV FOREIGN KEY(REV) REFERENCES Revision (revId);
ALTER TABLE SolicitudVerificacionFovis_aud CHECK CONSTRAINT FK_SolicitudVerificacionFovis_aud_REV;

--changeset rlopez:03
--comment: Se agrega campo banNit
ALTER TABLE Banco_aud ADD banNit VARCHAR(18);

--changeset rlopez:04
--comment: Se agrega campo pcsCalendarioPagoFallecimiento
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsCalendarioPagoFallecimiento SMALLINT;

