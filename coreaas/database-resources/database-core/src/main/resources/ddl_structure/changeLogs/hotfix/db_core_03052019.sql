--liquibase formatted sql

--changeset dsuesca:01
--comment: 
ALTER TABLE CuentaAdministradorSubsidio ADD casGrupoFamiliar bigint;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casGrupoFamiliar bigint;
