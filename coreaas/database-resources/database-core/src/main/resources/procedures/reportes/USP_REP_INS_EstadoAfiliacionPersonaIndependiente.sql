-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/02/22
-- Description: SP que comprueba si el afiliado a cambiado de categoría y lo inserta en CategoriaAfiliado
-- =============================================
CREATE PROCEDURE USP_REP_INS_EstadoAfiliacionPersonaIndependiente
    @perIdAfiliado BIGINT,   
    @perIdsAfiliados TablaIdType READONLY  
with execute as owner
AS

BEGIN
SET NOCOUNT ON; 	
    --print 'INICIA USP_REP_INS_EstadoAfiliacionPersonaIndependiente' 
    
    IF @perIdAfiliado IS NULL
    BEGIN       
        --print '@perIdsAfiliados'       

    INSERT EstadoAfiliacionPersonaIndependiente (eaiPersona,eaiEstadoAfiliacion,eaiFechaCambioEstado,eaiClaseIndependiente)
    SELECT vw.perId,vw.roaEstadoAfiliado,dbo.getLocalDate(),vw.roaClaseIndependiente
    FROM VW_EstadoAfiliacionPersonaIndependiente vw
    INNER JOIN @perIdsAfiliados personas ON personas.perIdAfiliado = vw.perId
    WHERE vw.roaEstadoAfiliado IS NOT NULL
      AND (vw.roaEstadoAfiliado NOT IN (SELECT eai.eaiEstadoAfiliacion
                                        FROM EstadoAfiliacionPersonaIndependiente eai
                                        WHERE eaiId = (SELECT MAX(eaiId) eaiId
                                                    FROM EstadoAfiliacionPersonaIndependiente 
                                                    WHERE eaiPersona = personas.perIdAfiliado))
          OR NOT EXISTS (SELECT 1 FROM EstadoAfiliacionPersonaIndependiente 
                        WHERE eaiPersona = personas.perIdAfiliado))

    END
    ELSE
    BEGIN
        --print '@perIdAfiliado' + cast(@perIdAfiliado AS VARCHAR) 
        
        INSERT EstadoAfiliacionPersonaIndependiente (eaiPersona,eaiEstadoAfiliacion,eaiFechaCambioEstado,eaiClaseIndependiente)
        SELECT vw.perId,vw.roaEstadoAfiliado,dbo.getLocalDate(),vw.roaClaseIndependiente
        FROM VW_EstadoAfiliacionPersonaIndependiente vw
        WHERE vw.perId = @perIdAfiliado
          AND vw.roaEstadoAfiliado IS NOT NULL
          AND (vw.roaEstadoAfiliado NOT IN (SELECT eaiEstadoAfiliacion
                                            FROM EstadoAfiliacionPersonaIndependiente
                                            WHERE eaiId = (SELECT MAX(eaiId) 
                                                           FROM EstadoAfiliacionPersonaIndependiente 
                                                            WHERE eaiPersona = @perIdAfiliado))                                                              
            OR NOT EXISTS (SELECT 1 FROM EstadoAfiliacionPersonaIndependiente 
                            WHERE eaiPersona = @perIdAfiliado))                              
    END
    --print 'TERMINA USP_REP_INS_EstadoAfiliacionPersonaIndependiente'     
END;