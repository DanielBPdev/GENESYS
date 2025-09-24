-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE TRIGGER TRG_AF_UPD_Persona
ON Persona
AFTER UPDATE AS
BEGIN
	SET NOCOUNT ON;

	IF UPDATE (perUbicacionPrincipal)
	BEGIN
    	INSERT HistoricoUbicacion (hubTelefonoFijo,hubTelefonoCelular,hubEmail,hubPersona)
		SELECT ubi.ubiTelefonoFijo,ubi.ubiTelefonoCelular,ubi.ubiEmail,per.perId
		FROM INSERTED per
		INNER JOIN Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
	END
END
