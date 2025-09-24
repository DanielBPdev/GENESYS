CREATE OR ALTER PROCEDURE [sap].[sp_GetTopAcreedores]
AS
BEGIN
	-- Definir los registros a tomar
	SELECT TOP (SELECT a.nroRegEnvio 
	            FROM   SAP.ConfigIntegraciones a
				WHERE  a.codigo = 'ACREEDORES_G2E') c.consecutivo 
	INTO   #TempConsecutivosAcreedores_G2E
	FROM   SAP.Acreedores c
	WHERE  c.estadoReg = 'P' 
	ORDER BY c.consecutivo;
	
	-- Seleccionar la informacion a enviar
	SELECT a.consecutivo, a.procesoOrigen, a.sociedad, a.grupoCuenta, a.tratamiento, a.nroDocumento, a.tipoDocumento, a.nombre1, a.nombre2, 
	       a.nombre3, a.nombre4, a.direccion, a.municipio, a.pais, a.departamento, a.telefono, a.celular, a.fax, a.email, a.email2, a.claseImpuesto, 
	       a.personaFisica, a.clavePaisBanco, a.claveBanco, a.nroCuentaBancaria, a.titularCuenta, a.claveControlBanco, a.tipoBancoInter, 
	       a.referenciaBancoCuenta, a.cuentaAsociada, a.claveClasificacion, a.grupoTesoreria, a.nroPersonal, a.condicionPago, 
		   a.grupoTolerancia, a.verificacionFacturaDoble, a.viasPago, a.extractoCuenta, a.actividadEconomica, a.paisRetencion		  
	FROM   SAP.Acreedores a
	WHERE  a.consecutivo IN (SELECT c.consecutivo
	                         FROM   #TempConsecutivosAcreedores_G2E c);
	
	-- Actualizar a Enviado (V) de los registros tomados
	UPDATE SAP.Acreedores SET estadoReg = 'V'
	WHERE  consecutivo IN (SELECT c.consecutivo
	                       FROM   #TempConsecutivosAcreedores_G2E c);
END;