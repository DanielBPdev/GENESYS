-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE TRIGGER TRG_AF_UPD_Solicitud
ON Solicitud
AFTER UPDATE AS
BEGIN
	SET NOCOUNT ON;	
    DECLARE @afiId BIGINT
    DECLARE @solId BIGINT   

    IF UPDATE (solClasificacion)
    BEGIN
    	SELECT @solId = solId
    	FROM INSERTED

        -- CATEGORIA AFILIADO **********************************************************************************    

    	SELECT @afiId = roa.roaAfiliado
        FROM RolAfiliado roa
        INNER JOIN SolicitudAfiliacionPersona sap ON sap.sapRolAfiliado = roa.roaId         
        WHERE sap.sapSolicitudGlobal = @solId
    END 
END
