-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de actualización en PilaEstadoBloque
-- =============================================
CREATE TRIGGER TRG_AF_UPD_PilaEstadoBloque
ON PilaEstadoBloque
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE peb 
	SET pebDateTimeUpdate = dbo.getLocalDate() 
	FROM PilaEstadoBloque peb
	INNER JOIN INSERTED ins ON ins.pebId = peb.pebId
END
