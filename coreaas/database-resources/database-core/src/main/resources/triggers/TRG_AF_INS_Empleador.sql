-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE TRIGGER TRG_AF_INS_Empleador
ON Empleador
AFTER INSERT AS
BEGIN
	SET NOCOUNT ON;
    DECLARE @empId BIGINT    
    DECLARE @empPersona BIGINT 
    DECLARE @afiPersonas AS TablaIdType;  

    SELECT @empId = empId       
    FROM INSERTED
   
    SELECT @empPersona = emp.empPersona
    FROM Empresa emp 
    INNER JOIN Empleador empl ON emp.empId = empl.empEmpresa
    WHERE empl.empId = @empId

    IF @empPersona IS NOT NULL 
    BEGIN
        EXEC USP_REP_INS_EstadoAfiliacionEmpleadorCaja @empPersona      
    END 
     
    INSERT @afiPersonas (perIdAfiliado,idEmpleador)
    SELECT afi.afiPersona,
            empl.empId    
    FROM Afiliado afi
    INNER JOIN RolAfiliado roa ON afi.afiId = roa.roaAfiliado
    INNER JOIN Empleador empl ON roa.roaEmpleador = empl.empId
    INNER JOIN Empresa emp ON emp.empId = empl.empEmpresa
    WHERE empl.empId = @empId

    EXEC USP_REP_INS_EstadoAfiliacionPersonaEmpresa NULL, NULL, @afiPersonas       
END
