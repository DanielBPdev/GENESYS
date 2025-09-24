--liquibase formatted sql

--changeset hhernandez:01
--comment: Se elimina la tabla ActualizacionDatosEmpleador y se crea la tabla GestionNotiNoEnviadas
ALTER TABLE ActualizacionDatosEmpleador_aud DROP CONSTRAINT FK_ActualizacionDatosEmpleador_aud_REV;
DELETE FROM ActualizacionDatosEmpleador_aud;
CREATE TABLE GestionNotiNoEnviadas_aud(
	gneId bigint NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	gneEmpresa bigint NOT NULL,
	gneTipoInconsistencia varchar(20) NOT NULL,
	gneCanalContacto varchar(20) NOT NULL,
	gneFechaIngreso datetime NOT NULL,
	gneEstadoGestion varchar(20) NOT NULL,
	gneObservaciones varchar(60),
	gneFechaRespuesta datetime,
);
ALTER TABLE GestionNotiNoEnviadas_aud WITH CHECK ADD CONSTRAINT FK_GestionNotiNoEnviadas_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset atoro:02
--comment: Se adiciona campo en la tabla DocumentoAdministracionEstadoSolicitud
ALTER TABLE DocumentoAdministracionEstadoSolicitud_aud ADD daeActividad varchar(29) NULL;