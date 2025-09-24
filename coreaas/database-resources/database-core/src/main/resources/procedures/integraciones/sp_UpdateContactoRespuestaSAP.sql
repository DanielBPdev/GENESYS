-- RESPUESTA DEL ERP CONTACTOS -- 
CREATE OR ALTER PROCEDURE [sap].[sp_UpdateContactoRespuestaSAP] (
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
	FROM   SAP.Contactos_G2E p WITH (NOLOCK)
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
	
	UPDATE SAP.Contactos_G2E SET fecproceso = @FecProceso, 
	                             horaproceso = @HoraProceso,
	                             estadoreg = @estadoreg, 
								 observacion = @observacion,
								 nrointentos = @NroIntentos,
								 codigosap = (SELECT a.codigosap 
								              FROM   SAP.CodSAPGenesysDeudor a 
											  INNER JOIN SAP.Contactos_G2E b ON a.codigoGenesys = b.CodigoGenesys
											  WHERE   b.consecutivo = @consecutivo
											  AND     a.tipo = 'E')
	WHERE  consecutivo = @consecutivo
	AND    estadoreg = 'V';
	
	/*
	IF @estadoreg = 'E' AND (@observacion LIKE '%La cuenta%siendo tratada%usuario%' OR @observacion LIKE '%Cuenta bloqueada por%')
		UPDATE SAP.Contactos_G2E SET estadoreg = 'P', 
		                            fecproceso = NULL, 
									horaproceso = NULL,
									nrointentos = ISNULL(nrointentos, 0) + 1, 
									observacion = ''
		WHERE  (nrointentos < 5 OR nrointentos IS NULL)
		AND    consecutivo = @consecutivo;	
	ELSE
		UPDATE SAP.Contactos_G2E SET fecproceso = [SAP].GetLocalDate(), 
	                             horaproceso = [SAP].GetLocalDate(),
	                             estadoreg = @estadoreg, 
								 observacion = @observacion,
								 codigosap = (SELECT a.codigosap 
								              FROM   SAP.CodSAPGenesysDeudor a WITH (NOLOCK)
											  INNER JOIN SAP.Contactos_G2E b WITH (NOLOCK) ON a.codigoGenesys = b.CodigoGenesys
											  WHERE   b.consecutivo = @consecutivo
											  AND     a.tipo = 'E')
		WHERE  consecutivo = @consecutivo;		
		*/
END;