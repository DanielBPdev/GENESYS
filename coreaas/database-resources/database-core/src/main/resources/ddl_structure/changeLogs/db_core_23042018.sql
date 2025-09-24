--liquibase formatted sql

--changeset clmarin:01
--comment: Se crea tabla AplicacionValidacionSubsidioPersona
CREATE TABLE SolicitudCierreRecaudo
(
    sciId BIGINT NOT NULL IDENTITY(1,1),
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
    sciResumen VARCHAR(255),
	CONSTRAINT PK_SolicitudCierreRecaudo_sciId PRIMARY KEY (sciId),
    CONSTRAINT FK_SolicitudCierreRecaudo_sciSolicitudGlobal FOREIGN KEY (sciSolicitudGlobal) REFERENCES Solicitud (solId)
)

--changeset clmarin:02
--comment: Se agregan campos a tablas AporteGeneral y AporteDetallado
ALTER TABLE AporteGeneral ADD apgMarcaPeriodo VARCHAR(19);
ALTER TABLE AporteDetallado ADD apdMarcaPeriodo VARCHAR(19);