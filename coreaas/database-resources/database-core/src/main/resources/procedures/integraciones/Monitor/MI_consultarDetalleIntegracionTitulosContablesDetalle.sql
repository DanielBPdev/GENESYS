-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosContablesDetalle]
@integracion varchar(200),
@idEncabezado varchar(200)
AS
BEGIN

	declare @tabla varchar(200);

    IF @integracion = 'Fovis'
	begin
		set @tabla = 'IC_FOVIS_Det';
	end;
	IF @integracion = 'Aportes'
	begin
		set @tabla = 'IC_Aportes_Det';
	end;
	IF @integracion = 'Cuota'
	begin
		set @tabla = 'IC_CM_Det';
	end;

	select COLUMN_NAME
	from INFORMATION_SCHEMA.COLUMNS
	where 
	TABLE_NAME = @tabla

END