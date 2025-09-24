--liquibase formatted sql

--changeset squintero:01
--comment:
create table RespuestaEcmExterno (
reeId bigint IDENTITY (1,1) NOT NULL,
reeCud varchar(100),
reeCode int,
reeMessage varchar(500),
reeStatus varchar(100)
);