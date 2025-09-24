--liquibase formatted sql
--changeset rcastillo:01
alter table DevolucionAporte alter column dapPeriodoReclamado varchar (500);
alter table aud.DevolucionAporte_aud alter column dapPeriodoReclamado varchar (500);