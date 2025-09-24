-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/02/22
-- Description: SP que comprueba si el afiliado a cambiado de categoría y lo inserta en CategoriaAfiliado
-- =============================================
CREATE PROCEDURE USP_REP_INS_EstadoAfiliacionPersonaCaja
    @perIdAfiliado BIGINT,
    @perIdsAfiliados TablaIdType READONLY  
with execute as owner
AS

BEGIN
SET NOCOUNT ON;	
    --print 'INICIA USP_REP_INS_EstadoAfiliacionPersonaCaja' 
    --print '@perId' + cast(@perIdAfiliado AS VARCHAR) 
    IF @perIdAfiliado IS NULL
    BEGIN       
        INSERT INTO EstadoAfiliacionPersonaCaja (eacPersona,eacEstadoAfiliacion,eacFechaCambioEstado)
        SELECT vw.perId,vw.roaEstadoAfiliado,dbo.getLocalDate()
        FROM vw_EstadoAfiliacionPersonaCaja vw        
        INNER JOIN @perIdsAfiliados personas ON personas.perIdAfiliado = vw.perId
        INNER JOIN (SELECT MIN(prioridad) prioridad,perId 
                    FROM vw_EstadoAfiliacionPersonaCaja                        
                    GROUP BY perId) estadoPrioridad ON estadoPrioridad.prioridad = vw.prioridad
                                                    AND estadoPrioridad.perId = vw.perId
        WHERE vw.roaEstadoAfiliado IS NOT NULL
          AND (vw.roaEstadoAfiliado NOT IN (SELECT eac.eacEstadoAfiliacion
                                        FROM EstadoAfiliacionPersonaCaja eac
                                        WHERE eacId = (SELECT MAX(eacId) eacId
                                                    FROM EstadoAfiliacionPersonaCaja 
                                                    WHERE eacPersona = personas.perIdAfiliado))
          OR NOT EXISTS (SELECT 1 FROM EstadoAfiliacionPersonaCaja WHERE eacPersona = personas.perIdAfiliado))

    END
    ELSE
    BEGIN
        INSERT EstadoAfiliacionPersonaCaja (eacPersona,eacEstadoAfiliacion,eacFechaCambioEstado)
        SELECT vw.perId,vw.roaEstadoAfiliado,dbo.getLocalDate()
        FROM VW_EstadoAfiliacionPersonaCaja vw
        INNER JOIN (SELECT MIN(prioridad) prioridad,perId 
                        FROM vw_EstadoAfiliacionPersonaCaja 
                        WHERE perId = @perIdAfiliado 
                    GROUP BY perId) estadoPrioridad ON estadoPrioridad.prioridad = vw.prioridad
                                                    AND estadoPrioridad.perId = vw.perId
        WHERE vw.perId = @perIdAfiliado 
          AND vw.roaEstadoAfiliado IS NOT NULL
          AND (vw.roaEstadoAfiliado NOT IN (SELECT eacEstadoAfiliacion
                                        FROM EstadoAfiliacionPersonaCaja
                                        WHERE eacId = (SELECT MAX(eacId) 
                                                        FROM EstadoAfiliacionPersonaCaja 
                                                        WHERE eacPersona = @perIdAfiliado))
      OR NOT EXISTS (SELECT 1 FROM EstadoAfiliacionPersonaCaja WHERE eacPersona = @perIdAfiliado))
    END
    --print 'TERMINA USP_REP_INS_EstadoAfiliacionPersonaCaja'     
END;