--liquibase formatted sql

--changeset jvelandia:01
--comment: Se agregan elementos a la tabla DestinatarioComunicado y PrioridadDestinatario
ALTER TABLE Comunicado ADD comPersonaDestinatario BIGINT;
ALTER TABLE aud.Comunicado_aud ADD comPersonaDestinatario BIGINT;