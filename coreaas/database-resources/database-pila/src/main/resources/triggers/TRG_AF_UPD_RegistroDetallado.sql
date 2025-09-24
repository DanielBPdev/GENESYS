-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de actualización en RegistroDetallado
-- =============================================
CREATE TRIGGER TRG_AF_UPD_RegistroDetallado
ON staging.RegistroDetallado
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE red 
	SET redDateTimeUpdate = dbo.getLocalDate() 
	FROM staging.RegistroDetallado red
	INNER JOIN INSERTED ins ON ins.redId = red.redId
END
