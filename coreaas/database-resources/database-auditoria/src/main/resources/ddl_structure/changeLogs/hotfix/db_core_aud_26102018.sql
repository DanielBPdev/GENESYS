--liquibase formatted sql

--changeset jocorrea:01
--comment: 
EXEC sp_RENAME 'LegalizacionDesembolso_aud.lgdFechaTrasnferencia' , 'lgdFechaTransferencia', 'COLUMN';
