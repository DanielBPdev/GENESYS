-- add_column_DevolucionAporte.sql
-- se agrega columna a la tabla DevolucionAporte por error en Liquibase 12-04-2024
IF (select DISTINCT TABLE_CATALOG  from INFORMATION_SCHEMA.TABLES i where i.TABLE_CATALOG = 'core') IS NOT NULL
BEGIN
	if NOT exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'DevolucionAporte' and COLUMN_NAME = 'apgIdNuevo')
	begin
		alter table DevolucionAporte
		add apgIdNuevo bigInt
	end
END