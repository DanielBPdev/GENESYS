-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/02/22
-- Description: SP que comprueba si el afiliado a cambiado de categoría y lo inserta en CategoriaAfiliado
-- =============================================
CREATE OR ALTER PROCEDURE USP_REP_INS_EstadoAfiliacionEmpleadorCaja
    @perId BIGINT
with execute as owner
AS

BEGIN
SET NOCOUNT ON;
    --print 'INICIA USP_REP_INS_EstadoAfiliacionEmpleadorCaja' 
    --print '@perId' + cast(@perId AS VARCHAR) 
    INSERT EstadoAfiliacionEmpleadorCaja (eecPersona,eecEstadoAfiliacion,eecFechaCambioEstado,eecMotivoDesafiliacion,eecNumeroTrabajadores)
    SELECT vw.perId,vw.empEstadoEmpleador,dbo.getLocalDate(),empl.empMotivoDesafiliacion,cantTra.cantTrabajadores
    FROM vw_EstadoAfiliacionEmpleadorCaja vw   
    LEFT JOIN Empresa emp ON emp.empPersona =  vw.perId    
    LEFT JOIN Empleador empl ON empl.empEmpresa = emp.empId
    LEFT JOIN (SELECT COUNT(*) as cantTrabajadores,per.perId
                FROM Persona per 
                INNER JOIN Empresa emp ON emp.empPersona = per.perId
                INNER JOIN Empleador empl ON empl.empEmpresa = emp.empId
                INNER JOIN RolAfiliado roa ON roa.roaEmpleador = empl.empId
                WHERE roa.roaEstadoAfiliado = 'ACTIVO'
                GROUP BY per.perId) cantTra ON cantTra.perId = vw.perId
    WHERE vw.perId = @perId     
      AND vw.empEstadoEmpleador IS NOT NULL
      AND (vw.empEstadoEmpleador NOT IN (SELECT eecEstadoAfiliacion
                                        FROM EstadoAfiliacionEmpleadorCaja
                                        WHERE eecId = (SELECT MAX(eecId) 
                                                        FROM EstadoAfiliacionEmpleadorCaja 
                                                        WHERE eecPersona = @perId))
      OR NOT EXISTS (SELECT 1 FROM EstadoAfiliacionEmpleadorCaja WHERE eecPersona = @perId))
     --print 'FIN USP_REP_INS_EstadoAfiliacionEmpleadorCaja' 
END;