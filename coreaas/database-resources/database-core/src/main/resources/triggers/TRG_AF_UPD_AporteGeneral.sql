-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/01/18 
-- Description: 
-- =============================================
CREATE TRIGGER TRG_AF_UPD_AporteGeneral
ON AporteGeneral
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @apgId BIGINT
    DECLARE @apgEmpresa BIGINT    
    DECLARE @empPersona BIGINT
    DECLARE @apdPersonas AS TablaIdType;  

    SELECT @apgId = apgId, 
            @apgEmpresa = apgEmpresa           
    FROM INSERTED   

    INSERT @apdPersonas (perIdAfiliado,idEmpleador)
    SELECT apd.apdPersona,
            empl.empId
    FROM AporteGeneral apg
    INNER JOIN AporteDetallado apd ON apd.apdAporteGeneral = apg.apgId
    INNER JOIN Empresa emp on emp.empid = apg.apgEmpresa
    INNER JOIN Empleador empl on empl.empEmpresa = emp.empId
    WHERE apg.apgId = @apgId 
   
    IF @apgEmpresa IS NOT NULL AND (UPDATE (apgEmpresa) OR UPDATE (apgPeriodoAporte))
    BEGIN
        SELECT @empPersona = emp.empPersona
        FROM Empresa emp                       
        WHERE emp.empId = @apgEmpresa       

        IF @empPersona IS NOT NULL
        BEGIN
            EXEC USP_REP_INS_EstadoAfiliacionEmpleadorCaja @empPersona           
            EXEC USP_REP_INS_EstadoAfiliacionPersonaEmpresa NULL, NULL, @apdPersonas  
        END   
    END

    IF UPDATE (apgPeriodoAporte)
    BEGIN
        EXEC USP_REP_INS_EstadoAfiliacionPersonaCaja NULL, @apdPersonas  
    END

    IF UPDATE (apgPeriodoAporte) OR UPDATE (apgTipoSolicitante)
    BEGIN
        EXEC USP_REP_INS_EstadoAfiliacionPersonaIndependiente NULL, @apdPersonas  
        EXEC USP_REP_INS_EstadoAfiliacionPersonaPensionado NULL, @apdPersonas
    END
END
