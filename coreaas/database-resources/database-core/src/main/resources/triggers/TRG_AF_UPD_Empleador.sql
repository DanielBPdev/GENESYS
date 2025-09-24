-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/01/18 
-- Description: 
-- =============================================
CREATE TRIGGER TRG_AF_UPD_Empleador
ON Empleador
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @empId BIGINT    
    DECLARE @empPersona BIGINT 
    DECLARE @afiPersonas AS TablaIdType; 
    DECLARE @estadoEmpleador varchar(50); 
	DECLARE @fechaRetiroEmpleador DATE; 
	DECLARE @motivoRetiroEmpledor varchar(100); 
    SELECT @empId = empId, @estadoEmpleador = empEstadoEmpleador,@fechaRetiroEmpleador= empFechaRetiro,@motivoRetiroEmpledor=empMotivoDesafiliacion    
    FROM INSERTED  
    --IF UPDATE(empEstadoEmpleador) and @estadoEmpleador='INACTIVO'
	--BEGIN
	--	UPDATE RolAfiliado SET roaEstadoAfiliado='INACTIVO',roaFechaRetiro=@fechaRetiroEmpleador,roaMotivoDesafiliacion=@motivoRetiroEmpledor where roaEmpleador=@empId and roaEstadoAfiliado='ACTIVO'
	--END

    IF UPDATE (empFechaRetiro) OR UPDATE (empEstadoEmpleador)
    BEGIN
        SELECT @empPersona = emp.empPersona
        FROM Empresa emp 
        INNER JOIN Empleador empl ON emp.empId = empl.empEmpresa
        WHERE empl.empId = @empId

        IF @empPersona IS NOT NULL 
        BEGIN
            EXEC USP_REP_INS_EstadoAfiliacionEmpleadorCaja @empPersona            
        END 
    END   

    IF UPDATE (empEmpresa)
    BEGIN 
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
END