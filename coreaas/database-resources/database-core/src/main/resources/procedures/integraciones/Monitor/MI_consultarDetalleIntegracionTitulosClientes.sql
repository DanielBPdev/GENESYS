-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosClientes]
@direccion varchar(200), 
@integracion varchar(200), 
@identificacion varchar(200), 
@codigoSap varchar(200), 
@fechaInicial varchar(200), 
@fechaFinal varchar(200), 
@estado varchar(200), 
@operacion varchar(200)
AS
BEGIN

	declare @tabla varchar(200);

    IF @direccion = 'GENESYS-ERP'
	begin
		set @tabla = concat(@integracion,'_G2E');
	end;
	IF @direccion = 'ERP-GENESYS'
	begin
		set @tabla = concat(@integracion,'_E2G');
	end;

	select 'Consecutivo'
	union all
	select 'Estado'
	union all
	select COLUMN_NAME
	from INFORMATION_SCHEMA.COLUMNS
	where 
	TABLE_NAME = @tabla
	AND COLUMN_NAME <> 'consecutivo'
	AND COLUMN_NAME <> 'FechaEjecucion'
END