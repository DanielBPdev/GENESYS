IF NOT EXISTS (
    SELECT COLUMN_NAME
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'ParametrizacionCartera'
    AND COLUMN_NAME = 'pacIncluirLC2'

)
BEGIN
    ALTER TABLE ParametrizacionCartera ADD pacIncluirLC2 BIT;
END

IF NOT EXISTS (
    SELECT COLUMN_NAME
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'ParametrizacionCartera'
    AND COLUMN_NAME = 'pacIncluirLC3'

)
BEGIN
    ALTER TABLE ParametrizacionCartera ADD pacIncluirLC3 BIT;
END

--------------- Auditoria
IF NOT EXISTS (
	SELECT 1
    FROM sys.columns
    WHERE object_id = OBJECT_ID('aud.ParametrizacionCartera_aud')
    AND name = 'pacIncluirLC2'
	
	)
	BEGIN
		ALTER TABLE aud.ParametrizacionCartera_aud ADD pacIncluirLC2 BIT;
	END

IF NOT EXISTS (
	SELECT 1
    FROM sys.columns
    WHERE object_id = OBJECT_ID('aud.ParametrizacionCartera_aud')
    AND name = 'pacIncluirLC3'
	
	)
	BEGIN
		ALTER TABLE aud.ParametrizacionCartera_aud ADD pacIncluirLC3 BIT;
	END

	