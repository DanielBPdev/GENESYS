-- =============================================
-- Author:      Alfonso Baquero E.
-- Create date: 2019/07/16 
-- Description: Trigger para el diligenciamiento de la 
-- fecha de inserción en RegistroGeneral
-- =============================================
CREATE TRIGGER TRG_AF_INS_RegistroGeneral
ON staging.RegistroGeneral
AFTER INSERT AS
BEGIN
    SET NOCOUNT ON;
	
	UPDATE reg 
	SET regDateTimeInsert = dbo.getLocalDate(), regDateTimeUpdate = dbo.getLocalDate() 
	FROM staging.RegistroGeneral reg 
	INNER JOIN INSERTED ins ON ins.regId = reg.regId
END
