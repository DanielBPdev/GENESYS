-- ====================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: MAYO 16 DE 2022
-- Description: Llama Los procedimientos de integracion para la integracion ERP-Genesys.
-- Es llamado desde tarea programada que se ejecuta cada 4 minutos.
--Script Modification Date: 11/08/2023 8:34:37 a. m.
-- =====================================================================================================
CREATE OR ALTER PROCEDURE [sap].[USP_EjecutarIntegracionCliente_G2E] AS

BEGIN

SET NOCOUNT ON;
BEGIN TRANSACTION

		----- insercion en tabla de log para ejecucion del procedimiento. 
	DECLARE @idlog INT, @fechahorainicio DATETIME, @fechahorafinal DATETIME --Agregado por Yesika Bernal
	INSERT INTO sap.IC_LogEjecucion ([Integracion],[fechahorainicio],[fechahorafinal],[RegistrosEnviados]) VALUES ('Clientes G2E',dbo.GetLocalDate(),null, '0' )
	SET  @idlog = SCOPE_IDENTITY()
	SELECT @fechahorainicio = dbo.GetLocalDate()

	IF (SELECT COUNT(*) FROM sap.ejecucion_ic_g2e with(nolock)) <= 0 
		BEGIN
			INSERT sap.ejecucion_ic_g2e
			SELECT 1

		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- GENESYS a ERP :INICIO DE LLAMADO AL PROCEDIMIENTO QUE ACTUALIZA LAS EMPRESAS, RELACIONES Y CONTACTOS
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	 	EXEC [sap].[USP_GetPersonas_Insert]; -- 2023-08-02 Se optimiza ejecucion integra 1 registro por 1.6 seg  -- Keyner Vides

		--select GETDATE() -'05:00:00' -1,'Ejecutando Empresas'
		EXEC [sap].[USP_GetEmpresas_Insert]; --"Revisado por Marlon Valbuena Esta optimo 1 segundo por empresa masivo"

		--select GETDATE() -'05:00:00' -1,'Ejecutando Relaciones'
	 	EXEC [sap].[USP_Relaciones];  -- "Revisado por Marlon Valbuena Esta optimo corre en 10 segundos masivo"

		--select GETDATE() -'05:00:00' -1,'Ejecutando Contactos'
	 	EXEC [sap].[USP_Contactos_G2E] '', '';  --"Revisado por Marlon Valbuena esta optimo corre en 30 segundos masivo"

		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- FIN DE LLAMADO AL PROCEDIMIENTO QUE ACTUALIZA LAS EMPRESAS, RELACIONES Y CONTACTOS
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		set @fechahorafinal = dbo.GetLocalDate()
		---- actualizacion fecha en tabla de log

		UPDATE sap.IC_LogEjecucion SET fechahorafinal = @fechahorafinal
		WHERE id = @idlog
	
		-- Actualizando campo de tabla ejecucion sumando los registros insertados en la tabla temporal, agregado por Yesika Bernal
		UPDATE sap.IC_LogEjecucion SET RegistrosEnviados =(SELECT SUM(TotalRegistros)
															FROM (
																SELECT COUNT(*) AS TotalRegistros FROM sap.Personas_G2E WHERE FechaEjecucion >=@fechahorainicio AND FechaEjecucion <=@fechahorafinal	
																UNION ALL
																SELECT COUNT(*) FROM sap.Empresas_G2E WHERE FechaEjecucion >=@fechahorainicio AND FechaEjecucion <=@fechahorafinal	
																UNION ALL
																SELECT COUNT(*) FROM sap.Contactos_G2E WHERE FechaEjecucion >=@fechahorainicio AND FechaEjecucion <=@fechahorafinal	
																UNION ALL
																SELECT COUNT(*) FROM sap.Relaciones_G2E WHERE FechaEjecucion >=@fechahorainicio AND FechaEjecucion <=@fechahorafinal	
															) AS Subquery)
		WHERE id = @idlog
		
			DROP TABLE IF EXISTS  SAP.IC_registros 
       
		SELECT 'ok ejec',dbo.GetLocalDate()

		DELETE FROM sap.ejecucion_ic_g2e 
		END

	ELSE BEGIN
		SELECT 'El proceso ya se encuentra en ejecucion'
	END
	
COMMIT TRANSACTION

END;