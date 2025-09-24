CREATE OR ALTER PROCEDURE [dbo].[SP_HistoricoAfiliacionPersona]
(
    @idPersona VARCHAR(100),
    @idEmpleador VARCHAR(100) = NULL,
    @tipoAfiliado INT = 0
)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @tipoAfi VARCHAR(100);
    DECLARE @sql NVARCHAR(MAX);
    DECLARE @prefixColumna NVARCHAR(3);

    -- Determinar el tipo de afiliado
    SET @tipoAfi = 
        CASE    
            WHEN @tipoAfiliado = 1 THEN 'TRABAJADOR_DEPENDIENTE'
            WHEN @tipoAfiliado = 2 THEN 'TRABAJADOR_INDEPENDIENTE'
            WHEN @tipoAfiliado = 3 THEN 'PENSIONADO'
            ELSE 'DESCONOCIDO'
        END;

    -- Determinar la tabla base y el prefijo de las columnas según el tipo de afiliado
    DECLARE @tablaBase NVARCHAR(50) = 
        CASE 
            WHEN @tipoAfiliado = 2 THEN 'EstadoAfiliacionPersonaIndependiente'
            WHEN @tipoAfiliado = 3 THEN 'EstadoAfiliacionPersonaPensionado'
            ELSE 'EstadoAfiliacionPersonaEmpresa'
        END;

    -- Prefijo para las columnas dinámicas según el tipo de afiliado
    SET @prefixColumna = 
        CASE 
            WHEN @tipoAfiliado = 2 THEN 'eai'  -- Independiente
            WHEN @tipoAfiliado = 3 THEN 'eap'  -- Pensionado
            ELSE 'eae'  -- Empresa (o cualquier otro caso)
        END;

    -- Construir la consulta dinámica
    SET @sql = N'
    SELECT estado, hraCambioEstado, @tipoAfi AS tipoAfiliado
    FROM (
        -- Subconsulta para estado "ACTIVO"
        SELECT ''ACTIVO'' AS estado, hraFechaIngreso AS hraCambioEstado
        FROM ' + @tablaBase + ' AS t
        LEFT JOIN Afiliado a ON a.afiPersona = t.[' + @prefixColumna + 'Persona]
        LEFT JOIN HistoricoRolAfiliado hr ON hr.hraAfiliado = a.afiid
        JOIN Persona p ON p.perId = t.[' + @prefixColumna + 'Persona]
        WHERE t.[' + @prefixColumna + 'Persona] = @idPersona
        AND (@idEmpleador IS NULL OR hr.hraEmpleador = @idEmpleador)
        AND t.[' + @prefixColumna + 'EstadoAfiliacion] IN (''ACTIVO'', ''INACTIVO'')

        UNION

        -- Subconsulta para estado "INACTIVO"
        SELECT ''INACTIVO'' AS estado, hraFechaRetiro AS hraCambioEstado
        FROM ' + @tablaBase + ' AS t
        LEFT JOIN Afiliado a ON a.afiPersona = t.[' + @prefixColumna + 'Persona]
        LEFT JOIN HistoricoRolAfiliado hr ON hr.hraAfiliado = a.afiid
        JOIN Persona p ON p.perId = t.[' + @prefixColumna + 'Persona]
        WHERE t.[' + @prefixColumna + 'Persona] = @idPersona
        AND (@idEmpleador IS NULL OR hr.hraEmpleador = @idEmpleador)
        AND t.[' + @prefixColumna + 'EstadoAfiliacion] IN (''ACTIVO'', ''INACTIVO'')
    ) AS f
    WHERE f.hraCambioEstado IS NOT NULL
    ORDER BY f.hraCambioEstado';

    -- Ejecutar la consulta dinámica
    EXEC sp_executesql @sql, 
                       N'@idPersona VARCHAR(100), @idEmpleador VARCHAR(100), @tipoAfi VARCHAR(100)', 
                       @idPersona, @idEmpleador, @tipoAfi;
END