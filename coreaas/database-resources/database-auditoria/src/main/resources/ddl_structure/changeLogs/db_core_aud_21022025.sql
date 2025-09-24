
--Creacion tablas auditoria en core_aud
IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'GestionUsuariosEmpleador_aud' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE dbo.GestionUsuariosEmpleador_aud (
        gueId BIGINT PRIMARY KEY IDENTITY(1,1),
        gueUsuarioEditado VARCHAR(100),
        gueCampoModificado VARCHAR(100),
        gueValorAnterior VARCHAR(100),
        gueNuevoValor VARCHAR(100),
        gueFechaModificacion DATETIME,
        gueModificadoPor VARCHAR(100),
        REV BIGINT NOT NULL,
        REVTYPE SMALLINT
    );
END

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'GestionUsuariosTrabajador_aud' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE dbo.GestionUsuariosTrabajador_aud (
        gutId BIGINT PRIMARY KEY IDENTITY(1,1),
        gutUsuarioEditado VARCHAR(100),
        gutCampoModificado VARCHAR(100),
        gutValorAnterior VARCHAR(100),
        gutNuevoValor VARCHAR(100),
        gutFechaModificacion DATETIME,
        gutModificadoPor VARCHAR(100),
        REV BIGINT NOT NULL,
        REVTYPE SMALLINT
    );
END

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'GestionUsuariosCcf_aud' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE dbo.GestionUsuariosCcf_aud (
        gucId BIGINT PRIMARY KEY IDENTITY(1,1),
        gucUsuarioEditado VARCHAR(100),
        gucCampoModificado VARCHAR(100),
        gucValorAnterior VARCHAR(100),
        gucNuevoValor VARCHAR(100),
        gucFechaModificacion DATETIME,
        gucModificadoPor VARCHAR(100),
        REV BIGINT NOT NULL,
        REVTYPE SMALLINT
    );
END

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'GestionUsuariosTerceros_aud' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE dbo.GestionUsuariosTerceros_aud (
        gutrId BIGINT PRIMARY KEY IDENTITY(1,1),
        gutrUsuarioEditado VARCHAR(100),
        gutrCampoModificado VARCHAR(100),
        gutrValorAnterior VARCHAR(100),
        gutrNuevoValor VARCHAR(100),
        gutrFechaModificacion DATETIME,
        gutrModificadoPor VARCHAR(100),
        REV BIGINT NOT NULL,
        REVTYPE SMALLINT
    );
END