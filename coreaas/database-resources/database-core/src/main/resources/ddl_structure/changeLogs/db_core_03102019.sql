--liquibase formatted sql

--changeset squintero:01
--comment:
CREATE TABLE RegistroInconsistenciaTarjeta (
ritId BigInt,
ritIdProceso BigInt,
ritTipoNovedad varchar(20),
ritTipoIdentificacion varchar(20),
ritNumeroIdentificacion varchar(20),
ritTarjetaBloqueo varchar(50),
ritTarjetaExpedida varchar(50),
ritSaldoTraslado numeric,
ritFechaTransaccion datetime,
ritUsuario varchar(100),
ritMotivoInconsistencia varchar(50),
ritEstadoResolucion varchar(50)
);
