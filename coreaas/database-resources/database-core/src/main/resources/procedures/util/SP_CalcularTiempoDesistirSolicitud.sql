CREATE OR ALTER PROCEDURE [dbo].[SP_CalcularTiempoDesistirSolicitud]
(
    @idSolicitud BIGINT,
    @idTareaString VARCHAR(20) = NULL,
    @estadoSolicitud VARCHAR(100), --NO_CONFORME_GESTIONADA, NO_CONFORME_SUBSANABLE, ASIGNADA_AL_BACK, NO_CONFORME_EN_GESTION, REGISTRO_INTENTO_AFILIACION, RADICADA, PRE_RADICADA, CERRADA
    @tipoTransaccion VARCHAR(100) = NULL
)
AS
BEGIN
    DECLARE @idTarea BIGINT;

    -- Convertir @idTareaString a BIGINT o NULL
    IF @idTareaString = 'null' OR @idTareaString = ''
    BEGIN
        SET @idTarea = NULL;
    END
    ELSE 
    BEGIN
        SET @idTarea = CONVERT(BIGINT, @idTareaString);
    END;

    DECLARE @fechaModificacion DATETIME = dbo.getLocalDate();
    DECLARE @TiempoDesistirSolicitud BIGINT;
    DECLARE @FechaFinal DATETIME;
    DECLARE @ParametroDesistir NVARCHAR(100);

    -- Si el estado es 'CERRADA', solo actualizar el estado
    IF @estadoSolicitud = 'CERRADA'
    BEGIN
        UPDATE desistirSolicitudes
        SET 
            desEstadoSolicitud = @estadoSolicitud,
            desFechaModificacion = @fechaModificacion
        WHERE desIdSolicitud = @idSolicitud;

        PRINT 'Se actualizó solo el estado a CERRADA.';
        RETURN; -- Salir del procedimiento
    END;

    -- Determinar el parámetro a usar basado en el tipo de transacción y estado de la solicitud
    SELECT @ParametroDesistir = 
        CASE 
            WHEN @tipoTransaccion = 'AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION' THEN
                CASE
                    WHEN @estadoSolicitud = 'PRE_RADICADA' THEN '122_CORREGIR_INFORMACION_TIMER'
                    --WHEN @estadoSolicitud = 'ASIGNADA_AL_BACK' THEN 'BPM_ADW_TIEMPO_ASIGNACION_BACK'
                    ELSE '122_CORREGIR_INFORMACION_TIMER'
                END
        END;

    -- Calcular el tiempo en días basado en el parámetro seleccionado
    SELECT @TiempoDesistirSolicitud =
        CASE  
            WHEN prmValor LIKE '%s' THEN CAST(REPLACE(prmValor, 's', '') AS BIGINT) / (60 * 60 * 24)
            WHEN prmValor LIKE '%m' THEN CAST(REPLACE(prmValor, 'm', '') AS BIGINT) / (60 * 24)
            WHEN prmValor LIKE '%h' THEN CAST(REPLACE(prmValor, 'h', '') AS BIGINT) / 24
            WHEN prmValor LIKE '%d' THEN CAST(REPLACE(prmValor, 'd', '') AS BIGINT)
            ELSE CAST(prmValor AS BIGINT) / (60 * 60 * 24) 
        END
    FROM dbo.Parametro 
    WHERE prmNombre = @ParametroDesistir;

    -- Calcular la fecha final
    SET @FechaFinal = DATEADD(DAY, @TiempoDesistirSolicitud, @fechaModificacion);

    -- Verificar si ya existe la solicitud con el mismo ID
    IF EXISTS (
        SELECT 1 
        FROM desistirSolicitudes 
        WHERE desIdSolicitud = @idSolicitud
    )
    BEGIN
        -- Si existe, verificar si el estado es diferente
        IF EXISTS (
            SELECT 1 
            FROM desistirSolicitudes 
            WHERE desIdSolicitud = @idSolicitud 
              AND desEstadoSolicitud = @estadoSolicitud
              AND (desIdTarea = @idTarea OR desIdTarea IS NULL)
        )
        BEGIN
            -- Si el estado es el mismo, no hacer nada
            PRINT 'No se realizaron cambios, el estado es el mismo.';
        END
        ELSE
        BEGIN
            -- Si el estado es diferente, actualizar los campos
            UPDATE desistirSolicitudes
            SET 
                desEstadoSolicitud = @estadoSolicitud,
                desFechaDesistir = @FechaFinal,
                desParametroDesistir = @ParametroDesistir,
                desFechaModificacion = @fechaModificacion,
                desIdTarea = @idTarea
            WHERE desIdSolicitud = @idSolicitud;

            PRINT 'Se actualizó la información para el ID de solicitud.';
        END
    END
    ELSE
    BEGIN
        -- Si no existe, insertar un nuevo registro
        INSERT INTO desistirSolicitudes
        (
            desIdSolicitud,
            desIdTarea,
            desEstadoSolicitud,
            desFechaDesistir,
            desParametroDesistir,
            desTipoTransaccion,
            desFechaModificacion
        )
        VALUES
        (
            @idSolicitud,
            @idTarea,
            @estadoSolicitud,
            @FechaFinal,
            @ParametroDesistir,
            @tipoTransaccion,
            @fechaModificacion
        );

        PRINT 'Se insertó un nuevo registro.';
    END
END;
