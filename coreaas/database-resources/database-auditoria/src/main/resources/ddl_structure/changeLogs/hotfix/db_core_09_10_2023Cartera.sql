IF NOT EXISTS (
	SELECT 1
    FROM sys.columns
    WHERE object_id = OBJECT_ID('ParametrizacionCartera_aud')
    AND name = 'pacIncluirLC2'
	
	)
	BEGIN
		ALTER TABLE ParametrizacionCartera_aud ADD pacIncluirLC2 BIT;
	END

IF NOT EXISTS (
	SELECT 1
    FROM sys.columns
    WHERE object_id = OBJECT_ID('ParametrizacionCartera_aud')
    AND name = 'pacIncluirLC3'
	
	)
	BEGIN
		ALTER TABLE ParametrizacionCartera_aud ADD pacIncluirLC3 BIT;
	END
