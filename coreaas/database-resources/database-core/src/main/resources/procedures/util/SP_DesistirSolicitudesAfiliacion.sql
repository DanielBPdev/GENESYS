CREATE OR ALTER PROCEDURE [dbo].[SP_DesistirSolicitudesAfiliacion]
AS
BEGIN

    DECLARE @localDate DATETIME = dbo.getLocalDate(); 
	DECLARE @twoDaysAgo DATETIME = DATEADD(DAY, -2, CONVERT(DATE, @localDate));

    UPDATE SolicitudAfiliacionPersona 
    SET sapEstadoSolicitud = 'CERRADA'
    WHERE sapId IN (
        SELECT sl.sapId
        FROM SolicitudAfiliacionPersona sl
        JOIN desistirSolicitudes ds ON sl.sapSolicitudGlobal = ds.desIdSolicitud
        WHERE sl.sapEstadoSolicitud = ds.desEstadoSolicitud
			AND ds.desFechaDesistir BETWEEN @twoDaysAgo AND @localDate
    );

    UPDATE Solicitud 
    SET solResultadoProceso = 'DESISTIDA'
    WHERE solId IN (
        SELECT ds.desIdSolicitud
        FROM desistirSolicitudes ds
        WHERE ds.desFechaDesistir BETWEEN @twoDaysAgo AND @localDate
          AND EXISTS (
              SELECT 1
              FROM SolicitudAfiliacionPersona sl
              WHERE sl.sapSolicitudGlobal = ds.desIdSolicitud
          )
    );
END;