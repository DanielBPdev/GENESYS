--liquibase formatted sql

--changeset rcastillo
--comment: modificacion del campo de periodos, para que permita mas periodos. 
alter table dbo.DevolucionAporte_aud alter column dapPeriodoReclamado varchar(1500)