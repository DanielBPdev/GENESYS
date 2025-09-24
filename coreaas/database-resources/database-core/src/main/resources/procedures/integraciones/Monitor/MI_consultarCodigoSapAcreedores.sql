-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
 CREATE OR ALTER PROCEDURE [sap].[MI_consultarCodigoSapAcreedores]
@identificacion varchar(200),
@codigoSap varchar(200)
AS
BEGIN

	declare @filtroIdentificacion varchar(200);
	declare @filtroCodigoSap varchar(200);
	declare @ScriptSQl varchar(2000);

	if @identificacion != ''
	begin
		set @filtroIdentificacion = CONCAT (' and p.perNumeroIdentificacion =''',@identificacion,'''') 
	end

	if @codigoSap != ''
	begin
		set @filtroCodigoSap = CONCAT (' and cs.codigoSAP =''',@codigoSap,'''') 
	end

	--IF @tipo = 'E'
	--BEGIN 
		set @ScriptSQl = Concat('SELECT 
				cs.codigoSAP,
				p.perNumeroIdentificacion AS Identificacion,
				p.perRazonSocial AS Nombre,
				case when cs.tipo is null then case p.pertipoidentificacion when ''NIT'' THEN ''E'' ELSE ''P''END ELSE cs.tipo end as tipo,
				p.perId as codigoGenesys
			from [sap].[CodSAPGenesysAcreedor] cs
			right join Persona p on p.perId = cs.codigoGenesys
			where 1=1 ',
			 @filtroIdentificacion, @filtroCodigoSap);
	--END
	--ELSE
	--BEGIN
	--	set @ScriptSQl = Concat('SELECT 
	--			cs.codigoSAP,
	--			p.perNumeroIdentificacion AS Identificacion,
	--			CONCAT(p.perPrimerNombre , '' '', p.perSegundoNombre, '' '',p.perPrimerApellido , '' '', p.perSegundoApellido),
	--			cs.tipo,
	--			cs.codigoGenesys
	--		from [sap].[CodSAPGenesysAcreedor] cs
	--		inner join Persona p on p.perId = cs.codigoGenesys
	--		where
	--		tipo = ''',@tipo,'''', @filtroIdentificacion, @filtroCodigoSap);
	--END;

	print @scriptSQL 

	EXEC (@scriptSQL)

END