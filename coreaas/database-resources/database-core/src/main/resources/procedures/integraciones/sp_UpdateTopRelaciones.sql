CREATE OR ALTER PROCEDURE [sap].[sp_UpdateTopRelaciones]
AS
BEGIN
	UPDATE SAP.Relaciones_G2E SET estadoreg = 'V'
	WHERE  consecutivo IN (SELECT TOP 100 a.consecutivo
						   FROM   SAP.Relaciones_G2E a, SAP.CodSAPGenesysDeudor b, SAP.CodSAPGenesysDeudor c
						   WHERE  a.cliente1 = b.codigoGenesys
						   AND    a.cliente2 = c.codigoGenesys
						   AND    a.estadoreg = 'P' 
						   AND   ([SAP].GetLocalDate() - CAST(CONVERT(VARCHAR(10), fecing) +' '+ CONVERT(VARCHAR(11), horaing) AS DATETIME)) >= '00:00:30'
						   ORDER BY a.consecutivo);
END;