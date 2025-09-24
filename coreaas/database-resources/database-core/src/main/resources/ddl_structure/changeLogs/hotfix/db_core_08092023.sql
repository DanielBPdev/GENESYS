IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'ParametrizacionExclusiones' AND COLUMN_NAME = 'pexEclusionMora' and TABLE_SCHEMA = 'dbo'
)
BEGIN
ALTER TABLE dbo.ParametrizacionExclusiones ADD pexEclusionMora bit
END

IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'ParametrizacionExclusiones_aud' AND COLUMN_NAME = 'pexEclusionMora'  and TABLE_SCHEMA = 'aud'
)
BEGIN
ALTER TABLE aud.ParametrizacionExclusiones_aud ADD pexEclusionMora bit
END
