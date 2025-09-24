--liquibase formatted sql

--changeset jsarmiento_01
--comment: Creacion campo tabla ParametrizacionCartera_aud
ALTER TABLE ParametrizacionCartera_aud ADD pacEstadoAfiliacion varchar(10);
