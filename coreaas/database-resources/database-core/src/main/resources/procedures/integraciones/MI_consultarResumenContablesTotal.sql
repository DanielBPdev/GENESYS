
-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarResumenContablesTotal]
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

	if(@tipoIntegracion = 'Fovis')
	begin

		select @varP = count(*) from [sap].[IC_FOVIS_Enc] CI where [estadoReg] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[IC_FOVIS_Enc] CI where [estadoReg] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[IC_FOVIS_Enc] CI where [estadoReg] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[IC_FOVIS_Enc] CI where [estadoReg] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
	if(@tipoIntegracion = 'Cuota')
	begin

		select @varP = count(*) from [sap].[IC_CM_Enc] CI where [estadoReg] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[IC_CM_Enc] CI where [estadoReg] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[IC_CM_Enc] CI where [estadoReg] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[IC_CM_Enc] CI where [estadoReg] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
		if(@tipoIntegracion = 'Aportes')
	begin
		select @varP = count(*) from [sap].[IC_Aportes_Enc] CI where [estadoReg] = 'P' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varV = count(*) from [sap].[IC_Aportes_Enc] CI where [estadoReg] = 'V' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varE = count(*) from [sap].[IC_Aportes_Enc] CI where [estadoReg] = 'E' and CI.fecing between @fechaInicio and @fechaFin; 
		select @varS = count(*) from [sap].[IC_Aportes_Enc] CI where [estadoReg] = 'S' and CI.fecing between @fechaInicio and @fechaFin;		
		select 'No aplica' fecing,@varP as 'varP',@varV as 'varV',@varE as 'varE',@varS as 'varS';

	end
END