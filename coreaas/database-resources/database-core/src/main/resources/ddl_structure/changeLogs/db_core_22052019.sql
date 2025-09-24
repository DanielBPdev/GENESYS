--liquibase formatted sql

--changeset dsuesca:01
--comment: Se crea tabla control de cambios subsidios
ALTER TABLE ActividadCartera ADD acrObservaciones varchar(400) NULL;
ALTER TABLE aud.ActividadCartera_aud ADD acrObservaciones varchar(400) NULL;