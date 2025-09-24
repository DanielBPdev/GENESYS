--liquibase formatted sql

--changeset abaquero:01
--comment: Ajuste en la longitud del campo pscUsuarioAprobador en la tabla PilaSolicitudCambioNumIdentAportante
alter table dbo.PilaSolicitudCambioNumIdentAportante alter column pscUsuarioAprobador varchar(255)