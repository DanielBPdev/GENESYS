IF NOT EXISTS (
	SELECT 1
    FROM sys.columns
    WHERE object_id = OBJECT_ID('Empleador_aud')
    AND name = 'empTrasladoCajasCompensacion'
	)
	BEGIN
		ALTER TABLE Empleador_aud
		ADD empTrasladoCajasCompensacion BIT;
	END