-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarResumenERPtoGENESYSClientes]
@tipoIntegracion varchar(200), 
@fechaInicio varchar(200), 
@fechaFin varchar(200)
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON

	if(@tipoIntegracion = 'Personas')
	begin
		select PE.fecing,(SELECT CASE PE.OPERACION
		When 'I' Then 'Ingreso'
		When 'U' Then 'Modificacion'
		ELSE 'No aplica'
		END) as operacion,
		(select count(*) from [sap].[Personas_E2G] CI where [estado] = 'P' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as P, 
		(select count(*) from [sap].[Personas_E2G] CI where [estado] = 'V' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as V, 
		(select count(*) from [sap].[Personas_E2G] CI where [estado] = 'E' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as E, 
		(select count(*) from [sap].[Personas_E2G] CI where [estado] = 'S' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as S
		from [sap].[Personas_E2G] PE
		where PE.fecing between @fechaInicio and @fechaFin
		and PE.estado in ('P','V','E','S')
		GROUP BY PE.fecing,PE.operacion
	end
	if(@tipoIntegracion = 'Empresas')
	begin
		select PE.fecing,(SELECT CASE PE.OPERACION
		When 'I' Then 'Ingreso'
		When 'U' Then 'Modificacion'
		ELSE 'No aplica'
		END) as operacion,
		(select count(*) from [sap].[EMPRESAS_E2G] CI where [estadoreg] = 'P' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as P, 
		(select count(*) from [sap].[EMPRESAS_E2G] CI where [estadoreg] = 'V' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as V, 
		(select count(*) from [sap].[EMPRESAS_E2G] CI where [estadoreg] = 'E' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as E, 
		(select count(*) from [sap].[EMPRESAS_E2G] CI where [estadoreg] = 'S' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as S
		from [sap].[EMPRESAS_E2G] PE
		where PE.fecing between @fechaInicio and @fechaFin 
		and PE.[estadoreg] in ('P','V','E','S')
		GROUP BY PE.fecing,PE.operacion
	end
		if(@tipoIntegracion = 'Contactos')
	begin
		select PE.fecing,(SELECT CASE PE.OPERACION
		When 'I' Then 'Ingreso'
		When 'U' Then 'Modificacion'
		ELSE 'No aplica'
		END) as operacion,
		(select count(*) from [sap].[CONTACTOS_E2G] CI where [estadoreg] = 'P' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as P, 
		(select count(*) from [sap].[CONTACTOS_E2G] CI where [estadoreg] = 'V' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as V, 
		(select count(*) from [sap].[CONTACTOS_E2G] CI where [estadoreg] = 'E' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as E, 
		(select count(*) from [sap].[CONTACTOS_E2G] CI where [estadoreg] = 'S' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as S
		from [sap].[CONTACTOS_E2G] PE
		where PE.fecing between @fechaInicio and @fechaFin
		and PE.[estadoreg] in ('P','V','E','S')
		GROUP BY PE.fecing,PE.operacion
	end
END