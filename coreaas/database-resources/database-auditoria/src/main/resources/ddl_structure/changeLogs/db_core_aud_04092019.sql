--liquibase formatted sql

--changeset mamonroy:01
--comment:
ALTER TABLE LicenciaDetalle_aud ALTER COLUMN lidClasificacionLicencia varchar (36);