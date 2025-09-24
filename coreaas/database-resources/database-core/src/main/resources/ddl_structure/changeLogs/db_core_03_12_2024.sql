IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ModificacionDummy' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE ModificacionDummy (
        mddId INT IDENTITY(1,1) PRIMARY KEY,
        mddUsuario NVARCHAR(255),
        mddFechaModificacion DATE,
        mddConstante INT,
        CONSTRAINT FK_ModificacionDummy_Constante FOREIGN KEY (mddConstante)
        REFERENCES Constante(cnsId)
    )
END

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ModificacionDummy_aud' AND TABLE_SCHEMA = 'aud')
BEGIN
    CREATE TABLE aud.ModificacionDummy_aud (
        mddId INT NOT NULL,
        REV INT NOT NULL,
	    REVTYPE SMALLINT,
        mddUsuario NVARCHAR(255),
        mddFechaModificacion DATE,
        mddConstante INT
    )
END