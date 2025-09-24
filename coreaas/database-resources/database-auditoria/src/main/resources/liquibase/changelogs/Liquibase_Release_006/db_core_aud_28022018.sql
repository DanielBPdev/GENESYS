--liquibase formatted sql

--changeset ecastano:01
--comment:Creacion de la tabla InhabilidadSubsidioFovis
CREATE TABLE InhabilidadSubsidioFovis_aud(
    isfId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
    isfJefeHogar BIGINT NULL,
    isfIntegranteHogar BIGINT NULL,
    isfFechaInicio DATETIME NULL,
    isfFechaFin DATETIME NULL,
    isfInhabilitadoParaSubsidio BIT NULL,    
);
ALTER TABLE InhabilidadSubsidioFovis_aud ADD CONSTRAINT FK_InhabilidadSubsidioFovis_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset ecastano:02
--comment:Se modifica campo en la tabla PostulacionFOVIS
ALTER TABLE PostulacionFOVIS_aud ALTER COLUMN pofEstadoHogar VARCHAR(40) NULL;

--changeset borozco:03
--comment: Se adiciona campo en la tabla DesafiliacionAportante_aud y se modifica campo en la tabla SolicitudDesafiliacion_aud
ALTER TABLE DesafiliacionAportante_aud ADD apdTipoLineaCobro VARCHAR(3) NOT NULL;
EXEC sp_RENAME 'SolicitudDesafiliacion_aud.sodComentarioAnalista' , 'sodComentarioCoordinador', 'COLUMN';

--changeset borozco:04
--comment:Se modifica el tipo de datos en campo para la tabla SolicitudDesafiliacion_aud
ALTER TABLE SolicitudDesafiliacion_aud ALTER COLUMN sodEstadoSolicitud VARCHAR(9) NULL;