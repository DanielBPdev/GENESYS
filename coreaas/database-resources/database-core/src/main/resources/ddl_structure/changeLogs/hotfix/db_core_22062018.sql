--liquibase formatted sql

--changeset dsuesca:01
--comment: Se cambia tipo de datos de tablas que tengan version de documento de alfresco
ALTER TABLE ArchivoConsumosAnibol ALTER COLUMN acnVersionDocumento VARCHAR(20);
ALTER TABLE ArchivoRetiroTerceroPagador ALTER COLUMN arrVersionDocumento VARCHAR(20) NOT NULL;
ALTER TABLE DocumentoSoporte ALTER COLUMN dosVersionDocumento VARCHAR(20) NOT NULL;
ALTER TABLE aud.DocumentoSoporte_aud ALTER COLUMN dosVersionDocumento VARCHAR(20);

--changeset abaquero:02
--comment: Actualizacion tabla ValidatorParamValue
UPDATE ValidatorParamValue SET value='2-15,2-16,2-19,2-20,2-21,2-22,2-23' WHERE id=2110400;
