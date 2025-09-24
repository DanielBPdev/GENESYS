CREATE OR ALTER PROCEDURE [sap].[sp_GetTopContactos]
AS
BEGIN
	-- Definir los registros a tomar
	SELECT TOP (SELECT a.nroregenvio 
	            FROM   SAP.ConfigIntegraciones a
				WHERE  a.codigo = 'CONTACTOS_G2E') c.consecutivo 
	INTO   #TempConsecutivosContactos_G2E
	FROM   SAP.Contactos_G2E c 
	INNER JOIN SAP.CodSAPGenesysDeudor d ON d.codigoGenesys = c.CodigoGenesys
	WHERE  c.estadoreg = 'P' 
	AND    d.tipo = 'E'
	ORDER BY c.consecutivo;
	
	-- Seleccionar la informacion a enviar
	SELECT a.consecutivo, a.operacion, d.codigosap, a.tipodoc, a.numerodoc, a.nombrecompleto, a.nombrepila, a.tipocontacto,
	       a.genero, FORMAT(CAST(a.fecnac AS DATETIME),'yyyyMMdd') AS fecnac, a.estadocivil, a.telefono, a.celular, a.email, 
		   a.direcc, a.ciudad, a.dpto, a.autenvio
	FROM   SAP.Contactos_G2E a WITH (NOLOCK)
	INNER JOIN SAP.CodSAPGenesysDeudor d ON d.codigoGenesys = a.CodigoGenesys
	WHERE  a.consecutivo IN (SELECT c.consecutivo
	                         FROM   #TempConsecutivosContactos_G2E c WITH (NOLOCK))
	AND    d.tipo = 'E';

	-- Actualizar a Enviado (V) de los registros tomados
	UPDATE SAP.Contactos_G2E SET estadoreg = 'V'
	WHERE  consecutivo IN (SELECT c.consecutivo FROM #TempConsecutivosContactos_G2E c);	
	
	DROP TABLE IF EXISTS #TempConsecutivosContactos_G2E;
END;