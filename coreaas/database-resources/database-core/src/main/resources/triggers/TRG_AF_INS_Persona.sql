-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE TRIGGER TRG_AF_INS_Persona
ON Persona
AFTER INSERT AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @perId BIGINT  
 
   	SELECT @perId = perId FROM INSERTED	

    DECLARE @tablaVacia AS TablaIdType;  
    EXEC USP_REP_INS_EstadoAfiliacionPersonaCaja @perId, @tablaVacia

    INSERT HistoricoUbicacion (hubTelefonoFijo,hubTelefonoCelular,hubEmail,hubPersona)
	SELECT ubi.ubiTelefonoFijo,ubi.ubiTelefonoCelular,ubi.ubiEmail,per.perId
	FROM INSERTED per
	INNER JOIN Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
END
