--liquibase formatted sql

--changeset jusanchez:01
--comment: Se agrega nuevo campo en la tabla Parametro
ALTER TABLE Parametro ADD prmEstado bit NULL;