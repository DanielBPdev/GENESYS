--liquibase formatted sql

--changeset clmarin:01
--comment: 
ALTER TABLE ItemChequeo_aud ADD ichFechaRecepcionDocumento DATE NULL;