
IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.TABLES
    WHERE TABLE_NAME = 'DesistirSolicitudes' and TABLE_SCHEMA = 'dbo'
)
BEGIN
CREATE TABLE DesistirSolicitudes ( desId INT IDENTITY(1,1) PRIMARY KEY, desIdSolicitud BIGINT NOT NULL,  desIdTarea BIGINT, desEstadoSolicitud VARCHAR(255), desFechaDesistir DATETIME2, desParametroDesistir VARCHAR(255), desTipoTransaccion VARCHAR(255), desFechaModificacion DATETIME CONSTRAINT CK_desistirSolicitudes_solicitud FOREIGN KEY (desIdSolicitud) REFERENCES Solicitud(solid));
END