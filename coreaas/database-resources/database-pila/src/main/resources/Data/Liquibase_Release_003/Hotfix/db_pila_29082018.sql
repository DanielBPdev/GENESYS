--liquibase formatted sql

--changeset abaquero:01
--comment:Ampliación del campo pscUsuario en la tabla PilaSolicitudCambioNumIdentAportante
ALTER TABLE dbo.PilaSolicitudCambioNumIdentAportante ALTER COLUMN pscUsuario VARCHAR(255) NOT NULL