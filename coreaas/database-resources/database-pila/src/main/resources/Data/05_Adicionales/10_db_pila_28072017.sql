--liquibase formatted sql

--changeset anbuitrago:01
--comment: Se agrega campo en la tabla PilaSolicitudCambioNumIdentAportante
ALTER TABLE dbo.PilaSolicitudCambioNumIdentAportante ADD pscErrorValidacionLog bigint;