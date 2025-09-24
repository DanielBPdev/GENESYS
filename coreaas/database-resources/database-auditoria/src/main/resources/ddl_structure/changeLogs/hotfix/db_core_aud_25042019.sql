--liquibase formatted sql

--changeset dsuesca:01
--comment:
ALTER TABLE CuentaAdministradorSubsidio_aud ADD casEmpleador bigint;
ALTER TABLE CuentaAdministradorSubsidio_aud ADD casAfiliadoPrincipal bigint;
ALTER TABLE CuentaAdministradorSubsidio_aud ADD casBeneficiarioDetalle bigint;