--liquibase formatted sql

--changeset arocha:01
--comment: Se adicionan dos campos a la tabla staging.RegistroGeneral
ALTER TABLE staging.RegistroGeneral ALTER COLUMN regTransaccion BIGINT NULL;