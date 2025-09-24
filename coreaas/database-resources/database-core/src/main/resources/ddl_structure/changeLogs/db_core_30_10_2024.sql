
-- Tabla para registrar los comunicados enviados a Niyaraky
IF NOT EXISTS (select * from INFORMATION_SCHEMA.TABLES T WHERE TABLE_NAME = 'ComunicadoNiyaraky')
BEGIN
    -- Parametros Nirayaky
    INSERT INTO dbo.Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato, prmVisualizarPantalla) VALUES (N'NIYARAKY_EMAIL_REST', N'jesus.buitragor@asopagos.com', 0, N'VALOR_GLOBAL_TECNICO', N'Correo con permisos de acceso a la API de Niyaraky', N'TEXT', 1);
    INSERT INTO dbo.Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato, prmVisualizarPantalla) VALUES (N'NIYARAKY_PASSWORD_REST', N'ede4530215b2849dcade09ea637c69ed82987bae', 0, N'VALOR_GLOBAL_TECNICO', N'Contraseña de la cuenta Niyaraky en formato Hash SHA1', N'TEXT', 1);
    INSERT INTO dbo.Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato, prmVisualizarPantalla) VALUES (N'NIYARAKY_URL_BASE', N'DUMMY', 0, N'VALOR_GLOBAL_TECNICO', N'Dirección URL para el acceso a la API de Niyaraky', N'TEXT', 1);

    CREATE TABLE ComunicadoNiyaraky (
    cnId INT IDENTITY PRIMARY KEY,
    cnComunicado BIGINT NOT NULL FOREIGN KEY REFERENCES Comunicado(comId),
    cnIdMensaje BIGINT NULL,
    cnFechaEnvio DATETIME NULL DEFAULT GETDATE(),
    cnObservacion VARCHAR(MAX)
    );
    
END

IF NOT EXISTS (select * from INFORMATION_SCHEMA.TABLES T WHERE TABLE_NAME = 'ComunicadoNiyarakyRecepcion')
BEGIN
    -- Tabla para registrar las actualizaciones de estado recibidas de Niyaraky de los comunicados que enviamos
    CREATE TABLE ComunicadoNiyarakyRecepcion (
        cnrId INT IDENTITY PRIMARY KEY,
        cnrComunicadoNiyaraky INT NULL FOREIGN KEY REFERENCES ComunicadoNiyaraky(cnId),
        cnrEstado BIGINT NULL,
        cnrDescripcion VARCHAR(MAX) NULL,
        cnrFechaEventoRecibido DATETIME NOT NULL DEFAULT GETDATE(),
        cnrFechaEventoRecibidoPorNiyaraky DATETIME NULL,
        cnrRequest VARCHAR(MAX) NULL,
        cnrResponse VARCHAR(MAX) NULL
    );
END

