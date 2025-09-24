-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de inserción en PilaErrorValidacionLog
-- =============================================
CREATE TRIGGER TRG_AF_INS_PilaErrorValidacionLog
ON PilaErrorValidacionLog
AFTER INSERT AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE pev 
	SET pevDateTimeInsert = dbo.getLocalDate(), pevDateTimeUpdate = dbo.getLocalDate() 
	FROM PilaErrorValidacionLog pev
	INNER JOIN INSERTED ins ON ins.pevId = pev.pevId
END
