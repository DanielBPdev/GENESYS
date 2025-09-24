	IF NOT EXISTS (select 1 from INFORMATION_SCHEMA.TABLES t where t.TABLE_NAME = 'BitacoraActualizarCarteraLog')
	BEGIN 
		CREATE TABLE BitacoraActualizarCarteraLog(
		bacId bigInt not null identity,
		tipoDocumento nvarchar(30),
		numeroDocumento nvarchar(30),
		periodo nvarchar(15),
		tipoSolicitante varchar(30),
		fecha dateTime not null
		)
	END