CREATE OR ALTER PROCEDURE [sap].[sp_GetTopRelaciones]
AS
BEGIN
	-- Definir los registros a tomar
	SELECT TOP (SELECT a.nroregenvio 
	            FROM   SAP.ConfigIntegraciones a
				WHERE  a.codigo = 'RELACIONES_G2E') c.consecutivo 
	INTO   #TempConsecutivosRelaciones_G2E
	FROM   SAP.Relaciones_G2E c 
	INNER JOIN SAP.CodSAPGenesysDeudor b ON c.cliente1 = b.codigoGenesys AND c.tipocliente1 = b.tipo
	INNER JOIN SAP.CodSAPGenesysDeudor d ON c.cliente2 = d.codigoGenesys AND c.tipocliente2 = d.tipo
	WHERE  c.estadoreg = 'P' 
	ORDER BY c.consecutivo;
	
	-- Seleccionar la informacion a enviar
	SELECT a.consecutivo, a.operacion, b.codigoSAP as cliente1, a.codrelacion, c.codigoSAP as cliente2
	FROM   SAP.Relaciones_G2E a WITH (NOLOCK)
	INNER JOIN SAP.CodSAPGenesysDeudor b ON a.cliente1 = b.codigoGenesys  AND a.tipocliente1 = b.tipo
	INNER JOIN SAP.CodSAPGenesysDeudor c  ON a.cliente2 = c.codigoGenesys  AND a.tipocliente2 = c.tipo
	WHERE  a.consecutivo IN (SELECT c.consecutivo FROM #TempConsecutivosRelaciones_G2E c WITH (NOLOCK));

	-- Actualizar a Enviado (V) de los registros tomados
	UPDATE SAP.Relaciones_G2E SET estadoreg = 'V'
	WHERE  consecutivo IN (SELECT c.consecutivo FROM #TempConsecutivosRelaciones_G2E c);
	
	DROP TABLE IF EXISTS #TempConsecutivosRelaciones_G2E;
END;