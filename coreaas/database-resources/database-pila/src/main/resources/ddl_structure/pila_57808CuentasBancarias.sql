if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = 'staging' and TABLE_NAME = 'RegistroGeneral' and COLUMN_NAME = 'regCuentaBancariaRecaudo')
begin
	ALTER TABLE staging.RegistroGeneral ADD regCuentaBancariaRecaudo int null;
end