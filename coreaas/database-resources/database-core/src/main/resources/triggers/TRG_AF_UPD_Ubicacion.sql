-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18
-- Description:
-- =============================================
CREATE TRIGGER TRG_AF_UPD_Ubicacion
ON Ubicacion
AFTER UPDATE AS
BEGIN
	SET NOCOUNT ON;
	IF UPDATE (ubiTelefonoFijo) OR UPDATE(ubiTelefonoCelular) OR UPDATE(ubiEmail)
	BEGIN
		INSERT HistoricoUbicacion (hubTelefonoFijo,hubTelefonoCelular,hubEmail,hubPersona)
		SELECT ubi.ubiTelefonoFijo,ubi.ubiTelefonoCelular,ubi.ubiEmail,per.perId
		FROM INSERTED ubi
		INNER JOIN Persona per ON per.perUbicacionPrincipal = ubi.ubiId
	END
END
