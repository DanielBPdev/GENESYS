--liquibase formatted sql

--changeset jocorrea:01
--comment: 
EXEC sp_RENAME 'aud.LegalizacionDesembolso_aud.lgdFechaTrasnferencia' , 'lgdFechaTransferencia', 'COLUMN';

--changeset jocorrea:02
--comment: 
ALTER TABLE CargueArchivoCruceFovis ADD cacInfoArchivoJsonPayload TEXT;