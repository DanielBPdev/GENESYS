--liquibase formatted sql

--changeset abaquero:01
--comment: Cambio de tipo de dato para el campo apdMunicipioLaboral en AporteDetallado 
ALTER TABLE dbo.AporteDetallado ALTER COLUMN apdMunicipioLaboral VARCHAR(5)