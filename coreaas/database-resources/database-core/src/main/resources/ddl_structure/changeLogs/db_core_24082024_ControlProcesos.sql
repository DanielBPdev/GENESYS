
IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.TABLES
    WHERE TABLE_NAME = 'ControlProcesos' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    CREATE TABLE ControlProcesos(
    cpaId BIGINT IDENTITY (1,1) PRIMARY KEY,
    cpaSolicitudGlobal BIGINT,
    cpaNumeroRadicado VARCHAR(100),
    cpaNombreProceso VARCHAR(250),
    cpaNombreTarea VARCHAR(250),
    cpaNombreProcesoEjecutado VARCHAR(100),
    cpaFechaInicio DATETIME2,
    cpaFechaFin DATETIME2,
    cpaTiempoProceso DATETIME,
    cpaEstado VARCHAR (50),
    cpaUsuario VARCHAR(100),
    cpaNumeroRegistroObjetivos BIGINT,
    cpaNumeroRegistroProcesados BIGINT,
    cpaNumeroRegistroErrores BIGINT,
    cpaNumeroRegistroValidados BIGINT
    FOREIGN KEY (cpaSolicitudGlobal) REFERENCES Solicitud(solid)
    )
END