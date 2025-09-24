-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de inserción en PilaIndicePlanilla
-- =============================================
CREATE TRIGGER TRG_AF_INS_PilaIndicePlanilla
ON PilaIndicePlanilla
AFTER INSERT AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE pip 
	SET pipDateTimeInsert = dbo.getLocalDate(), pipDateTimeUpdate = dbo.getLocalDate() 
	FROM PilaIndicePlanilla pip
	INNER JOIN INSERTED ins ON ins.pipId = pip.pipId
END
