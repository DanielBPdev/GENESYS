-- Verificar si la columna existe en la tabla
IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'RegistroSolicitudAnibol' AND COLUMN_NAME = 'rsaMotivoAnulacion')
BEGIN
    -- Agregar la columna si no existe
    ALTER TABLE RegistroSolicitudAnibol
    ADD rsaMotivoAnulacion VARCHAR(200);
END;