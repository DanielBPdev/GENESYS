--liquibase formatted sql

--changeset abaquero:01
--comment:Se adiciona campo en las tablas TemAportante y staging.RegistroGeneral
ALTER TABLE TemAportante ADD tapMotivoFiscalizacion VARCHAR(30);
ALTER TABLE staging.RegistroGeneral ADD regOUTMotivoFiscalizacion VARCHAR(30);
