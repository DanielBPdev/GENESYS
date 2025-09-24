--liquibase formatted sql

--changeset squintero:01
--comment: Ajustes para control de cambios #705 - cambio en longitud de parametros
ALTER TABLE Parametro ALTER COLUMN prmValor VARCHAR (1000);
ALTER TABLE aud.Parametro_aud ALTER COLUMN prmValor VARCHAR (1000);