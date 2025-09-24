--liquibase formatted sql

--changeset arocha:01
--comment: Actualizacion de campo en la tabla AdmiSubsidioGrupo_aud
ALTER TABLE AdminSubsidioGrupo_aud ALTER COLUMN asgAdministradorSubsidio BIGINT NOT NULL;

--changeset jusanchez:02
--comment: Se adiciona campo en la tabla Beneficiario_aud
ALTER TABLE Beneficiario_aud ADD benRolAfiliado BIGINT NOT NULL;