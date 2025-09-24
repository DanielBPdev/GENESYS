--liquibase formatted sql

--changeset jocampo:01
--comment: tabla para realización de log de ejecución de scripts entragados sobre corrección de datos.
if not exists (select * from sysobjects where name='ScriptEjecucionLog' and xtype='U')
    create table ScriptEjecucionLog (
        id bigint PRIMARY KEY IDENTITY,
        nombre varchar(255) not null,
        identificador bigint,
        fechaInicioEjecucion DATETIME,
        fechaFinEjecucion DATETIME,
        resultado bit default 0
    )
