--liquibase formatted sql

--changeset squintero:01
--comment:
ALTER TABLE RegistroSolicitudAnibol ADD rsaEstadoSolicitudAnibol varchar(60);