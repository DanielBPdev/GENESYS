--liquibase formatted sql

--changeset dsuesca:01
--comment: Mantis 0259343
ALTER TABLE CuentaAdministradorSubsidio ADD casIdCuentaOriginal BIGINT;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casIdCuentaOriginal BIGINT;