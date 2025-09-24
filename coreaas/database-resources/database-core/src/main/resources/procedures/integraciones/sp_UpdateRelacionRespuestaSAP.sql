-- RESPUESTA DEL ERP RELACIONES -- 
CREATE OR ALTER PROCEDURE [sap].[sp_UpdateRelacionRespuestaSAP] (
	@consecutivo AS INT,
	@estadoreg AS VARCHAR(1),
	@observacion AS VARCHAR(2000)
)
AS
BEGIN
	DECLARE @NroIntentos INT, 
			@FecProceso DATE,
			@HoraProceso TIME
			
	SELECT @NroIntentos = p.nrointentos
	FROM   SAP.Relaciones_G2E p WITH (NOLOCK)
	WHERE  p.consecutivo = @consecutivo;
	
	SET @FecProceso = [SAP].GetLocalDate()
	SET @HoraProceso = [SAP].GetLocalDate()
	
	IF @estadoreg = 'E' AND (@observacion LIKE '%La cuenta%siendo tratada%usuario%' OR @observacion LIKE '%Cuenta bloqueada por%')
		IF @NroIntentos < 5 OR @NroIntentos IS NULL 
		BEGIN
			SET @estadoreg = 'P'
			SET @FecProceso = NULL
			SET @HoraProceso = NULL
			SET @NroIntentos = @NroIntentos + 1		
			SET @observacion = ''
		END
		
	UPDATE SAP.Relaciones_G2E SET estadoreg = @estadoreg, 
								  fecproceso = @FecProceso, 
								  horaproceso = @HoraProceso,
								  nrointentos = @NroIntentos, 
								  observacion = @observacion
	WHERE  consecutivo = @consecutivo
	AND    estadoreg = 'V';
	/*
	IF @estadoreg = 'E' AND (@observacion LIKE '%La cuenta%siendo tratada%usuario%' OR @observacion LIKE '%Cuenta bloqueada por%')
		UPDATE SAP.Relaciones_G2E SET estadoreg = 'P', 
		                            fecproceso = NULL, 
									horaproceso = NULL,
									nrointentos = ISNULL(nrointentos, 0) + 1, 
									observacion = ''
		WHERE  (nrointentos < 5 OR nrointentos IS NULL)
		AND    consecutivo = @consecutivo;
	ELSE
		UPDATE SAP.Relaciones_G2E SET fecproceso = [SAP].GetLocalDate(), 
									horaproceso = [SAP].GetLocalDate(), 
									estadoreg = @estadoreg, 
									observacion = @observacion
		WHERE  consecutivo = @consecutivo;		
	*/
END;