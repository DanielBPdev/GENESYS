--liquibase formatted sql

--changeset ecardenas:01
--comment: 
DELETE FROM PARAMETRO WHERE PRMNOMBRE = '112_IMPRIMIR_FORMULARIO_TIMER';

--changeset jocorrea:02
--comment: 
--Creación tabla CalificacionPostulacion
CREATE TABLE CalificacionPostulacion (
	cafId BIGINT IDENTITY (1,1) NOT NULL,
	cafPostulacionFovis BIGINT NOT NULL,
	cafCicloAsignacion BIGINT NOT NULL,
	cafFechaCalificacion DATETIME NULL,
	cafPuntaje NUMERIC(10,6) NULL,
	cafValorB1 NUMERIC(10,6) NULL,
	cafValorB2 NUMERIC(10,6) NULL,
	cafValorB3 NUMERIC(10,6) NULL,
	cafValorB4 NUMERIC(10,6) NULL,
	cafValorB5 NUMERIC(10,6) NULL,
	cafValorB6 NUMERIC(10,6) NULL,
	CONSTRAINT PK_CalificacionPostulacion_cafId PRIMARY KEY (cafId ASC)
) ;

ALTER TABLE CalificacionPostulacion  WITH CHECK ADD  CONSTRAINT FK_CalificacionPostulacion_cafPostulacionFovis FOREIGN KEY(cafPostulacionFovis)
REFERENCES PostulacionFOVIS (pofId);
ALTER TABLE CalificacionPostulacion CHECK CONSTRAINT FK_CalificacionPostulacion_cafPostulacionFovis;
ALTER TABLE CalificacionPostulacion  WITH CHECK ADD CONSTRAINT FK_CalificacionPostulacion_cafCicloAsignacion FOREIGN KEY(cafCicloAsignacion)
REFERENCES CicloAsignacion (ciaId);
ALTER TABLE CalificacionPostulacion CHECK CONSTRAINT FK_CalificacionPostulacion_cafCicloAsignacion;

