CREATE OR ALTER PROCEDURE [sap].[USP_Contactos_G2E] @tipoid VARCHAR(20) = NULL , @Nroid VARCHAR(16) = NULL  AS

 BEGIN TRY

 IF @tipoid  = '' AND @Nroid  = ''
 BEGIN 
 ---select 'para pruebas de duplicidad'
		EXECUTE  sap.USP_INSERT_Contactos_G2E
		EXECUTE  sap.USP_UPDATE_Contactos_G2E
 END
 ELSE
		EXECUTE sap.USP_SELECT_Contactos_G2E @tipoid , @Nroid 
		--      EXEC Sap.USP_SELECT_Contactos_G2E 'CEDULA_CIUDADANIA', '79792414'
		--		EXEC Sap.USP_SELECT_Contactos_G2E 'CEDULA_CIUDADANIA', '98545567'
	    --      EXEC Sap.USP_SELECT_Contactos_G2E 'CEDULA_CIUDADANIA', '30645638'
		--      EXEC Sap.USP_SELECT_Contactos_G2E 'CEDULA_CIUDADANIA', '21571417'
		--      EXEC Sap.USP_SELECT_Contactos_G2E 'CEDULA_CIUDADANIA', '16651983'
	      
 END TRY  

BEGIN CATCH
		SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
    ,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
    ,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage;  
 
END CATCH