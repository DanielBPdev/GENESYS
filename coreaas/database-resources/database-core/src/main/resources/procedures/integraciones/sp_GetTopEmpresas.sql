CREATE OR ALTER PROCEDURE [sap].[sp_GetTopEmpresas]
AS
BEGIN
	-- Definir los registros a tomar
	SELECT TOP (SELECT a.nroregenvio 
	            FROM   SAP.ConfigIntegraciones a
				WHERE  a.codigo = 'EMPRESAS_G2E') c.consecutivo 
	INTO   #TempConsecutivosEmpresas_G2E
	FROM   SAP.Empresas_G2E c 
	WHERE  c.estadoreg = 'P' 
	ORDER BY c.consecutivo;
	
	-- Seleccionar la informacion a enviar
	SELECT a.consecutivo, operacion, codigosap, tipodoc, numerodoc, digverif, nombreempresa, direcc, 
	       ciudad, region, telefono, celular, email, tipoafil, afiliadoa, tarifaserv, estado, 
		   grupocta, celcorresp, dptocorresp, dircorresp, muncorresp, telcorresp, morosa, nrotrabinsc, 
		   solpazysalvo, tiposector, tipocliente, trasladocaja, vlraporte, autenvio, 
		   FORMAT(fecconstitucion,'yyyyMMdd') AS fecconstitucion, FORMAT(fecafil,'yyyyMMdd') AS fecafil, 
	       FORMAT(fecretiro,'yyyyMMdd') AS fecretiro, FORMAT(fecformulario,'yyyyMMdd') AS fecformulario, 
	       acteco, FORMAT(fecingreso,'yyyyMMdd') AS fecingreso, arl, paginaweb, tipopersona, telefono2, 
		   codpostal, ultimaCCFProcedencia
	FROM   SAP.Empresas_G2E a WITH (NOLOCK)
	WHERE  a.consecutivo IN (SELECT c.consecutivo FROM #TempConsecutivosEmpresas_G2E c WITH (NOLOCK));

	-- Actualizar a Enviado (V) de los registros tomados
	UPDATE SAP.Empresas_G2E SET estadoreg = 'V'
	WHERE  consecutivo IN (SELECT c.consecutivo FROM #TempConsecutivosEmpresas_G2E c);	
	
	DROP TABLE IF EXISTS #TempConsecutivosEmpresas_G2E;	
END;