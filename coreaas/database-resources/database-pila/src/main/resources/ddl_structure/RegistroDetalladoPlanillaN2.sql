--- Tabla de control para las planillas N
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'RegistroDetalladoPlanillaN' and TABLE_SCHEMA = 'staging' and COLUMN_NAME = 'actualizarApd')
begin
	alter table staging.RegistroDetalladoPlanillaN add actualizarApd bit
end