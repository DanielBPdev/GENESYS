--liquibase formatted sql

--changeset arocha:01 
--comment: Se elimina columna redRegistroGeneralPrincipal
ALTER TABLE staging.RegistroDetallado DROP COLUMN redRegistroGeneralPrincipal