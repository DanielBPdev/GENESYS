-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_reenviarDetalleIntegracionContables]
@integracion varchar(200),
@id varchar(200),
@resultado varchar(200)OUTPUT,
@mensaje varchar(200)OUTPUT
AS
BEGIN
    
	declare @ScriptSQl varchar(2000);
	declare @tabla varchar(200);
	declare @nombreEstado varchar(200) = 'estadoReg';
	
	IF @integracion = 'Fovis'
	begin
		set @tabla = 'IC_FOVIS_Enc';
	end;
	IF @integracion = 'Cuota'
	begin
		set @tabla = 'IC_CM_Enc';
	end;
	IF @integracion = 'Aportes'
	begin
		set @tabla = 'IC_Aportes_Enc';
	end;

	set @scriptSQL = concat ('Update sap.', @tabla ,' set ', @nombreEstado, ' = ', '''P'' WHERE consecutivo = ',@id);

	--print @scriptSQL 

	 EXEC (@scriptSQL)

	set @resultado = 'Ok';
	set @mensaje = 'Funcionando correctamente';

END