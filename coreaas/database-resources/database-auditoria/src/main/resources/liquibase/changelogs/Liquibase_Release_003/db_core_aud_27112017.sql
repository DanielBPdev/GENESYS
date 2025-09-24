--liquibase formatted sql

--changeset dasanchez:01 
--comment:Se modifica tamaño de campo en la tabla Constante_aud
ALTER TABLE Constante_aud ALTER COLUMN cnsValor VARCHAR(150) NULL;