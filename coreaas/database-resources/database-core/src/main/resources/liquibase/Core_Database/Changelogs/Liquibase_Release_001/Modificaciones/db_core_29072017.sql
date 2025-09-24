--liquibase formatted sql

--changeset jusanchez:01
--comment: Se adiciona campo a la tabla Parametro	
ALTER TABLE Parametro ADD prmEstado bit NULL;


--changeset squintero:02	
--comment: Se modifica el campo rpiCargueMultipleSupervivencia a NULL 
ALTER TABLE RegistroPersonaInconsistente ALTER COLUMN rpiCargueMultipleSupervivencia bigint NULL;