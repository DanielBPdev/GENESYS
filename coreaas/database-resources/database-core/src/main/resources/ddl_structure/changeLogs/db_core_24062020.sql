--liquibase formatted sql

--changeset cmarin:01
--comment: CC terceros pagadores
ALTER TABLE dbo.SitioPago ADD sipPrincipal bit  NOT NULL CONSTRAINT DF_SitioPago_sipPrincipal DEFAULT 0 WITH VALUES;
ALTER TABLE aud.SitioPago_aud ADD sipPrincipal bit;
ALTER TABLE dbo.CuentaAdministradorSubsidio ADD casIdPuntoDeCobro VARCHAR(200);
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casIdPuntoDeCobro VARCHAR(200);
