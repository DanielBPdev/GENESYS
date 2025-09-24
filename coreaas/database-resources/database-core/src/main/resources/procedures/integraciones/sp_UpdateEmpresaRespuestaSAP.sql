-- RESPUESTA DEL ERP EMPRESAS -- 
CREATE OR ALTER PROCEDURE [sap].[sp_UpdateEmpresaRespuestaSAP] (
	@consecutivo AS INT,
	@codigosap AS VARCHAR(10),
	@estadoreg AS VARCHAR(1),
	@observacion AS VARCHAR(2000)
)
AS
BEGIN
	DECLARE @CodigoGenesys BIGINT,
	        @NroIntentos INT, 
			@FecProceso DATE,
			@HoraProceso TIME
	
	SET @codigosap = ISNULL(@codigosap, '')
	IF @codigosap <> '' -- Quitar ceros a la izquierda		
		SET @codigosap = CAST(@codigosap AS BIGINT) 
	
	-- Consultar el codigo de Genesys segun el consecutivo enviado
	SELECT @CodigoGenesys = p.codigoGenesys, @NroIntentos = p.nrointentos
	FROM   SAP.Empresas_G2E p WITH (NOLOCK)
	WHERE  p.consecutivo = @consecutivo;
	
	IF @estadoreg = 'S'
		EXEC SAP.sp_HomologarCodSAPGenesysDeudor 'E', @CodigoGenesys, @codigosap, @estado = @estadoreg OUTPUT, @obs = @observacion OUTPUT;		
	
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
		
	UPDATE SAP.Empresas_G2E SET fecproceso = @FecProceso, 
								horaproceso = @HoraProceso, 
								codigosap = @codigosap,
								estadoreg = @estadoreg, 
								nrointentos = @NroIntentos,
								observacion = @observacion
	WHERE  consecutivo = @consecutivo
	AND    estadoreg = 'V';
	/*
	IF @estadoreg = 'E' AND (@observacion LIKE '%La cuenta%siendo tratada%usuario%' OR @observacion LIKE '%Cuenta bloqueada por%')
		IF @NroIntentos < 5 OR @NroIntentos IS NULL
			UPDATE SAP.Empresas_G2E SET estadoreg = 'P', 
										fecproceso = NULL, 
										horaproceso = NULL,
										nrointentos = ISNULL(nrointentos, 0) + 1, 
										observacion = ''
			WHERE  (nrointentos < 5 OR nrointentos IS NULL)
			AND    consecutivo = @consecutivo;	
		ELSE
			UPDATE SAP.Empresas_G2E SET fecproceso = [SAP].GetLocalDate(), 
										horaproceso = [SAP].GetLocalDate(), 
										codigosap = @codigosap,
										estadoreg = @estadoreg, 
										observacion = @observacion
			WHERE  consecutivo = @consecutivo;
	ELSE
		UPDATE SAP.Empresas_G2E SET fecproceso = [SAP].GetLocalDate(), 
									horaproceso = [SAP].GetLocalDate(), 
									codigosap = @codigosap,
									estadoreg = @estadoreg, 
									observacion = @observacion
		WHERE  consecutivo = @consecutivo;
	*/
END;