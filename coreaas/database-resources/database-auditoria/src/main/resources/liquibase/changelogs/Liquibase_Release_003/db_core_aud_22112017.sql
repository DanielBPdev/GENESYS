--liquibase formatted sql

--changeset jvelandia:01
--comment: Se adiciona campo en la tabla VariableComunicado_aud
ALTER TABLE VariableComunicado_aud ADD vcoOrden VARCHAR(3) NULL;