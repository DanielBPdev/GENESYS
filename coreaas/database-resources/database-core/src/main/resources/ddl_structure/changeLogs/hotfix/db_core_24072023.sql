IF NOT EXISTS (
    SELECT COLUMN_NAME
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'Empleador'
    AND COLUMN_NAME = 'empTrasladoCajasCompensacion'
)
BEGIN
    ALTER TABLE Empleador
    ADD empTrasladoCajasCompensacion BIT;
END
--------------- Auditoria
IF NOT EXISTS (
	SELECT 1
    FROM sys.columns
    WHERE object_id = OBJECT_ID('aud.Empleador_aud')
    AND name = 'empTrasladoCajasCompensacion'
	)
	BEGIN
		ALTER TABLE aud.Empleador_aud
		ADD empTrasladoCajasCompensacion BIT;
	END