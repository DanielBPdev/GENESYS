-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/02/22
-- Description: SP que comprueba si el afiliado a cambiado de categoría y lo inserta en CategoriaAfiliado
-- =============================================
CREATE PROCEDURE USP_REP_INS_EstadoAfiliacionPersonaEmpresa
    @perIdAfiliado BIGINT,
    @idEmpleador BIGINT,
    @perIdsAfiliados TablaIdType READONLY  
with execute as owner
AS

BEGIN
SET NOCOUNT ON;
    --print 'INICIA USP_REP_INS_EstadoAfiliacionPersonaEmpresa'
    
    IF @perIdAfiliado IS NULL
    BEGIN       
        --print '@perIdsAfiliados'

        INSERT INTO EstadoAfiliacionPersonaEmpresa (eaePersona,eaeEmpleador,eaeEstadoAfiliacion,eaeFechaCambioEstado)
        SELECT vw.perId,vw.roaEmpleador,vw.roaEstadoAfiliado,dbo.getLocalDate()            
        FROM VW_EstadoAfiliacionPersonaEmpresa vw
        INNER JOIN @perIdsAfiliados personas ON personas.perIdAfiliado = vw.perId AND  personas.idEmpleador = vw.roaEmpleador    
        WHERE vw.roaEstadoAfiliado IS NOT NULL
          AND (vw.roaEstadoAfiliado NOT IN (SELECT eae.eaeEstadoAfiliacion
                                        FROM EstadoAfiliacionPersonaEmpresa eae
                                        WHERE eaeId = (SELECT MAX(eaeId) eaeId
                                                    FROM EstadoAfiliacionPersonaEmpresa 
                                                    WHERE eaePersona = personas.perIdAfiliado
                                                      AND eaeEmpleador = personas.idEmpleador))
          OR NOT EXISTS (SELECT 1 FROM EstadoAfiliacionPersonaEmpresa 
                        WHERE eaePersona = personas.perIdAfiliado
                          AND eaeEmpleador = personas.idEmpleador))

    END
    ELSE
    BEGIN
        --print '@perIdAfiliado' + cast(@perIdAfiliado AS VARCHAR)
        --print '@idEmpleador' + cast(@idEmpleador AS VARCHAR)
        INSERT INTO EstadoAfiliacionPersonaEmpresa (eaePersona,eaeEmpleador,eaeEstadoAfiliacion,eaeFechaCambioEstado)
        SELECT vw.perId,vw.roaEmpleador,vw.roaEstadoAfiliado,dbo.getLocalDate()            
        FROM VW_EstadoAfiliacionPersonaEmpresa vw
        WHERE vw.perId = @perIdAfiliado    
          AND vw.roaEmpleador = @idEmpleador  
          AND vw.roaEstadoAfiliado IS NOT NULL
          AND (vw.roaEstadoAfiliado NOT IN (SELECT eaeEstadoAfiliacion
                                            FROM EstadoAfiliacionPersonaEmpresa
                                            WHERE eaeId = (SELECT MAX(eaeId) 
                                                           FROM EstadoAfiliacionPersonaEmpresa 
                                                            WHERE eaePersona = @perIdAfiliado
                                                              AND eaeEmpleador = @idEmpleador))
            OR NOT EXISTS (SELECT 1 FROM EstadoAfiliacionPersonaEmpresa 
                            WHERE eaePersona = @perIdAfiliado 
                              AND eaeEmpleador = @idEmpleador))
    END
    --print 'TERMINA USP_REP_INS_EstadoAfiliacionPersonaEmpresa'
END;