--liquibase formatted sql

--changeset squintero:02
--comment:Se agrega campos en la tablas Infraestructura_aud y MedioEfectivo_aud
ALTER TABLE MedioEfectivo_aud ADD mefSitioPago BIGINT NULL;