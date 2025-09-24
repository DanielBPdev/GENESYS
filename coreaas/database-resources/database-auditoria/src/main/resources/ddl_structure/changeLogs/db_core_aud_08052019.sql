--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE PostulacionFOVIS_aud ADD pofInfoParametrizacion VARCHAR(MAX) NULL
ALTER TABLE PostulacionFOVIS_aud ADD pofSalarioAsignacion NUMERIC(12,6) NULL
ALTER TABLE PostulacionFOVIS_aud ADD pofValorSFVAjustado NUMERIC(19,5) NULL