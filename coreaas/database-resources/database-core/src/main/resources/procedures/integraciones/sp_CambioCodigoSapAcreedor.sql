CREATE OR ALTER PROCEDURE [sap].[sp_CambioCodigoSapAcreedor] (
@e_codigoGenesys AS BIGINT,
@e_codigoSAP AS VARCHAR(10)
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
		DELETE FROM sap.CodSAPGenesysAcreedor
		WHERE  codigoGenesys = @e_codigoGenesys;
	END
	ELSE
	BEGIN
		-- Quitar ceros a la izquierda
		SET @e_codigosap = CAST(@e_codigosap AS BIGINT)

		-- Validar si @e_codigoSAP no se encuentra asignado para otro acreedor es decir otro codigoGenesys
		SET @codigoGenesysTemp = NULL;
		SELECT @codigoGenesysTemp = a.codigoGenesys
		FROM   sap.CodSAPGenesysAcreedor a WITH (NOLOCK)
		WHERE  a.codigoSAP = @e_codigoSAP;

		IF @codigoGenesysTemp IS NULL -- Codigo SAP no se encuentra asignado
		BEGIN
			-- Viene el codigo SAP, se debe validar si existe el codigoGenesys para modificarlo o sino para insertarlo
			SET @Existe = '';

			SELECT @Existe = 'X'
			FROM   sap.CodSAPGenesysAcreedor a WITH (NOLOCK)
			WHERE  a.codigoGenesys = @e_codigoGenesys;

			IF @Existe = 'X'
			BEGIN
			    -- se asigna @e_codigoSAP al @e_codigoGenesys asi previamente haya 
				-- tenido otro códigoSap asignado es decir se sobreescribe codigoSap
				UPDATE sap.CodSAPGenesysAcreedor SET codigoSAP = @e_codigoSAP
				WHERE  codigoGenesys = @e_codigoGenesys;
			END
			ELSE
			BEGIN
				INSERT INTO sap.CodSAPGenesysAcreedor 
				VALUES (@e_codigoGenesys, @e_codigoSAP,(SELECT case p.pertipoidentificacion when 'NIT' THEN 'E' ELSE 'P' END AS TIPO FROM PERSONA P WHERE perId=@e_codigoGenesys) );
			END
		END
		ELSE
		BEGIN
		    --@e_codigoSAP se encuentra asignado a un @codigoGenesysTemp
			IF @e_codigoGenesys <> @codigoGenesysTemp
			BEGIN
				SET @error = 1;
				SET @mensaje = 'Codigo SAP acreedor ' + @e_codigoSAP + ' existe para otro codigoGenesys ' + @codigoGenesysTemp + ' No se ha realizado el cambio';
			END
		END
END
SELECT @error, @mensaje
END;