--liquibase formatted sql

--changeset dasanchez:01
--comment:Se modifican campos en diferentes tablas
ALTER TABLE AFP_aud ALTER COLUMN afpNombre VARCHAR(150) NOT NULL;

ALTER TABLE ARL_aud ALTER COLUMN arlNombre VARCHAR(25) NOT NULL;

ALTER TABLE Beneficio_aud ALTER COLUMN befVigenciaFiscal BIT NOT NULL;

ALTER TABLE CajaCompensacion_aud ALTER COLUMN ccfHabilitado BIT NOT NULL;
ALTER TABLE CajaCompensacion_aud ALTER COLUMN ccfMetodoGeneracionEtiquetas VARCHAR(150) NOT NULL;
ALTER TABLE CajaCompensacion_aud ALTER COLUMN ccfNombre VARCHAR(100) NOT NULL;
ALTER TABLE CajaCompensacion_aud ALTER COLUMN ccfSocioAsopagos BIT NOT NULL;
ALTER TABLE CajaCompensacion_aud ALTER COLUMN ccfDepartamento SMALLINT NOT NULL;
ALTER TABLE CajaCompensacion_aud ALTER COLUMN ccfCodigo VARCHAR(5) NOT NULL;

ALTER TABLE CodigoCIIU_aud ALTER COLUMN ciiCodigo VARCHAR(4) NOT NULL;
ALTER TABLE CodigoCIIU_aud ALTER COLUMN ciiDescripcion VARCHAR(255) NOT NULL;

ALTER TABLE Departamento_aud ALTER COLUMN depCodigo VARCHAR(2) NOT NULL;
ALTER TABLE Departamento_aud ALTER COLUMN depIndicativoTelefoniaFija VARCHAR(2) NOT NULL;
ALTER TABLE Departamento_aud ALTER COLUMN depNombre VARCHAR(100) NOT NULL;

ALTER TABLE DiasFestivos_aud ALTER COLUMN pifConcepto VARCHAR(150) NOT NULL;
ALTER TABLE DiasFestivos_aud ALTER COLUMN pifFecha DATE NOT NULL;

ALTER TABLE ElementoDireccion_aud ALTER COLUMN eldNombre VARCHAR(20) NOT NULL;

ALTER TABLE GradoAcademico_aud ALTER COLUMN graNombre VARCHAR(20) NOT NULL;
ALTER TABLE GradoAcademico_aud ALTER COLUMN graNivelEducativo VARCHAR(43) NOT NULL;

ALTER TABLE Municipio_aud ALTER COLUMN munCodigo VARCHAR(6) NOT NULL;
ALTER TABLE Municipio_aud ALTER COLUMN munNombre VARCHAR(50) NOT NULL;
ALTER TABLE Municipio_aud ALTER COLUMN munDepartamento SMALLINT NOT NULL;

ALTER TABLE OcupacionProfesion_aud ALTER COLUMN ocuNombre VARCHAR(100) NOT NULL;

ALTER TABLE Requisito_aud ALTER COLUMN reqEstado VARCHAR(20) NOT NULL;

ALTER TABLE TipoVia_aud ALTER COLUMN tviNombreVia VARCHAR(20) NOT NULL;