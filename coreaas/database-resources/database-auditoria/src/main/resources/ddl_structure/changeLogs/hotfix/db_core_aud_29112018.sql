--liquibase formatted sql

--changeset jocorrea:01
--comment: 
CREATE TABLE CalificacionPostulacion_aud (
	cafId BIGINT NOT NULL,
	REV BIGINT NOT NULL,
	REVTYPE SMALLINT NULL,
	cafPostulacionFovis BIGINT NOT NULL,
	cafCicloAsignacion BIGINT NOT NULL,
	cafFechaCalificacion DATETIME NULL,
	cafPuntaje NUMERIC(10,6) NULL,
	cafValorB1 NUMERIC(10,6) NULL,
	cafValorB2 NUMERIC(10,6) NULL,
	cafValorB3 NUMERIC(10,6) NULL,
	cafValorB4 NUMERIC(10,6) NULL,
	cafValorB5 NUMERIC(10,6) NULL,
	cafValorB6 NUMERIC(10,6) NULL
);

CREATE TABLE PostulacionAsignacion_aud (
	pasId BIGINT NOT NULL,
	REV BIGINT NOT NULL,
	REVTYPE SMALLINT NULL,
	pasPostulacionFovis BIGINT NOT NULL,
	pasCicloAsignacion BIGINT NOT NULL,
	pasCalificacionPostulacion BIGINT NULL,
	pasSolicitudAsignacion BIGINT NULL,
	pasPrioridadAsignacion VARCHAR(11) NULL,
	pasValorAsignadoSFV NUMERIC(19, 5) NULL,
	pasResultadoAsignacion VARCHAR(50) NULL,
	pasDocumentoActaAsignacion VARCHAR(255) NULL
);

ALTER TABLE PostulacionFovis_aud ADD pofCalificacionPostulacion BIGINT NULL;