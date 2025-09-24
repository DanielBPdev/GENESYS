-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de actualización en RegistroDetalladoNovedad
-- =============================================
CREATE TRIGGER TRG_AF_UPD_RegistroDetalladoNovedad
ON staging.RegistroDetalladoNovedad
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE rdn
	SET rdnDateTimeUpdate = dbo.getLocalDate() 
	FROM staging.RegistroDetalladoNovedad rdn
	INNER JOIN INSERTED ins ON ins.rdnId = rdn.rdnId
END
