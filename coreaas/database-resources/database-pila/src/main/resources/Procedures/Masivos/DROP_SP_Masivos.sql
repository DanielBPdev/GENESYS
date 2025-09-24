--Drop Store Procedures of Aportes Masivos	
IF (OBJECT_ID('ASP_Execute_pila2FaseDos') IS NOT NULL)
	DROP PROCEDURE [masivos].[ASP_Execute_pila2FaseDos];
IF (OBJECT_ID('ASP_Execute_pila2FaseInicial') IS NOT NULL)
	DROP PROCEDURE [masivos].[ASP_Execute_pila2FaseInicial];
IF (OBJECT_ID('ASP_Execute_pilaProcesarAporte') IS NOT NULL)
	DROP PROCEDURE [masivos].[ASP_Execute_pilaProcesarAporte];
IF (OBJECT_ID('ASP_Execute_Validacion_Inicial') IS NOT NULL)
	DROP PROCEDURE [masivos].[ASP_Execute_Validacion_Inicial];
IF (OBJECT_ID('ASP_ExecutePILA2Fase2RegistrarRelacionarAportes') IS NOT NULL)
	DROP PROCEDURE [masivos].[ASP_ExecutePILA2Fase2RegistrarRelacionarAportes];
IF (OBJECT_ID('ASP_ExecuteRegistrarRelacionarAportesRegistro') IS NOT NULL)
	DROP PROCEDURE [masivos].[ASP_ExecuteRegistrarRelacionarAportesRegistro];
IF (OBJECT_ID('crearPersonas') IS NOT NULL)
	DROP PROCEDURE [masivos].[crearPersonas];