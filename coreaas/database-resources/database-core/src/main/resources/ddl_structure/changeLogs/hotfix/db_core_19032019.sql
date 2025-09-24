--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE Comunicado ADD comEmpresa bigint;
ALTER TABLE aud.Comunicado_aud ADD comEmpresa bigint;