CREATE OR ALTER PROCEDURE [sap].[sp_UpdateTopAcreedores]
AS
BEGIN
	UPDATE SAP.Acreedores SET estadoreg = 'V'	
	WHERE consecutivo IN (SELECT TOP 100 a.consecutivo 
	                       FROM   SAP.Acreedores a
						   WHERE  a.estadoreg = 'P' 
						   AND   ([SAP].GetLocalDate() - CAST(CONVERT(VARCHAR(10), fecing) +' '+ CONVERT(VARCHAR(11), horaing) AS DATETIME)) >= '00:00:30'
						   ORDER BY consecutivo);
END;