--Creación tabla CalificacionPostulacion_aud
CREATE TABLE aud.CalificacionPostulacion_aud (
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

--Creación tabla PostulacionAsignacion
CREATE TABLE PostulacionAsignacion (
	pasId BIGINT  NOT NULL,
	pasPostulacionFovis BIGINT NOT NULL,
	pasCicloAsignacion BIGINT NOT NULL,
	pasCalificacionPostulacion BIGINT NULL,
	pasSolicitudAsignacion BIGINT NULL,
	pasPrioridadAsignacion VARCHAR(11) NULL,
	pasValorAsignadoSFV NUMERIC(19, 5) NULL,
	pasResultadoAsignacion VARCHAR(50) NULL,
	pasDocumentoActaAsignacion VARCHAR(255) NULL,
	CONSTRAINT PK_PostulacionAsignacion_pasId PRIMARY KEY (pasId ASC)
);

ALTER TABLE PostulacionAsignacion  WITH CHECK ADD  CONSTRAINT FK_PostulacionAsignacion_pasPostulacionFovis FOREIGN KEY(pasPostulacionFovis)
REFERENCES PostulacionFOVIS (pofId);
ALTER TABLE PostulacionAsignacion CHECK CONSTRAINT FK_PostulacionAsignacion_pasPostulacionFovis;
ALTER TABLE PostulacionAsignacion  WITH CHECK ADD CONSTRAINT FK_PostulacionAsignacion_pasCicloAsignacion FOREIGN KEY(pasCicloAsignacion)
REFERENCES CicloAsignacion (ciaId);
ALTER TABLE PostulacionAsignacion CHECK CONSTRAINT FK_PostulacionAsignacion_pasCicloAsignacion;
ALTER TABLE PostulacionAsignacion  WITH CHECK ADD CONSTRAINT FK_PostulacionAsignacion_pasCalificacionPostulacion FOREIGN KEY(pasCalificacionPostulacion)
REFERENCES CalificacionPostulacion (cafId);
ALTER TABLE PostulacionAsignacion CHECK CONSTRAINT FK_PostulacionAsignacion_pasCalificacionPostulacion;
ALTER TABLE PostulacionAsignacion  WITH CHECK ADD  CONSTRAINT FK_PostulacionAsignacion_pasSolicitudAsignacion FOREIGN KEY(pasSolicitudAsignacion)
REFERENCES SolicitudAsignacion (safId);
ALTER TABLE PostulacionAsignacion CHECK CONSTRAINT FK_PostulacionAsignacion_pasSolicitudAsignacion;

--Creación tabla PostulacionAsignacion_aud

CREATE TABLE aud.PostulacionAsignacion_aud (
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

--Creación secuencia SEC_PostulacionAsignacion

CREATE SEQUENCE SEC_PostulacionAsignacion START WITH 1 INCREMENT BY 1;

--Agregar campo a la tabla PostulacionFovis

ALTER TABLE PostulacionFovis ADD pofCalificacionPostulacion BIGINT NULL;
ALTER TABLE PostulacionFovis  WITH CHECK ADD CONSTRAINT FK_PostulacionFovis_pofCalificacionPostulacion FOREIGN KEY(pofCalificacionPostulacion)
REFERENCES CalificacionPostulacion (cafId);
ALTER TABLE PostulacionFovis CHECK CONSTRAINT FK_PostulacionFovis_pofCalificacionPostulacion;

--Agregar campo a la tabla PostulacionFovis_aud

ALTER TABLE aud.PostulacionFovis_aud ADD pofCalificacionPostulacion BIGINT NULL;

--Migración datos de calificación y asignación

INSERT INTO CalificacionPostulacion (cafPostulacionFovis, cafCicloAsignacion, cafFechaCalificacion, cafPuntaje)
SELECT pofId, pofCicloAsignacion, pofFechaCalificacion, pofPuntaje
FROM PostulacionFovis
WHERE pofPuntaje IS NOT NULL
AND pofFechaCalificacion IS NOT NULL
AND (pofResultadoAsignacion NOT IN ('ESTADO_CALIFICADO_NO_ASIGNADO') OR pofResultadoAsignacion IS NULL);

INSERT INTO CalificacionPostulacion (cafPostulacionFovis, cafCicloAsignacion, cafFechaCalificacion, cafPuntaje)
SELECT pofId, (SELECT safCicloAsignacion FROM SolicitudAsignacion WHERE safId = pofSolicitudAsignacion), pofFechaCalificacion, pofPuntaje
FROM PostulacionFovis
WHERE pofPuntaje IS NOT NULL
AND pofFechaCalificacion IS NOT NULL
AND pofResultadoAsignacion = 'ESTADO_CALIFICADO_NO_ASIGNADO';

INSERT INTO PostulacionAsignacion (pasId, pasPostulacionFovis, pasCicloAsignacion,
pasCalificacionPostulacion,
pasSolicitudAsignacion,
pasPrioridadAsignacion, pasValorAsignadoSFV, pasResultadoAsignacion,
pasDocumentoActaAsignacion)
SELECT NEXT VALUE FOR SEC_PostulacionAsignacion, pofId, 
(SELECT safCicloAsignacion FROM SolicitudAsignacion WHERE safId = pofSolicitudAsignacion), 
(SELECT cafId FROM CalificacionPostulacion WHERE cafPostulacionFovis = pofId AND cafCicloAsignacion = (SELECT safCicloAsignacion FROM SolicitudAsignacion WHERE safId = pofSolicitudAsignacion)),
pofSolicitudAsignacion,
pofPrioridadAsignacion, pofValorAsignadoSFV, pofResultadoAsignacion,
pofIdentificardorDocumentoActaAsignacion
FROM PostulacionFovis
WHERE pofSolicitudAsignacion IS NOT NULL
AND pofResultadoAsignacion IS NOT NULL;

--changeset jocorrea:03
--comment:

ALTER TABLE aud.CalificacionPostulacion_aud  WITH CHECK ADD CONSTRAINT FK_CalificacionPostulacion_aud_REV FOREIGN KEY(REV) REFERENCES aud.Revision (revId);
ALTER TABLE aud.PostulacionAsignacion_aud  WITH CHECK ADD CONSTRAINT FK_PostulacionAsignacion_aud_REV FOREIGN KEY(REV) REFERENCES aud.Revision (revId);

--changeset abaquero:04
--comment: Actualización en configuración de validación FileProcessing PILA M1
UPDATE ValidatorParamValue SET value='9' WHERE id=2110772