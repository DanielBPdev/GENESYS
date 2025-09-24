--liquibase formatted sql

--changeset jsarmiento_01
--comment: Creacion campo tabla ParametrizacionCartera
ALTER TABLE ParametrizacionCartera ADD pacEstadoAfiliacion varchar(10);

--changeset jsarmiento:02
--comment: Creacion campo de la tabla audParametrizacionCartera_aud del esquema AUD
ALTER TABLE aud.ParametrizacionCartera_aud ADD pacEstadoAfiliacion varchar(10);