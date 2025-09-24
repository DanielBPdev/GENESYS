-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarResumenERPtoGENESYSClientesTotal]
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

		select @varP = count(*) from [sap].[Personas_E2G] CI where [estado] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[Personas_E2G] CI where [estado] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[Personas_E2G] CI where [estado] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[Personas_E2G] CI where [estado] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,'No aplica' as operacion,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
	if(@tipoIntegracion = 'Empresas')
	begin

		select @varP = count(*) from [sap].[EMPRESAS_E2G] CI where [estadoreg] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[EMPRESAS_E2G] CI where [estadoreg] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[EMPRESAS_E2G] CI where [estadoreg] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[EMPRESAS_E2G] CI where [estadoreg] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,'No aplica' as operacion,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
		if(@tipoIntegracion = 'Contactos')
	begin

		select @varP = count(*) from [sap].[CONTACTOS_E2G] CI where [estadoreg] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[CONTACTOS_E2G] CI where [estadoreg] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[CONTACTOS_E2G] CI where [estadoreg] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[CONTACTOS_E2G] CI where [estadoreg] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,'No aplica' as operacion,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
END