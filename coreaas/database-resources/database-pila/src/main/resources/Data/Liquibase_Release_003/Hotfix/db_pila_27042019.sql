--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de campo para el usuario aprobador en historial de estados
alter table HistorialEstadoBloque add hebUsuarioEspecifico varchar(255)