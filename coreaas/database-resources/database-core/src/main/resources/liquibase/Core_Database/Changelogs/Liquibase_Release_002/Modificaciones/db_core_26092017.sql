--liquibase formatted sql

--changeset arocha:01
--comment: Actualizacion de campo en la tabla AdmiSubsidioGrupo
ALTER TABLE AdminSubsidioGrupo ALTER COLUMN asgAdministradorSubsidio BIGINT NOT NULL;

--changeset jusanchez:02
--comment: Se adiciona campo en la tabla Beneficiario
ALTER TABLE Beneficiario ADD benRolAfiliado BIGINT NULL;
UPDATE Beneficiario SET Beneficiario.benRolAfiliado = RolAfiliado.roaId FROM RolAfiliado INNER JOIN Beneficiario ON RolAfiliado.roaAfiliado = Beneficiario.benAfiliado;
ALTER TABLE Beneficiario ALTER COLUMN benRolAfiliado BIGINT NOT NULL;
ALTER TABLE Beneficiario ADD CONSTRAINT FK_Beneficiario_benRolAfiliado FOREIGN KEY (benRolAfiliado) REFERENCES RolAfiliado(roaId);

--changeset clmarin:03
--comment: Se eliminan registros de las tablas GrupoPrioridad, DestinatarioComunicado, DestinatarioGrupo y PrioridadDestinatario
DELETE FROM PrioridadDestinatario;
DELETE FROM DestinatarioGrupo;
DELETE FROM DestinatarioComunicado;
DELETE FROM GrupoPrioridad;