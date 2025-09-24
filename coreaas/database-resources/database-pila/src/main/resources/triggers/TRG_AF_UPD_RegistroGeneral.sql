-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de actualización en RegistroGeneral
-- =============================================
CREATE TRIGGER TRG_AF_UPD_RegistroGeneral
ON staging.RegistroGeneral
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE reg
	SET regDateTimeUpdate = dbo.getLocalDate() 
	FROM staging.RegistroGeneral reg
	INNER JOIN INSERTED ins ON ins.regId = reg.regId
END
