--liquibase formatted sql

--changeset mgiraldo:01
/* Organizar campos date*/
ALTER TABLE ListaEspecialRevision ALTER COLUMN lerFechaInicioInclusion DATE;
ALTER TABLE ListaEspecialRevision ALTER COLUMN lerFechaFinInclusion DATE;