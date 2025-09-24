--liquibase formatted sql

--changeset flopez:01
--comment:Se modifica campo en la tabla ProyectoSolucionVivienda
ALTER TABLE ProyectoSolucionVivienda ALTER COLUMN psvModalidad VARCHAR(50) NULL;