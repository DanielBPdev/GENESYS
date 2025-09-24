
--GLPI69244
IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'ExclusionCartera' AND COLUMN_NAME = 'excPeriodoDeuda' and TABLE_SCHEMA = 'dbo'
)
BEGIN
ALTER TABLE dbo.ExclusionCartera ADD excPeriodoDeuda date
END

IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'ExclusionCartera_aud' AND COLUMN_NAME = 'excPeriodoDeuda'  and TABLE_SCHEMA = 'aud'
)
BEGIN
ALTER TABLE aud.ExclusionCartera_aud ADD excPeriodoDeuda date
END
