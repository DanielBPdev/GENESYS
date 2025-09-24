-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de actualización en PilaErrorValidacionLog
-- =============================================
CREATE TRIGGER TRG_AF_UPD_PilaErrorValidacionLog
ON PilaErrorValidacionLog
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE pev 
	SET pevDateTimeUpdate = dbo.getLocalDate() 
	FROM PilaErrorValidacionLog pev
	INNER JOIN INSERTED ins ON ins.pevId = pev.pevId
END
