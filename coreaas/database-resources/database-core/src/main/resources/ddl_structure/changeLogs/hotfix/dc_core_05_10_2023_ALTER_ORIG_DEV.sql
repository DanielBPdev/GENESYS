------OrigenAporte------------
IF EXISTS (
    SELECT COLUMN_NAME
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'AporteGeneral'
    AND COLUMN_NAME = 'apgOrigenAporte'
)
BEGIN
    ALTER TABLE AporteGeneral ALTER COLUMN apgOrigenAporte VARCHAR(60);
END
------DevolucionAporte--------
IF EXISTS (
    SELECT COLUMN_NAME
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'DevolucionAporte'
    AND COLUMN_NAME = 'dapMotivoPeticion'
)
BEGIN
    ALTER TABLE DevolucionAporte ALTER COLUMN dapMotivoPeticion VARCHAR(60);
END


----------------AUDITORIA--------------------------
------OrigenAporte------------
IF EXISTS (
    SELECT 1
    FROM sys.columns
    WHERE object_id = OBJECT_ID('aud.AporteGeneral_aud')
    AND name = 'apgOrigenAporte'
	)
BEGIN
    ALTER TABLE aud.AporteGeneral_aud ALTER COLUMN apgOrigenAporte VARCHAR(60);
END
------DevolucionAporte--------
IF EXISTS (
   SELECT 1
    FROM sys.columns
    WHERE object_id = OBJECT_ID('aud.DevolucionAporte_aud')
    AND name = 'dapMotivoPeticion'
	)
BEGIN
    ALTER TABLE aud.DevolucionAporte_aud ALTER COLUMN dapMotivoPeticion VARCHAR(60);
END
