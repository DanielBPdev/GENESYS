--liquibase formatted sql

--changeset atoro:01
--comment: Se crea tabla SolicitudAporte
CREATE TABLE dbo.SolicitudAporte (
	soaId bigint NOT NULL IDENTITY,
	soaSolicitudGlobal bigint NOT NULL,
	soaEstadoSolicitud varchar(20) NULL,
	soaAporteGeneral bigint NOT NULL,
	CONSTRAINT PK_SolicitudAporte_soaId PRIMARY KEY (soaId)
); 

ALTER TABLE dbo.SolicitudAporte ADD CONSTRAINT FK_SolicitudAporte_soaSolicitudGlobal FOREIGN KEY (soaSolicitudGlobal) REFERENCES dbo.Solicitud(solId);
ALTER TABLE dbo.SolicitudAporte ADD CONSTRAINT FK_SolicitudAporte_soaAporteGeneral FOREIGN KEY (soaAporteGeneral) REFERENCES dbo.AporteGeneral(apgId);
ALTER TABLE dbo.SolicitudAporte ADD CONSTRAINT UK_SolicitudAporte_soaSolicitudGlobal UNIQUE (soaSolicitudGlobal); 