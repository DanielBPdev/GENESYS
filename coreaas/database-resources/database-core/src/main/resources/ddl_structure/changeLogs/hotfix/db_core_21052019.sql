IF EXISTS (
        SELECT 1
        FROM INFORMATION_SCHEMA.TABLES
        WHERE TABLE_NAME = 'PostulacionProveedor'
          AND TABLE_SCHEMA = 'dbo'
    )
    BEGIN
        EXEC sp_rename 'dbo.PostulacionProveedor', 'PostulacionProvOfe', 'OBJECT';
    END

    IF NOT EXISTS (
        SELECT *
        FROM INFORMATION_SCHEMA.COLUMNS
        WHERE
            TABLE_NAME = 'PostulacionProvOfe'
            AND TABLE_SCHEMA = 'dbo'
            AND COLUMN_NAME = 'prpPsvid'
    )
    BEGIN
	    ALTER TABLE PostulacionProvOfe ADD prpPsvid bigint
    END

    IF NOT EXISTS (
        SELECT *
        FROM INFORMATION_SCHEMA.COLUMNS
        WHERE
            TABLE_NAME = 'PostulacionProvOfe'
            AND TABLE_SCHEMA = 'dbo'
            AND COLUMN_NAME = 'prpOferente'
    )
    BEGIN
        ALTER TABLE PostulacionProvOfe ADD prpOferente bigint
    END