--liquibase formatted sql

--changeset abaquero:01
--comment: Se amplia el tamaño del campo de teléfono fijo en la tabla Ubicación
alter table dbo.Ubicacion alter column ubiTelefonoFijo varchar(10) null
alter table aud.Ubicacion_aud alter column ubiTelefonoFijo varchar(10) null

--changeset jocorrea:02
--comment: Se amplia el tamaño del campo de teléfono fijo en la tabla Ubicación
exec sp_rename 'dbo.CerficadoEscolarBeneficiario', 'CertificadoEscolarBeneficiario';
exec sp_rename 'aud.CerficadoEscolarBeneficiario_aud', 'CertificadoEscolarBeneficiario_aud';