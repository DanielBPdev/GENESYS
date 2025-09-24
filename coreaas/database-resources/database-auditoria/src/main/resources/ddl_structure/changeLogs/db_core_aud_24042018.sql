--liquibase formatted sql

--changeset clmarin:01
--comment: Se agregan campos a tablas AporteGeneral_aud y AporteDetallado_aud
ALTER TABLE AporteGeneral_aud ADD apgMarcaPeriodo VARCHAR(19);
ALTER TABLE AporteDetallado_aud ADD apdMarcaPeriodo VARCHAR(19);

--changeset clmarin:02
--comment: Se crea tabla SolicitudCierreRecaudo_aud
CREATE TABLE SolicitudCierreRecaudo_aud
(
    sciId BIGINT NOT NULL IDENTITY(1,1),
    REV bigint NOT NULL,
	REVTYPE smallint NULL,
    sciSolicitudGlobal BIGINT NOT NULL,
    sciEstadoSolicitud VARCHAR(31) NOT NULL,
    sciFechaInicio DATETIME NOT NULL,
    sciFechaFin DATETIME NOT NULL,
    sciTipoCierre VARCHAR(9) NOT NULL,
    sciObservacionesSupervisor VARCHAR(255),
    sciObservacionesContabilidad VARCHAR(255),
    sciUsuarioSupervisor VARCHAR(50),
    sciUsuarioAnalistaContable VARCHAR(50),
    sciCodigoIdentificacionECM VARCHAR(255),
    sciResumen VARCHAR(255)	
)

ALTER TABLE SolicitudCierreRecaudo_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudCierreRecaudo_aud_REV FOREIGN KEY(REV) REFERENCES Revision (revId);

ALTER TABLE SolicitudCierreRecaudo_aud CHECK CONSTRAINT FK_SolicitudCierreRecaudo_aud_REV;
