if not exists (select * from information_schema.columns where column_name = 'maaIdCuentaBancaria' and table_name = 'masivoArchivo' and table_schema = 'masivos')
	begin
		alter table masivos.masivoArchivo add maaIdCuentaBancaria bigint;
	end