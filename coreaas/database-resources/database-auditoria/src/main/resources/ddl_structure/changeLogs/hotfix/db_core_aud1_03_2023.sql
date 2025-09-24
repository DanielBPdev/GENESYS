IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'pacEstadoAfiliacion' AND TABLE_NAME = 'ParametrizacionCartera_aud' AND TABLE_SCHEMA = 'dbo')
BEGIN
alter table ParametrizacionCartera_aud add pacEstadoAfiliacion varchar(10)
END