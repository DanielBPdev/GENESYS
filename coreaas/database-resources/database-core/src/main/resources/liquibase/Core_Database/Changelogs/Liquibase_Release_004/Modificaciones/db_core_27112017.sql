--liquibase formatted sql

--changeset dasanchez:01 
--comment:Se modifica tamaño de campo en la tabla Constante
ALTER TABLE Constante ALTER COLUMN cnsValor VARCHAR(150) NULL;