--liquibase formatted sql

--changeset rarboleda:01
--comment: Creacion campo roaOportunidadPago
ALTER TABLE RolAfiliado_aud ADD roaOportunidadPago varchar(11);