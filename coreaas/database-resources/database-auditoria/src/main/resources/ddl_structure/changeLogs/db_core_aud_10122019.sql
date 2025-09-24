--liquibase formatted sql

--changeset clmarin:01
--comment: Creación columna Beneficiario.benOmitirValidaciones 
ALTER TABLE Beneficiario_aud ADD benOmitirValidaciones bit NULL;