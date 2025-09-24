--liquibase formatted sql

--changeset jocorrea:01
--comment: Cambio de tamanio de campo caaNombreArchivo en tablas CargueArchivoActualizacion y CargueArchivoCruceFovis
ALTER TABLE CargueArchivoActualizacion ALTER COLUMN caaNombreArchivo VARCHAR(255) NOT NULL;
ALTER TABLE CargueArchivoCruceFovis ALTER COLUMN cacNombreArchivo VARCHAR(255) NOT NULL;