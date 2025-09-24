--liquibase formatted sql

--changeset jvelandia:01
--comment: Se adiciona campo en la tabla VariableComunicado
ALTER TABLE VariableComunicado ADD vcoOrden VARCHAR(3) NULL;