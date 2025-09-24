-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de actualización en PilaIndicePlanilla
-- =============================================
CREATE TRIGGER TRG_AF_UPD_PilaIndicePlanilla
ON PilaIndicePlanilla
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE pip 
	SET pipDateTimeUpdate = dbo.getLocalDate() 
	FROM PilaIndicePlanilla pip
	INNER JOIN INSERTED ins ON ins.pipId = pip.pipId
END
