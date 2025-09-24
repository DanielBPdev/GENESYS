-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_guardarCodigoSapClientes]
@e_codigoGenesys varchar(200),
@e_codigoSAP varchar(200),
@e_tipocliente varchar(200),
@resultado varchar(200)OUTPUT,
@mensaje varchar(200)OUTPUT
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON

	--Exec secondSPName  @anyparams
	Exec SAP.sp_CambioCodigoSAP @e_codigoGenesys,@e_codigoSAP,@e_tipocliente;

	set @resultado = 'Ok';
	set @mensaje = 'Funcionando correctamente';

END