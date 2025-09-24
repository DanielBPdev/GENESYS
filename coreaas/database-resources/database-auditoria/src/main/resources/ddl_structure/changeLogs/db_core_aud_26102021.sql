--liquibase formatted sql
--changeset rcastillo:01
alter table DevolucionAporte_aud alter column dapPeriodoReclamado varchar (500);