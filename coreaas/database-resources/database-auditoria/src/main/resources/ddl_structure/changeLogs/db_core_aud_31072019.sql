--liquibase formatted sql

--changeset mamonroy:01
--comment:mantis
ALTER TABLE IntentoAfiliacion_aud ADD iafComunicado BIGINT;