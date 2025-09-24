-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_reenviarDetalleIntegracionClientes]
@direccion varchar(200), 
@integracion varchar(200),
@id varchar(200),
@resultado varchar(200)OUTPUT,
@mensaje varchar(200)OUTPUT
AS
BEGIN
    
	declare @ScriptSQl varchar(2000);
	declare @tabla varchar(200);
	declare @nombreEstado varchar(200) = 'estado';
	
	IF @direccion = 'GENESYS-ERP'
	begin
		set @tabla = concat(@integracion,'_G2E');
		IF @integracion = 'Contactos' or @integracion = 'Relaciones' or @integracion = 'Personas'
		begin
			set @nombreEstado = 'estadoreg';
		end
	end;
	IF @direccion = 'ERP-GENESYS'
	begin
		set @tabla = concat(@integracion,'_E2G');
		IF @integracion = 'Empresas'
		begin
			set @nombreEstado = 'estadoreg';
		end
		IF @integracion = 'Contactos'
		begin
			set @nombreEstado = 'estadoreg';
		end
	end;

	set @scriptSQL = concat ('Update sap.', @tabla ,' set ', @nombreEstado, ' = ', '''P'' WHERE consecutivo = ',@id);

	--print @scriptSQL 

	 EXEC (@scriptSQL)

	set @resultado = 'Ok';
	set @mensaje = 'Funcionando correctamente';

END