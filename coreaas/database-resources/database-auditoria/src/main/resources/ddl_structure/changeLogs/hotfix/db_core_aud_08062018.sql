--liquibase formatted sql

--changeset borozco:01
--comment: Ajuste capacidad campo sgeEstado
ALTER TABLE SolicitudGestionCobroElectronico_aud ALTER COLUMN sgeEstado VARCHAR(52);
