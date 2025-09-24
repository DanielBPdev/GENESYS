--liquibase formatted sql

--changeset squintero:01
--comment:

DROP TABLE RegistroInconsistenciaTarjeta;

CREATE TABLE RegistroInconsistenciaTarjeta (
ritId bigint IDENTITY(1,1) NOT NULL,
ritIdProceso bigInt,
ritTipoNovedad varchar(20),
ritTipoIdentificacion varchar(20),
ritNumeroIdentificacion varchar(20),
ritTarjetaBloqueo varchar(50),
ritTarjetaExpedida varchar(50),
ritSaldoTraslado numeric,
ritFechaTransaccion datetime,
ritUsuario varchar(100),
ritMotivoInconsistencia varchar(50),
ritEstadoResolucion varchar(50),
ritDetalleResolucion varchar(500),
ritNombreCompleto varchar(250),
CONSTRAINT PK_RegistroInconsistenciaTarjeta_ritId PRIMARY KEY (ritId)
);
