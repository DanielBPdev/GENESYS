--liquibase formatted sql

--changeset mamonroy:01
--comment:
ALTER TABLE LicenciaDetalle ALTER COLUMN lidClasificacionLicencia varchar (36);
ALTER TABLE aud.LicenciaDetalle_aud ALTER COLUMN lidClasificacionLicencia varchar (36);