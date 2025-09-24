-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarResumenAcreedores]
@fechaInicio varchar(200), 
@fechaFin varchar(200)
AS
BEGIN

		select PE.fecing,(SELECT CASE PE.OPERACION
		When 'I' Then 'Ingreso'
		When 'U' Then 'Modificacion'
		ELSE 'No aplica'
		END) as operacion,
		(select count(*) from [sap].Acreedores CI where [estadoReg] = 'P' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as P, 
		(select count(*) from [sap].Acreedores CI where [estadoReg] = 'V' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as V, 
		(select count(*) from [sap].Acreedores CI where [estadoReg] = 'E' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as E, 
		(select count(*) from [sap].Acreedores CI where [estadoReg] = 'S' and CI.fecing = PE.fecing AND CI.OPERACION = PE.OPERACION) as S
		from [sap].Acreedores PE
		where PE.fecing between @fechaInicio and @fechaFin
		GROUP BY PE.fecing,PE.operacion
	
END