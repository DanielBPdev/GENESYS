--liquibase formatted sql

--changeset abaquero:01
--comment:Adicion de campos en las tablas staging.RegistroGeneral y TemAportante
ALTER TABLE staging.RegistroGeneral ADD regOUTEnviadoAFiscalizacion bit;
ALTER TABLE TemAportante ADD tapEnviadoAFiscalizacion bit;
