--liquibase formatted sql

--changeset mamonroy:01
--comment:
ALTER TABLE ConexionOperadorInformacion_aud ADD coiNombre varchar(100);