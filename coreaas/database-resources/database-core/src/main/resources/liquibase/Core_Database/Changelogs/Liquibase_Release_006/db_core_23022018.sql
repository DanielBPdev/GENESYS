--liquibase formatted sql

--changeset abaquero:01
--comment: Creacion de las tablas ConjuntoValidacionSubsidio y AplicacionValidacionSubsidio
CREATE TABLE ConjuntoValidacionSubsidio(
	cvsId BIGINT NOT NULL,
	cvsTipoValidacion VARCHAR(50) NOT NULL,
CONSTRAINT PK_ConjuntoValidacionSubsidio_cvsId PRIMARY KEY (cvsId)
);

CREATE TABLE AplicacionValidacionSubsidio(
	avsId BIGINT NOT NULL IDENTITY(1,1),
	avsConjuntoValidacionSubsidio BIGINT NOT NULL,
	avsSolicitudLiquidacionSubsidio BIGINT NOT NULL,
	avsEsValidable BIT NOT NULL,
CONSTRAINT PK_AplicacionValidacionSubsidio_avsId PRIMARY KEY (avsId)
);
ALTER TABLE AplicacionValidacionSubsidio ADD CONSTRAINT FK_AplicacionValidacionSubsidio_avsConjuntoValidacionSubsidio FOREIGN KEY (avsConjuntoValidacionSubsidio) REFERENCES ConjuntoValidacionSubsidio
ALTER TABLE AplicacionValidacionSubsidio ADD CONSTRAINT FK_AplicacionValidacionSubsidio_avsSolicitudLiquidacionSubsidio FOREIGN KEY (avsSolicitudLiquidacionSubsidio) REFERENCES SolicitudLiquidacionSubsidio

--changeset abaquero:02
--comment: Insercion de registros en la tabla ConjuntoValidacionSubsidio
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (1,'CONDICION_AGRICOLA');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (2,'RETROACTIVO_NOVEDAD');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (3,'RETROACTIVO_APORTE');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (4,'ESTADO_EMPLEADOR');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (5,'ESTADO_TRABAJADOR');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (6,'TIENE_BENEFICARIOS');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (7,'CAUSACION_SALARIOS');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (8,'TRABAJADOR_ES_EMPLEADOR');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (9,'ESTADO_APORTE');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (10,'DIAS_COTIZADOS_NOVEDADES');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (11,'SALARIO');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (12,'ESTADO_BENEFICIARIO_PADRE');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (13,'ESTADO_BENEFICIARIO_HIJO');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (14,'BENEFICIARIO_AFILIADO_PRINCIPAL_PADRE');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (15,'BENEFICIARIO_EMPLEADOR_PADRE');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (16,'BENEFICIARIO_OTROS_APORTES_PADRE');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (17,'BENEFICIARIO_AFILIADO_PRINCIPAL_HIJO');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (18,'BENEFICIARIO_EMPLEADOR_HIJO');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (19,'BENEFICIARIO_OTROS_APORTES_HIJO');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (20,'BENEFICIARIO_OTRAS_PERSONAS_PADRE');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (21,'BENEFICIARIO_OTRAS_PERSONAS_HIJO');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (22,'CONDICION_INVALIDEZ_PADRE');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (23,'CONDICION_INVALIDEZ_HIJO');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (24,'EDAD_BENEFICIARIO_PADRE');
INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion) VALUES (25,'EDAD_BENEFICIARIO_HIJO');

--changeset squintero:03
--comment: Insercion y actualizacion de registros en la tabla Constante
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_DESAFILIACION_APORTANTES_DEPLOYMENTID','com.asopagos.coreaas.bpm.desafiliacion_aportantes:desafiliacion_aportantes:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para desafiliacion de aportantes');
UPDATE Constante SET cnsNombre = 'BPMS_PROCESS_SOLICITUD_ANULACION_SUBSIDIO_COBRADO_DEPLOYMENTID', cnsValor = 'com.asopagos.coreaas.bpm.solicitud_anulacion_subsidio_cobrado:solicitud_anulacion_subsidio_cobrado:0.0.2-SNAPSHOT' WHERE cnsNombre = 'SOLICITUD_ANULACION_SUBSIDIO_COBRADO';

