CREATE OR ALTER PROCEDURE [sap].[sp_HomologarCodSAPGenesysAcreedor] (
	@tipo VARCHAR(1),
	@codigoGenesys AS BIGINT,
	@codigoSap AS VARCHAR(10),
	@estado AS VARCHAR(01) OUTPUT,
	@obs AS VARCHAR(2000) OUTPUT
)
AS 
BEGIN
	DECLARE @codigoSapTabla VARCHAR(10)
		
	-- 21-11-2021 Consultar si el codigo SAP existe en la tabla de homologacion asociado al codigoGenesys
	SET @codigoSapTabla = NULL
	SELECT @codigoSapTabla = a.codigoSap
	FROM   sap.CodSAPGenesysAcreedor a WITH (NOLOCK)
	WHERE  a.codigoGenesys = @codigoGenesys
	AND a.tipo = @tipo;

	IF @codigoSapTabla IS NULL 
	BEGIN
		-- Grabarlo en la tabla de homologacion
		SET @estado = 'S';
		SET @obs = concat ( trim(@obs),' - Operación exitosa homologacion acreedor') ;
		INSERT INTO sap.CodSAPGenesysAcreedor (codigoGenesys, codigoSap, tipo ) VALUES (@codigoGenesys, @codigoSap, @tipo );
	END
	ELSE
	BEGIN
		-- Comparar si tiene el mismo codigo SAP
		IF @codigoSapTabla <> @codigoSap 
		BEGIN
			SET @estado = 'E';
			SET @obs = concat ( trim(@obs),' - CodigoSAP retornado por ERP (' + @codigoSap + ') es diferente al que esta homologado en Genesys (' + @CodigoSapTabla + ' )' )
		END		
	END
END;