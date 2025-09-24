-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarResumenGENESYStoERPClientesTotal]
@tipoIntegracion varchar(200), 
@fechaInicio varchar(200), 
@fechaFin varchar(200)
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON
	DECLARE @varP Int;
	DECLARE @varV Int;
	DECLARE @varE Int;
	DECLARE @varS Int;

	if(@tipoIntegracion = 'Personas')
	begin
	
		select @varP = count(*) from [sap].[Personas_G2E] CI where [estadoreg] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[Personas_G2E] CI where [estadoreg] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[Personas_G2E] CI where [estadoreg] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[Personas_G2E] CI where [estadoreg] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,'No aplica' as operacion,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
	if(@tipoIntegracion = 'Empresas')
	begin
		
		select @varP = count(*) from [sap].[EMPRESAS_G2E] CI where [estadoreg] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[EMPRESAS_G2E] CI where [estadoreg] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[EMPRESAS_G2E] CI where [estadoreg] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[EMPRESAS_G2E] CI where [estadoreg] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,'No aplica' as operacion,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
		if(@tipoIntegracion = 'Contactos')
	begin
		
		select @varP = count(*) from [sap].[CONTACTOS_G2E] CI where [estadoreg] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[CONTACTOS_G2E] CI where [estadoreg] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[CONTACTOS_G2E] CI where [estadoreg] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[CONTACTOS_G2E] CI where [estadoreg] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,'No aplica' as operacion,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
		if(@tipoIntegracion = 'Relaciones')
	begin
		
		select @varP = count(*) from [sap].[RELACIONES_G2E] CI where [estadoreg] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[RELACIONES_G2E] CI where [estadoreg] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[RELACIONES_G2E] CI where [estadoreg] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[RELACIONES_G2E] CI where [estadoreg] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,'No aplica' as operacion,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
END