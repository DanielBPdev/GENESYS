--liquibase formatted sql


--changeset  mgiraldo:01
--comment:eliminación perAutorizaEnvioEmail
ALTER TABLE Persona drop column perAutorizaEnvioEmail;