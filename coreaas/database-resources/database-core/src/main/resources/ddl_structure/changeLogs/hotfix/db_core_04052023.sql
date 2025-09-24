--liquibase formatted sql

--changeset
--comment: Agrega campo carDeudaParcial a tabl cartera_aud
IF
NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='Cartera_aud' AND column_name='carDeudaParcial')
ALTER TABLE aud.Cartera_aud
    ADD carDeudaParcial varchar(32);

alter table DatosRegistraduriaNacional
alter column drnInformante varchar(50)
