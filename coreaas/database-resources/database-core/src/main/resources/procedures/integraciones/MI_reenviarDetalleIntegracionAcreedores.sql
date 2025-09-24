-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_reenviarDetalleIntegracionAcreedores]
@id varchar(200),
@resultado varchar(200)OUTPUT,
@mensaje varchar(200)OUTPUT
AS
BEGIN
    
	declare @ScriptSQl varchar(2000);
	declare @tabla varchar(200) = 'Acreedores'
	declare @nombreEstado varchar(200) = 'estadoreg';


	set @scriptSQL = concat ('Update sap.', @tabla ,' set ', @nombreEstado, ' = ', '''P'' WHERE consecutivo = ',@id);

	print @scriptSQL 

	 EXEC (@scriptSQL)

	set @resultado = 'Ok';
	set @mensaje = 'Funcionando correctamente';

END