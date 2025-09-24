IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CargueCertificadosMasivos_aud' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE dbo.CargueCertificadosMasivos_aud (
        ccmId BIGINT ,
        ccmIdArchivoCargueCertificadosMasivos BIGINT,
        ccmTipoIdentificacion VARCHAR(255),
        ccmNumeroIdentificacion VARCHAR(255),
        ccmFechaYHoraCargue DATETIME2 NOT NULL,
        ccmCodigoIdentificacionECM VARCHAR(255),
        REVTYPE SMALLINT,
        REV INT NOT NULL,
    )
END


IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ControlCertificadosMasivos_aud' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE dbo.ControlCertificadosMasivos_aud (
        ccmId BIGINT NOT NULL,
        ccmIdArchivoECM VARCHAR(255) NULL,
        ccmNombreArchivo VARCHAR(255) NULL,
        ccmFechaGeneracion DATETIME NULL,
        ccmIdEmpleador BIGINT NOT NULL,
        REVTYPE SMALLINT,
        REV INT NOT NULL,
        ccmTipoCertificado varchar(50),
        ccmNombreCargue varchar(36) null
    )
END