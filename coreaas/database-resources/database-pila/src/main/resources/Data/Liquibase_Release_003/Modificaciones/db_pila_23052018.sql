--liquibase formatted sql

--changeset abaquero:01
--comment: cambio del campo de referencia única de log de errores de índice planilla a referencias serparada para OI y OF

ALTER TABLE dbo.LogErrorPilaM1 DROP COLUMN lp1IndicePlanilla
ALTER TABLE dbo.LogErrorPilaM1 ADD lp1IndicePlanillaOI bigint
ALTER TABLE dbo.LogErrorPilaM1 ADD lp1IndicePlanillaOF bigint