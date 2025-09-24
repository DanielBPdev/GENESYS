--liquibase formatted sql

--changeset  mgiraldo:01
--comment: Actualización tamaño de campos
ALTER TABLE AFP ALTER COLUMN  afpNombre VARCHAR(150);
ALTER TABLE Constante ALTER COLUMN cnsNombre VARCHAR(100);
ALTER TABLE Constante ALTER COLUMN cnsValor VARCHAR(100);
ALTER TABLE Parametro DROP CONSTRAINT UK_Parametro_prmNombre;  
ALTER TABLE Parametro ALTER COLUMN prmNombre VARCHAR(100);
ALTER TABLE Parametro ADD CONSTRAINT UK_Parametro_prmNombre UNIQUE (prmNombre);
ALTER TABLE Parametro ALTER COLUMN prmValor VARCHAR(150);
ALTER TABLE Requisito ALTER COLUMN reqDescripcion VARCHAR (100);
ALTER TABLE Requisito ALTER COLUMN reqDescripcion VARCHAR (100);
ALTER TABLE Ubicacion ALTER COLUMN ubiDireccionFisica VARCHAR (150);
ALTER TABLE VariableComunicado ALTER COLUMN vcoNombreConstante VARCHAR (100);
ALTER TABLE ProductoNoConforme ALTER COLUMN pncValorCampoBack VARCHAR (150);
ALTER TABLE ProductoNoConforme ALTER COLUMN pncValorCampoFront VARCHAR (150);
