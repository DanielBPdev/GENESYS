CREATE OR ALTER PROCEDURE [sap].[sp_UpdateICAportesRespuestaSAP] (
	@consecutivo AS VARCHAR(10),
	@estadoReg AS VARCHAR (1),
	@observacion AS VARCHAR (2000),
	@documentoContable AS VARCHAR(10),
	@sociedad AS VARCHAR(04),
	@ejercicio AS VARCHAR(04)
)
AS
BEGIN 
	UPDATE SAP.IC_Aportes_Enc 
	SET    fecProceso = [SAP].GetLocalDate(), 
	       horaProceso = [SAP].GetLocalDate(), 		
	       estadoReg = @estadoReg, 
		   observacion = @observacion,
		   documentoContable = @documentoContable,
		   ejercicio = @ejercicio		   
	WHERE  consecutivo = @consecutivo;
END;