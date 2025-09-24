--liquibase formatted sql

--changeset mamonroy:01
--comment: 
ALTER TABLE Comunicado ADD comEmpleador bigint;
ALTER TABLE aud.Comunicado_aud ADD comEmpleador bigint;