--liquibase formatted sql

--changeset jocorrea:01
--comment:Se modifica tamaño campo en la tabla CargueArchivoCruceFovisReunidos
ALTER TABLE CargueArchivoCruceFovisReunidos ALTER COLUMN cfrMunicipio VARCHAR (100);
