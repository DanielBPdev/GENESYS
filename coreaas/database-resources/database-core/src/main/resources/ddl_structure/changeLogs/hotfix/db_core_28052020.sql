--liquibase formatted sql

--changeset mamonroy:01
--comment:

CREATE TABLE ExepcionNovedad (
enoId BIGINT IDENTITY(1,1),
enoFechaExcepcion DATETIME2(7),
enoSolicitudNovedadDTO varchar(max),
enoSolicitudNovedadModeloDTO varchar(max),
enoUserDTO varchar(500),
enoExcepcion varchar(max));