CREATE OR ALTER PROCEDURE [sap].[sp_CambioCodigoSAP] (
	@e_codigoGenesys AS BIGINT,
	@e_codigoSAP AS VARCHAR(10),
	@e_tipocliente AS VARCHAR(01)
)
AS
BEGIN
	DECLARE @Existe AS VARCHAR(01),
	        @codigoGenesysTemp AS BIGINT,
			@error AS BIT,
			@mensaje AS VARCHAR(100)

	SET @error = 0;
	SET @mensaje = '';

	SET @e_codigoSAP = TRIM(@e_codigoSAP);
	IF @e_codigoSAP IS NULL OR @e_codigoSAP = '' 
	BEGIN
		-- Se debe borrar la homologacion que tiene actualmente el codigoGenesys
		DELETE FROM sap.CodSAPGenesysDeudor
		WHERE  codigoGenesys = @e_codigoGenesys
		AND    tipo = @e_tipocliente;
	END
	ELSE
	BEGIN
		-- Quitar ceros a la izquierda
		SET @e_codigosap = CAST(@e_codigosap AS BIGINT)

		-- Validar si @e_codigoSAP no se encuentra asignado para otro cliente
		SET @codigoGenesysTemp = NULL;
		SELECT @codigoGenesysTemp = a.codigoGenesys
		FROM   sap.CodSAPGenesysDeudor a WITH (NOLOCK)
		WHERE  a.codigoSAP = @e_codigoSAP
		AND    a.tipo = @e_tipocliente;

		IF @codigoGenesysTemp IS NULL -- Codigo SAP no se encuentra asignado 
		BEGIN
			-- Viene el codigo SAP, se debe validar si existe el codigoGenesys para modificarlo o sino para insertarlo		
			SET @Existe = '';
			SELECT @Existe = 'X'
			FROM   sap.CodSAPGenesysDeudor a WITH (NOLOCK)
			WHERE  a.codigoGenesys = @e_codigoGenesys
			AND    a.tipo = @e_tipocliente;
			IF @Existe = 'X' 
			BEGIN
				UPDATE sap.CodSAPGenesysDeudor SET codigoSAP = @e_codigoSAP
				WHERE  codigoGenesys = @e_codigoGenesys
				AND    tipo = @e_tipocliente;
			END
			ELSE
			BEGIN
				INSERT INTO sap.CodSAPGenesysDeudor (codigoGenesys, codigoSAP, tipo) 
				VALUES (@e_codigoGenesys, @e_codigoSAP, @e_tipocliente);
			END 
		END
		ELSE
		BEGIN
			IF @e_codigoGenesys <> @codigoGenesysTemp 
			BEGIN
				SET @error = 1;
				SET @mensaje = 'Codigo SAP existe para otro cliente. No se ha realizado el cambio';
			END
		END
	END
	SELECT @error, @mensaje
END;