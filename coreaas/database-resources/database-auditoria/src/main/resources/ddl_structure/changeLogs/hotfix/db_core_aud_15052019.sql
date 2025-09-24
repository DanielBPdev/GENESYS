--liquibase formatted sql

--changeset dsuesca:01
--comment:
ALTER TABLE CuentaAdministradorSubsidio_aud ADD casGrupoFamiliar bigint;