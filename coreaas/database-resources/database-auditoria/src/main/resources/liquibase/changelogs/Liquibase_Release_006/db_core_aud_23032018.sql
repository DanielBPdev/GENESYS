--liquibase formatted sql

--changeset dsuesca:01
--comment:Se adicionan campos en las tablas CuentaAdministradorSubsidio_aud y DetalleSubsidioAsignado_aud
ALTER TABLE CuentaAdministradorSubsidio_aud ADD casCondicionPersonaAdmin BIGINT NULL;
ALTER TABLE DetalleSubsidioAsignado_aud ADD dsaCondicionPersonaBeneficiario BIGINT NULL;
ALTER TABLE DetalleSubsidioAsignado_aud ADD dsaCondicionPersonaAfiliado BIGINT NULL;
ALTER TABLE DetalleSubsidioAsignado_aud ADD dsaCondicionPersonaEmpleador BIGINT NULL;

--changeset fvasquez:02
--comment:Se modifica campo en la tabla Cartera_aud
ALTER TABLE Cartera_aud ALTER COLUMN carMetodo VARCHAR(8) NULL;
