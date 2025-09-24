CREATE OR ALTER PROCEDURE [sap].[sp_HomologarCodSAPGenesysDeudor] (
	@tipocliente AS VARCHAR(01),
	@CodigoGenesys AS BIGINT,
	@codigosap AS VARCHAR(10),
	@estado AS VARCHAR(01) OUTPUT,
	@obs AS VARCHAR(2000) OUTPUT
)
AS 
BEGIN
	DECLARE @CodigoSAPTabla VARCHAR(10)
	
	IF @CodigoGenesys IS NULL
	BEGIN
		SET @estado = 'E';
		SET @obs = 'Codigo Genesys nulo';
	END
	ELSE
	BEGIN
		SET @estado = 'S';
		SET @obs = 'Operación exitosa';
		
		-- Consultar si el codigo SAP existe en la tabla de homologacion
		SET @CodigoSAPTabla = NULL
		SELECT @CodigoSAPTabla = a.codigosap
		FROM   SAP.CodSAPGenesysDeudor a WITH (NOLOCK)
		WHERE  a.codigoGenesys = @CodigoGenesys
		AND    a.tipo = @tipocliente;

		IF @CodigoSAPTabla IS NULL 
		BEGIN
			-- Grabarlo en la tabla de homologacion
			INSERT INTO SAP.CodSAPGenesysDeudor (codigoGenesys, codigoSAP, tipo) VALUES (@CodigoGenesys, @codigosap, @tipocliente);
		END
		ELSE
		BEGIN
			-- Comparar si tiene el mismo codigo SAP
			IF @CodigoSAPTabla <> @codigosap 
			BEGIN
				SET @estado = 'E';
				SET @obs = 'Codigo SAP retornado por el ERP (' + @codigosap + ') es diferente al que se encuentra homologado en Genesys (' + @CodigoSAPTabla + ')'
			END		
		END
	END
END;