--liquibase formatted sql

--changeset squintero:01
--comment: eliminación de restricción not null en el campo novFechaFin dela tabla staging.Novedad
ALTER TABLE staging.Novedad ALTER COLUMN novFechaFin date NULL