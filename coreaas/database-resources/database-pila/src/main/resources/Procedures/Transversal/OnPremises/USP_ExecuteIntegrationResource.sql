-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/10/13
-- Description:	Procedimiento almacenado encargado  de cargar las tablas del esquema Staging  
-- por Id de Transaccion)
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteIntegrationResource]
       @IdTransaccion Bigint
AS


DECLARE @ErrorMessage NVARCHAR(4000);
DECLARE @ErrorSeverity INT;
DECLARE @ErrorState INT;

BEGIN TRY
	
	DECLARE @ReturnCode INT,
			@Msg VARCHAR(1000),
			@sDTExec VARCHAR(500) = 'DTEXEC /File "C:\SSIS_Packages\PILA2\' + SUSER_SNAME() + '\ExecuteBloqueStaging.dtsx"  /SET \Package.Variables[User::IdTransaccion].Properties[Value];'+ CAST (@IdTransaccion AS VARCHAR(12))

	DECLARE @Result AS TABLE ([Output] VARCHAR(MAX))

	INSERT INTO @Result
	exec @ReturnCode = xp_cmdshell @sDTExec

	print @ReturnCode

	IF @ReturnCode <> 0
	BEGIN
		

		SELECT @ErrorMessage = 'SSIS package execution failed for C:\SSIS_Packages\PILA2\' + SUSER_SNAME() + '\ExecuteBloqueStaging.dtsx',
			   @ErrorSeverity = 16,
			   @ErrorState = 1;

		RAISERROR (@ErrorMessage, -- Message text.
				   @ErrorSeverity, -- Severity.
				   @ErrorState -- State.
				   );
	END

	-- sí la ETL ejecuta correctamente, se incova el cálculo de días de novedad
	EXEC USP_CalcularDiasNovedadesBD @IdTransaccion

END TRY
BEGIN CATCH

    SELECT @ErrorMessage = '[dbo].[USP_ExecuteIntegrationResource]|' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

    RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );  
    
END CATCH
;