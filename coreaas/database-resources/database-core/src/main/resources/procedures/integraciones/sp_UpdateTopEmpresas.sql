CREATE OR ALTER PROCEDURE [sap].[sp_UpdateTopEmpresas]
AS
BEGIN
	UPDATE SAP.Empresas_G2E SET estadoreg = 'V'
	WHERE  consecutivo IN (SELECT TOP 100 a.consecutivo 
	                       FROM   SAP.Empresas_G2E a
						   WHERE  a.estadoreg = 'P' 
						   AND   ([SAP].GetLocalDate() - CAST(CONVERT(VARCHAR(10), fecing) +' '+ CONVERT(VARCHAR(11), horaing) AS DATETIME)) >= '00:00:30'
						   ORDER BY consecutivo);
END;