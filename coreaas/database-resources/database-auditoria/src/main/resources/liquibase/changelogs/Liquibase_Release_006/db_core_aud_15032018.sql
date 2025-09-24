--liquibase formatted sql

--changeset mamonroy:01
--comment: Se modifica campo en la tabla HistoriaResultadoValidacion_aud
ALTER TABLE HistoriaResultadoValidacion_aud ALTER COLUMN hrvDetalle VARCHAR(400) NULL;
