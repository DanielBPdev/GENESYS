--REFACTOR PILA MAYO 2020 INICIO
IF (OBJECT_ID('USP_ConsultarInfoAportanteProcesar') IS NOT NULL)
	DROP PROCEDURE [dbo].[USP_ConsultarInfoAportanteProcesar];
IF (OBJECT_ID('USP_ConsultarInfoCotizantePorCrear') IS NOT NULL)
	DROP PROCEDURE [dbo].[USP_ConsultarInfoCotizantePorCrear];	
IF (OBJECT_ID('USP_ConsultarInfoCotizantePorCrear_B7') IS NOT NULL)
	DROP PROCEDURE [dbo].[USP_ConsultarInfoCotizantePorCrear_B7];	