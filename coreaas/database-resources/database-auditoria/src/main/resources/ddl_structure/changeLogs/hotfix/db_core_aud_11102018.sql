--liquibase formatted sql

--changeset abaquero:01
--comment: Se amplia el tamaño del campo de teléfono fijo en la tabla Ubicación
ALTER TABLE dbo.Ubicacion_aud ALTER COLUMN ubiTelefonoFijo VARCHAR(10) null

--changeset jocorrea:02
--comment: Se amplia el tamaño del campo de teléfono fijo en la tabla Ubicación
exec sp_rename 'dbo.CerficadoEscolarBeneficiario_aud', 'CertificadoEscolarBeneficiario_aud';