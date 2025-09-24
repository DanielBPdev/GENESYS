-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE TRIGGER TRG_AF_INS_Ubicacion
ON Ubicacion
AFTER INSERT AS
BEGIN
	SET NOCOUNT ON;
	INSERT HistoricoUbicacion (hubTelefonoFijo,hubTelefonoCelular,hubEmail,hubPersona)
	SELECT ubi.ubiTelefonoFijo,ubi.ubiTelefonoCelular,ubi.ubiEmail,per.perId
	FROM INSERTED ubi
	INNER JOIN Persona per ON per.perUbicacionPrincipal = ubi.ubiId
END
