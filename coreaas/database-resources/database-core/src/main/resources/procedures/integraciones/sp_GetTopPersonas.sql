CREATE OR ALTER PROCEDURE [sap].[sp_GetTopPersonas]
AS
BEGIN	
DECLARE @cantidadReg BIGINT, @nroRegEnvio SMALLINT
	SELECT @nroRegEnvio = a.nroregenvio
    FROM   SAP.ConfigIntegraciones a WITH (NOLOCK)
	WHERE  a.codigo = 'PERSONAS_G2E';
	
	-- Definir los registros a tomar
	SELECT TOP (@nroRegEnvio) c.consecutivo 
	INTO   #TempConsecutivosPersonas_G2E
	FROM   SAP.Personas_G2E c
	WHERE  c.estadoreg = 'P' 
	ORDER BY c.consecutivo;		
			
	SELECT @cantidadReg = COUNT(*)
	FROM   #TempConsecutivosPersonas_G2E WITH (NOLOCK);
	
	IF @cantidadReg > 0
	BEGIN			
		-- Actualizar a Enviado (V) de los registros tomados			
		SET NOCOUNT ON;
		UPDATE SAP.Personas_G2E SET estadoreg = 'V'
		WHERE  consecutivo IN (SELECT c.consecutivo FROM #TempConsecutivosPersonas_G2E c);
		
		-- Seleccionar la informacion a enviar
		SELECT a.consecutivo, operacion, codigosap, tipodoc, numerodoc, nombre1, nombre2, apellido1, apellido2, 
			   FORMAT(CAST(fecnac AS DATETIME),'yyyyMMdd') AS fecnac, estcivil, direcc, ciudad, region, 
			   telefono, celular, email, tipoafil, afiliadoa, categoria, estado, sexo, autenvio, discapacidad, 
			   FORMAT(fecafil,'yyyyMMdd') AS fecafil, FORMAT(fecretiro,'yyyyMMdd') AS fecretiro, 
			   FORMAT(fecformulario,'yyyyMMdd') AS fecformulario, FORMAT(fecexped,'yyyyMMdd') AS fecexped, 
			   FORMAT(fecingreso,'yyyyMMdd') AS fecingreso, gradoesc, grupofam, indsub, niveledu, ccostos, 
			   ocupacion, porcdisca, profesion, rangosalario, sectorresidencia, tipocontrato, tipoSalario,
			   tipopersona, codpostal, nomempresa, nitempresa
		FROM   SAP.Personas_G2E a WITH (NOLOCK)
		WHERE  a.consecutivo IN (SELECT c.consecutivo FROM #TempConsecutivosPersonas_G2E c WITH (NOLOCK));
	END
	DROP TABLE IF EXISTS #TempConsecutivosPersonas_G2E;
END;