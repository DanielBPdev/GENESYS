--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de campos de control de registro actual y original en staging.RegistroGeneral
alter table staging.RegistroGeneral add regOUTRegistroActual bit
alter table staging.RegistroGeneral add regOUTReginicial bigint