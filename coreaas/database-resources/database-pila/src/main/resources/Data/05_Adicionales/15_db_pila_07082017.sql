--liquibase formatted sql

--changeset abaquero:01
--comment: Se adiciona campo en la tabla staging.RegistroDetallado
ALTER TABLE staging.RegistroDetallado ADD redEstadoRegistroCorreccion varchar(40);