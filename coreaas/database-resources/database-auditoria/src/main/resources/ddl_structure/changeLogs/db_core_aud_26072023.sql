--liquibase formatted sql

--changeset keynervides
--comment:
--== core_aud
alter table CuentaAdministradorSubsidioProgramada_aud alter column capCodigoBanco varchar(7)