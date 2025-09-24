--liquibase formatted sql

--changeset abaquero:01
--comment: Cambio de tipo de dato para el campo apdMunicipioLaboral en AporteDetallado_aud
ALTER TABLE dbo.AporteDetallado_aud ALTER COLUMN apdMunicipioLaboral VARCHAR(5)