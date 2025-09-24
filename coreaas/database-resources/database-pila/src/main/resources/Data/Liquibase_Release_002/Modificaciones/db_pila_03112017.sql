--liquibase formatted sql

--changeset arocha:01
--comment:Se adiciona y modifica campo en la tabla TemNovedad
ALTER TABLE TemNovedad ADD tenValor VARCHAR(255);
ALTER TABLE TemNovedad ALTER COLUMN tenFechaInicioNovedad DATE NULL;
