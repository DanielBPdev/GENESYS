update ValidacionProceso
set vapestadoproceso = 'INACTIVO'
where vapvalidacion = 'VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO'
and vapbloque = 'NOVEDAD_SOLICITUD_SERVICIOS_SIN_AFILIACION_TRABAJADOR_DEPENDIENTE'

IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'roaFechaFinCondicionVeterano' AND TABLE_NAME = 'RolAfiliado' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    ALTER TABLE dbo.RolAfiliado ADD roaFechaFinCondicionVeterano DATETIME 
END

IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'roaFechaInicioCondicionVeterano' AND TABLE_NAME = 'RolAfiliado' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    ALTER TABLE dbo.RolAfiliado ADD roaFechaInicioCondicionVeterano DATETIME 
END

IF exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'roaClaseTrabajador' AND TABLE_NAME = 'RolAfiliado' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    alter table dbo.RolAfiliado alter column roaClaseTrabajador varchar(25);
END


IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'roaFechaFinCondicionVeterano' AND TABLE_NAME = 'RolAfiliado_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
    ALTER TABLE aud.RolAfiliado_aud ADD roaFechaFinCondicionVeterano DATETIME 
END

IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'roaFechaInicioCondicionVeterano' AND TABLE_NAME = 'RolAfiliado_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
    ALTER TABLE aud.RolAfiliado_aud ADD roaFechaInicioCondicionVeterano DATETIME 
END

IF exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'roaClaseTrabajador' AND TABLE_NAME = 'RolAfiliado_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
    alter table aud.RolAfiliado_aud alter column roaClaseTrabajador varchar(25);
END
