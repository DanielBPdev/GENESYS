--liquibase formatted sql

--changeset
--comment: Agrega campo carDeudaParcial a tabl cartera_aud
IF NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='Cartera' AND column_name='carDeudaParcial')
ALTER TABLE Cartera ADD carDeudaParcial varchar(32);
