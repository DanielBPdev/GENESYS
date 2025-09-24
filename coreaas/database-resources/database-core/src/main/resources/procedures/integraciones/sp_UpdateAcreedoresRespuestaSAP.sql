CREATE OR ALTER PROCEDURE [sap].[sp_UpdateAcreedoresRespuestaSAP] (
	@consecutivo AS INT,
	@codigoSap AS VARCHAR(10),
	@estadoReg AS VARCHAR (1),
	@observacion AS VARCHAR (2000)
)
AS
BEGIN
	DECLARE @codigoSapTabla VARCHAR(10),
			@codigoGenesys BIGINT,
			@tipo VARCHAR(1)
			
	--21-11-2021 Cuando la respuesta del ERP SAP es que el acreedor existe, se cambia el estado de 'E' a 'S' y se extrae el codigoSAP
	--del campo observacion.y se actualiza en la tabla de integración de Acreedores
	IF @estadoReg = 'E' AND (@observacion LIKE '%El acreedor con identificación%' AND @observacion LIKE '%ya existe, codigo%')
	BEGIN
	   SET @estadoReg = 'S'
	   --SET @codigoSap = SUBSTRING(@observacion,CHARINDEX('codigo',@observacion)+7, 10 )	   
	END
	-- -------
	SET @codigoSap = ISNULL(@codigoSap, '')
	IF  @codigoSap <> '' -- Quitar ceros a la izquierda		
		SET @codigoSap = CAST(@codigoSap AS BIGINT) 

	IF @estadoReg = 'S'	 
	BEGIN
		-- 21-11-2021 Consultar el codigo de Genesys y tipo en la tabla de integración de acreedores segun el consecutivo enviado en 
		-- la respuesta del ERP SAP de creación del acredor, se agregó la consulta del tipo
		SELECT @codigoGenesys=p.codigoGenesys, @tipo=p.tipo
		FROM   SAP.Acreedores p WITH (NOLOCK)
		WHERE  p.consecutivo = @consecutivo;
		
		EXEC SAP.sp_HomologarCodSAPGenesysAcreedor @tipo, @codigoGenesys, 
		@codigoSap, @estado = @estadoReg OUTPUT, @obs = @observacion OUTPUT;

		IF @estadoReg = 'E' AND (@observacion LIKE '%La cuenta%siendo tratada%usuario%' OR @observacion LIKE '%Cuenta bloqueada por%')
			BEGIN
		    -- se coloca en estado P la tabla de integración de acreedores
			-- a pesar de ya estar creado el acreedor en el ERP SAP pero es debido al error que se dió en la tabla de homologación
			UPDATE SAP.Acreedores SET estadoReg = 'P', 
		                            fecProceso = NULL, 
									horaProceso = NULL,
									nroIntentos = ISNULL(nroIntentos, 0) + 1, 
									observacion = ''
			WHERE  estadoReg = 'E'
			AND    (nroIntentos < 5 OR nroIntentos IS NULL)
			AND    consecutivo = @consecutivo;	
			
			UPDATE SAP.Acreedores SET estadoReg = @estadoReg, 
		                            fecProceso = [SAP].GetLocalDate(), 
									horaProceso = [SAP].GetLocalDate(),
									observacion = @observacion
			WHERE  estadoReg = 'E'
			AND    (nroIntentos >= 5)
			AND    consecutivo = @consecutivo;	
			END
		ELSE		
			UPDATE SAP.Acreedores 
			SET fecProceso = [SAP].GetLocalDate(), 
	            horaProceso = [SAP].GetLocalDate(), 
				codigoSap = @codigoSap,
	       		estadoReg = @estadoReg, 
				observacion = @observacion
			WHERE  consecutivo = @consecutivo;
	END
	ELSE
		UPDATE SAP.Acreedores 
			SET fecProceso = [SAP].GetLocalDate(), 
	            horaProceso = [SAP].GetLocalDate(), 
				codigoSap = @codigoSap,
	       		estadoReg = @estadoReg, 
				observacion = @observacion
			WHERE  consecutivo = @consecutivo;
END;