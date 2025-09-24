--liquibase formatted sql

--changeset abaquero:01
--comment: Actualización del tamaño del campo hebBloque en la tabla HistorialEstadoBloque
alter table dbo.HistorialEstadoBloque alter column hebBloque varchar(20) not null