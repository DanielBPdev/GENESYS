------------ RESUMEN DE INTEGRACIONES ----------------
------------------------------------------------------
CREATE OR ALTER PROCEDURE [sap].[sp_UpdatePI]
@fecini date,
@fecfin date
AS
BEGIN
	SELECT DISTINCT 1 as orden, 'PERSONAS' AS tipo, 'GENESYS - ERP' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.Personas_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.Personas_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.Personas_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.Personas_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'E') AS Error
	FROM   SAP.Personas_G2E a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin
	UNION ALL
	SELECT DISTINCT 2 as orden, 'EMPRESAS' AS tipo, 'GENESYS - ERP' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.Empresas_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.Empresas_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.Empresas_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.Empresas_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'E') AS Error
	FROM   SAP.Empresas_G2E a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin
	UNION ALL
	SELECT DISTINCT 3 as orden, 'CONTACTOS' AS tipo, 'GENESYS - ERP' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.Contactos_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.Contactos_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.Contactos_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.Contactos_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'E') AS Error
	FROM   SAP.Contactos_G2E a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin
	UNION ALL
	SELECT DISTINCT 4 as orden, 'RELACIONES' AS tipo, 'GENESYS - ERP' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.Relaciones_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.Relaciones_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.Relaciones_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.Relaciones_G2E b WHERE b.fecing = a.fecing AND b.estadoreg = 'E') AS Error
	FROM   SAP.Relaciones_G2E a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin
	UNION ALL
	SELECT DISTINCT 5 as orden, 'PERSONAS' AS tipo, 'ERP - GENESYS' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.Personas_E2G b WHERE b.fecing = a.fecing AND b.estado = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.Personas_E2G b WHERE b.fecing = a.fecing AND b.estado = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.Personas_E2G b WHERE b.fecing = a.fecing AND b.estado = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.Personas_E2G b WHERE b.fecing = a.fecing AND b.estado = 'E') AS Error
	FROM   SAP.Personas_E2G a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin
	UNION ALL
	SELECT DISTINCT 6 as orden, 'EMPRESAS' AS tipo, 'ERP - GENESYS' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.Empresas_E2G b WHERE b.fecing = a.fecing AND b.estadoreg = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.Empresas_E2G b WHERE b.fecing = a.fecing AND b.estadoreg = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.Empresas_E2G b WHERE b.fecing = a.fecing AND b.estadoreg = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.Empresas_E2G b WHERE b.fecing = a.fecing AND b.estadoreg = 'E') AS Error
	FROM   SAP.Empresas_E2G a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin
	UNION ALL
	SELECT DISTINCT 7 as orden, 'CONTACTOS' AS tipo, 'ERP - GENESYS' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.Contactos_E2G b WHERE b.fecing = a.fecing AND b.estadoreg = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.Contactos_E2G b WHERE b.fecing = a.fecing AND b.estadoreg = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.Contactos_E2G b WHERE b.fecing = a.fecing AND b.estadoreg = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.Contactos_E2G b WHERE b.fecing = a.fecing AND b.estadoreg = 'E') AS Error
	FROM   SAP.Contactos_E2G a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin	
	UNION ALL
	SELECT DISTINCT 8 as orden, 'APORTES' AS tipo, 'GENESYS - ERP' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.IC_Aportes_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.IC_Aportes_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.IC_Aportes_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.IC_Aportes_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'E') AS Error
	FROM   SAP.IC_Aportes_Enc a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin	
	UNION ALL
	SELECT DISTINCT 9 as orden, 'FOVIS' AS tipo, 'GENESYS - ERP' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.IC_FOVIS_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.IC_FOVIS_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.IC_FOVIS_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.IC_FOVIS_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'E') AS Error
	FROM   SAP.IC_FOVIS_Enc a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin	
	UNION ALL
	SELECT DISTINCT 10 as orden, 'CUOTA MONETARIA' AS tipo, 'GENESYS - ERP' AS direccion, a.fecing, 
		   (SELECT COUNT(*) FROM SAP.IC_CM_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'P') AS Pendiente,
		   (SELECT COUNT(*) FROM SAP.IC_CM_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'V') AS Enviado,
		   (SELECT COUNT(*) FROM SAP.IC_CM_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'S') AS Satisfactorio,
		   (SELECT COUNT(*) FROM SAP.IC_CM_Enc b WHERE b.fecing = a.fecing AND b.estadoreg = 'E') AS Error
	FROM   SAP.IC_CM_Enc a
	WHERE  a.fecing BETWEEN @fecini AND @fecfin	
	ORDER BY orden, fecing
END;