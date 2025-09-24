--liquibase formatted sql

--changeset sbrinez:01
--comment:Se modifica tamaño de campo para la tabla Municipio_aud
ALTER TABLE Municipio_aud ALTER COLUMN munCodigo VARCHAR(5) NOT NULL;