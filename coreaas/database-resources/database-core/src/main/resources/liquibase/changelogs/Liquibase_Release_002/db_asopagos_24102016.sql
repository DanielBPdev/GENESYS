--liquibase formatted sql

--changeset mgiraldo:01
/*Solución mantis 0216142*/
UPDATE MUNICIPIO SET munNombre='EL PAUJIL' WHERE munNombre LIKE '%PAUJÍL%';
UPDATE MUNICIPIO SET munNombre='CUASPUD' WHERE munNombre LIKE '%CUASPÚD%';

--changeset sbrinez:02
/*solución mantis 0215703*/
INSERT INTO Parametro (prmNombre, prmValor) VALUES ('BPMS_TIMEOUT', '30');

--changeset jocampo:03
ALTER TABLE solicitudAfiliacionPersona ALTER COLUMN sapEstadoSolicitud VARCHAR (50);

--changeset jcamargo:04
/*HU110*/
CREATE TABLE DatoTemporalSolicitud(
	dtsId bigint IDENTITY(1,1) NOT NULL,
	dtsSolicitud bigint NULL,
	dtsjsonPayload varchar(500) NULL,
	
    CONSTRAINT PK_DatoTemporalSolicitud_dtsId PRIMARY KEY (dtsId) 
);


--changeset jcamargo:05
Insert into ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapOrden,vapEstadoProceso)values('AFILIACION_EMPRESAS_PRESENCIAL','111-059-1','VALIDACION_EMPLEADOR_BD_CORE',1,'ACTIVO');

--changeset halzate:06
ALTER TABLE EntidadPagadora ADD cargoContacto VARCHAR (20);

--changeset mgiraldo:07
 ALTER TABLE EntidadPagadora drop column cargoContacto;
 ALTER TABLE EntidadPagadora ADD epaCargoContacto VARCHAR (20);