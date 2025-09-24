--liquibase formatted sql

--changeset jvelandia:01
--comment: Se agrega campo comPersonaDestinatario
ALTER TABLE Comunicado_aud ADD comPersonaDestinatario BIGINT;