--liquibase formatted sql

--changeset atoro:01
--comment: Se adiciona columna daeTipoDocumentoAdjunto para la tabla DocumentoAdministracionEstadoSolicitud_aud, se crean las tablas InformacionFaltanteAportante_aud y Trazabilidad_aud
ALTER TABLE dbo.DocumentoAdministracionEstadoSolicitud_aud ADD daeTipoDocumentoAdjunto varchar (19);

CREATE TABLE dbo.InformacionFaltanteAportante_aud(
	ifaId bigint NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ifaSolicitud bigint NOT NULL,
	ifaTipoGestion varchar(22) NULL,
	ifaResponsableInformacion varchar(18) NULL,
	ifaTipoDocumentoGestion varchar (10) NULL,
	ifaMedioComunicacion varchar (19) NULL,
	ifaObservaciones varchar (500) NULL
) ;

CREATE TABLE dbo.Trazabilidad_aud (
	traId bigint NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	traSolicitud bigint NOT NULL,
	traActividad varchar(29) NULL,
	traEstadoSolicitud varchar(18) NULL,
	traFecha datetime NULL,
	traComunicado bigint NULL,
	traUsuario varchar (20) NULL
);

ALTER TABLE InformacionFaltanteAportante_aud WITH CHECK ADD CONSTRAINT FK_InformacionFaltanteAportante_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE Trazabilidad_aud WITH CHECK ADD CONSTRAINT FK_Trazabilidad_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);