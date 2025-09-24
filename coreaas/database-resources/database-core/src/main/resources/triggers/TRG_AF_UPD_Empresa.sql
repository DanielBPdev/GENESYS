-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE TRIGGER TRG_AF_UPD_Empresa
ON Empresa
AFTER UPDATE AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @empId BIGINT   
    DECLARE @empPersona BIGINT   

    SELECT @empId = empId,
        @empPersona = empPersona
    FROM INSERTED   

    IF UPDATE (empPersona)
    BEGIN
        IF @empPersona IS NOT NULL 
        BEGIN
            EXEC USP_REP_INS_EstadoAfiliacionEmpleadorCaja @empPersona	       
		END	
    END   
END