--changeset jocorrea:04
--comment: Creacion de las tablas SolicitudAnalisisNovedadFovis, SolicitudNovedadFovis y SolicitudNovedadPersonaFovis
CREATE TABLE SolicitudAnalisisNovedadFovis(
	sanId BIGINT NOT NULL IDENTITY(1,1),
	sanSolicitudGlobal BIGINT NOT NULL,
	sanSolicitudNovedad BIGINT NOT NULL,
	sanPersona BIGINT NOT NULL,
	sanEstadoSolicitud VARCHAR(9) NOT NULL,
	sanPostulacionFovis BIGINT NOT NULL,
	sanObservaciones VARCHAR(500) NULL,
CONSTRAINT PK_SolicitudAnalisisNovedadFovis_sanId PRIMARY KEY (sanId)
);
CREATE SEQUENCE SEC_consecutivoSan START WITH 1 INCREMENT BY 1 ;
ALTER TABLE SolicitudAnalisisNovedadFovis ADD CONSTRAINT FK_SolicitudAnalisisNovedadFovis_sanSolicitudGlobal FOREIGN KEY(sanSolicitudGlobal) REFERENCES Solicitud (solId);
ALTER TABLE SolicitudAnalisisNovedadFovis ADD CONSTRAINT FK_SolicitudAnalisisNovedadFovis_sanSolicitudNovedad FOREIGN KEY(sanSolicitudNovedad) REFERENCES SolicitudNovedad (snoId);
ALTER TABLE SolicitudAnalisisNovedadFovis ADD CONSTRAINT FK_SolicitudAnalisisNovedadFovis_sanPersona FOREIGN KEY(sanPersona) REFERENCES Persona (perId);
ALTER TABLE SolicitudAnalisisNovedadFovis ADD CONSTRAINT FK_SolicitudAnalisisNovedadFovis_sanPostulacionFovis FOREIGN KEY(sanPostulacionFovis) REFERENCES PostulacionFOVIS (pofId);

CREATE TABLE SolicitudNovedadFovis(
	snfId BIGINT NOT NULL IDENTITY (1,1),
	snfSolicitudGlobal BIGINT NOT NULL,
	snfEstadoSolicitud VARCHAR(38) NOT NULL,
	snfParametrizacionNovedad BIGINT NOT NULL,
	snfObservaciones VARCHAR(200) NULL,
CONSTRAINT PK_SolicitudNovedadFovis_snfId PRIMARY KEY (snfId)
);
ALTER TABLE SolicitudNovedadFovis ADD CONSTRAINT FK_SolicitudNovedadFovis_snfParametrizacionNovedad FOREIGN KEY(snfParametrizacionNovedad) REFERENCES ParametrizacionNovedad (novId);
ALTER TABLE SolicitudNovedadFovis ADD CONSTRAINT FK_SolicitudNovedadFovis_snfSolicitudGlobal FOREIGN KEY(snfSolicitudGlobal) REFERENCES Solicitud (solId);

CREATE TABLE SolicitudNovedadPersonaFovis(
	spfId BIGINT NOT NULL,
	spfPersona BIGINT NULL,
	spfSolicitudNovedadFovis BIGINT NOT NULL,
	spfPostulacionFovis BIGINT NOT NULL,
CONSTRAINT PK_SolicitudNovedadPersonaFovis_spfId PRIMARY KEY (spfId)
);
CREATE SEQUENCE SEC_consecutivoSpf START WITH 1 INCREMENT BY 1 ;
ALTER TABLE SolicitudNovedadPersonaFovis ADD CONSTRAINT FK_SolicitudNovedadPersonaFovis_spfPersona FOREIGN KEY(spfPersona) REFERENCES Persona (perId);
ALTER TABLE SolicitudNovedadPersonaFovis ADD CONSTRAINT FK_SolicitudNovedadPersonaFovis_spfSolicitudNovedadFovis FOREIGN KEY(spfSolicitudNovedadFovis) REFERENCES SolicitudNovedadFovis (snfId);
ALTER TABLE SolicitudNovedadPersonaFovis ADD CONSTRAINT FK_SolicitudNovedadPersonaFovis_spfPostulacionFovis FOREIGN KEY(spfPostulacionFovis) REFERENCES PostulacionFovis (pofId);
