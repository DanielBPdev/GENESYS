-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de inserción en PilaEstadoBloque
-- =============================================
CREATE TRIGGER TRG_AF_INS_PilaEstadoBloque
ON PilaEstadoBloque
AFTER INSERT AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE peb 
	SET pebDateTimeInsert = dbo.getLocalDate(), pebDateTimeUpdate = dbo.getLocalDate() 
	FROM PilaEstadoBloque peb
	INNER JOIN INSERTED ins ON ins.pebId = peb.pebId
END
