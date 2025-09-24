--liquibase formatted sql

--changeset flopez:01
--comment:Se modifica tamaño de campo de la tabla PostulacionFovis
ALTER TABLE PostulacionFOVIS_aud ALTER COLUMN pofEstadoHogar VARCHAR (35) NULL;
