CREATE OR ALTER PROCEDURE [sap].[sp_UpdatePersonaInfoSAP] (
	@codigosap AS varchar(10),  
	@tipodoc AS VARCHAR(2),
	@numerodoc AS VARCHAR(16),
	@nombre1 AS VARCHAR(35),
	@nombre2 AS VARCHAR(35),
	@apellido1 AS VARCHAR(35),
	@apellido2 AS VARCHAR(35),
	@direcc AS VARCHAR(60) = '',	
	@ciudad AS VARCHAR(05) = '',
	@region AS VARCHAR(3) = '',
	@telefono AS VARCHAR(10) = '',
	@celular AS VARCHAR(10) = '',
	@email AS VARCHAR(241) = '',
	@operacion AS VARCHAR(1) = ''
)
AS
BEGIN
	SET @codigosap = CAST(@codigosap AS BIGINT);
	INSERT INTO SAP.Personas_E2G (fecing, horaing, direccionresidencia, municipioresidencia, departamentoresidencia,
	            telefonoresidencia, celular, correoelectronico, codigosap, nombre1, nombre2, apellido1, apellido2,
				tipoi, nroiden, operacion, estado)
	VALUES ([SAP].GetLocalDate(), [SAP].GetLocalDate(), @direcc, @ciudad, @region, @telefono, @celular, @email, @codigosap, @nombre1,
	            @nombre2, @apellido1, @apellido2, @tipodoc, @numerodoc, @operacion, 'P');				
END;