IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ModificacionDummy_aud' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE dbo.ModificacionDummy_aud (
        mddId INT NOT NULL,
        REV INT NOT NULL,
	    REVTYPE SMALLINT,
        mddUsuario NVARCHAR(255),
        mddFechaModificacion DATE,
        mddConstante INT
    )
END