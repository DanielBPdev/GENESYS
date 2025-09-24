--liquibase formatted sql

--changeset fvasquez:01
--comment: Se agrega campo bcaNumeroOperacion
ALTER TABLE Comunicado_aud ADD comEmpresa bigint;