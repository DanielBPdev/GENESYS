--liquibase formatted sql

--changeset jzambrano:01
--comment: Se modifica campo en la tabla SucursaRolContactEmpleador_aud
ALTER TABLE SucursaRolContactEmpleador_aud ALTER COLUMN srcId BIGINT NULL;