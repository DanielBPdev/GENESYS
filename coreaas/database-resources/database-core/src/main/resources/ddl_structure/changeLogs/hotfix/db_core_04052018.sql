--liquibase formatted sql

--changeset dsuesca:01
--comment: Se cambia tipo de datos
ALTER TABLE CuentaAdministradorSubsidio ALTER COLUMN casCodigoBanco VARCHAR(7);
ALTER TABLE CuentaAdministradorSubsidio ALTER COLUMN casNombreBanco VARCHAR(255);
ALTER TABLE aud.CuentaAdministradorSubsidio_aud alter column casCodigoBanco varchar (7)
