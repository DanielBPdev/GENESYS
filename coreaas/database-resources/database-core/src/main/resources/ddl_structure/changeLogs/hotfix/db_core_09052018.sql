--liquibase formatted sql

--changeset mosorio:01
--comment: Creacion tabla RegistroSolicitudAnibol
CREATE TABLE RegistroSolicitudAnibol(
rsaId BIGINT NOT NULL IDENTITY(1,1),
rsaFechaHoraRegistro DATETIME NOT NULL,
rsaTipoOperacionAnibol VARCHAR(60) NOT NULL,
rsaParametrosIn VARCHAR (500) NOT NULL,
rsaParametrosOut VARCHAR (500) NULL
);
