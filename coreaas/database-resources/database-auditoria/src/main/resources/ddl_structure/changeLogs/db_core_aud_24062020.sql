--liquibase formatted sql

--changeset cmarin:01
--comment:
ALTER TABLE SitioPago_aud ADD sipPrincipal bit;
ALTER TABLE CuentaAdministradorSubsidio_aud ADD casIdPuntoDeCobro VARCHAR(200);