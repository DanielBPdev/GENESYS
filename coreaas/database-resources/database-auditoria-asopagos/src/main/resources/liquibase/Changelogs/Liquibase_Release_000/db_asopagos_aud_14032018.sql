--liquibase formatted sql

--changeset jocampo:01
--comment:Se adiciona campo en la tabla Banco_aud
ALTER TABLE Banco_aud ADD banCodigo VARCHAR(6) NULL;