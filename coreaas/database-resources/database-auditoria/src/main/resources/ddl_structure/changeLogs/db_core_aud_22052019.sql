--liquibase formatted sql

--changeset clmarin:01
--comment:
ALTER TABLE ActividadCartera_aud ADD acrObservaciones varchar(400) NULL;