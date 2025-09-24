--liquibase formatted sql

--changeset borozco:01
--comment: Se agrega un nuevo campo con relacion a la tabla 
ALTER TABLE NotificacionPersonal_aud ADD ntpCartera BIGINT;
