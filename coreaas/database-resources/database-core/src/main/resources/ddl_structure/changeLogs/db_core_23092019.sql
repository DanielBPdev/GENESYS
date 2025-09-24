--liquibase formatted sql

--changeset squintero:01
--comment:
ALTER TABLE ConexionOperadorInformacion ADD coiNombre varchar(100);
ALTER TABLE aud.ConexionOperadorInformacion_aud ADD coiNombre varchar(100);