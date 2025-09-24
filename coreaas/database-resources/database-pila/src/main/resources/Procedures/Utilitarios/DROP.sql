--REFACTOR PILA MAYO 2020 INICIO
IF (OBJECT_ID('USP_BorrarDatosTemporalesAportes') IS NOT NULL)
	DROP PROCEDURE [dbo].[USP_BorrarDatosTemporalesAportes];
IF (OBJECT_ID('USP_ReiniciarPlanillas') IS NOT NULL)
	DROP PROCEDURE [dbo].[USP_ReiniciarPlanillas];	