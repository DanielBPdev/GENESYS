-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE TRIGGER TRG_AF_UPD_Afiliado
ON Afiliado
AFTER UPDATE AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @afiId BIGINT
    DECLARE @afiPersona BIGINT
    DECLARE @empPersona BIGINT
END
