--liquibase formatted sql

--changeset keynervides
--comment: Ajuste Codigo banco para fallecidos

alter table CuentaAdministradorSubsidioProgramada alter column capCodigoBanco varchar(7)
alter table aud.CuentaAdministradorSubsidioProgramada_aud alter column capCodigoBanco varchar(7)