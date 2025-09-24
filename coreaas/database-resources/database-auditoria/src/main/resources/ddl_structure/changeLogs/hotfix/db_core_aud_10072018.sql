--liquibase formatted sql

--changeset jocorrea:01
--comment: Cambio de tipo de datos tabla LegalizacionDesembolso_aud
ALTER TABLE LegalizacionDesembolso_aud ALTER COLUMN lgdDocumentoSoporte VARCHAR(255);
ALTER TABLE LegalizacionDesembolso_aud ALTER COLUMN lgdDocumentoSoporteBack VARCHAR(255);

--changeset jocorrea:02
--comment: Cambio logitud campo cmsNombreArchivo
ALTER TABLE CargueMultipleSupervivencia_aud ALTER COLUMN cmsNombreArchivo VARCHAR(255);
