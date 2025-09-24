-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarCodigoSapClientes]
@tipo varchar(200),
@identificacion varchar(200),
@codigoSap varchar(200)
AS
BEGIN

	declare @filtroIdentificacion varchar(200);
	set @filtroIdentificacion = '';
	declare @filtroCodigoSap varchar(200);
	set @filtroCodigoSap = '';
	declare @ScriptSQl varchar(2000);

	if @identificacion != ''
	begin
		set @filtroIdentificacion = CONCAT (' and p.perNumeroIdentificacion =''',@identificacion,'''') 
	end

	if @codigoSap != ''
	begin
		set @filtroCodigoSap = CONCAT (' and cs.codigoSAP =''',@codigoSap,'''') 
	end

	IF @tipo = 'E'
	BEGIN 
		set @ScriptSQl = Concat('SELECT 
				cs.codigoSAP,
				p.perNumeroIdentificacion AS Identificacion,
				p.perRazonSocial AS Nombre,
				case when cs.tipo is null then case p.pertipoidentificacion when ''NIT'' THEN ''E'' ELSE ''P''END ELSE cs.tipo end as tipo,
				p.perId as codigoGenesys
			from [sap].[CodSAPGenesysDeudor] cs
			right join Persona p on p.perId = cs.codigoGenesys
			where
			1=1', @filtroIdentificacion,@filtroCodigoSap);
	END
	ELSE
	BEGIN
		set @ScriptSQl = Concat('SELECT 
				cs.codigoSAP,
				p.perNumeroIdentificacion AS Identificacion,
				CONCAT(p.perPrimerNombre , '' '', p.perSegundoNombre, '' '',p.perPrimerApellido , '' '', p.perSegundoApellido) as nombre,
				case when cs.tipo is null then case p.pertipoidentificacion when ''NIT'' THEN ''E'' ELSE ''P''END ELSE cs.tipo end as tipo,
				p.perId as codigoGenesys
			from [sap].[CodSAPGenesysDeudor] cs
			right join Persona p on p.perId = cs.codigoGenesys
			where
			1=1', @filtroIdentificacion,@filtroCodigoSap);
	END;

	print @scriptSQL 

	EXEC (@scriptSQL)

END