-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/02/22
-- Description: SP que comprueba si el afiliado a cambiado de categoría y lo inserta en CategoriaAfiliado
-- =============================================
CREATE PROCEDURE USP_REP_INS_EstadoAfiliacionPersonaPensionado
    @perIdAfiliado BIGINT,   
    @perIdsAfiliados TablaIdType READONLY  
with execute as owner
AS

BEGIN
SET NOCOUNT ON;	
    --print 'INICIA USP_REP_INS_EstadoAfiliacionPersonaPensionado' 
    
    IF @perIdAfiliado IS NULL
    BEGIN       
        --print '@perIdsAfiliados'       

    INSERT EstadoAfiliacionPersonaPensionado (eapPersona,eapEstadoAfiliacion,eapFechaCambioEstado)
    SELECT vw.perId,vw.roaEstadoAfiliado,dbo.getLocalDate()
    FROM VW_EstadoAfiliacionPersonaPensionado vw
    INNER JOIN @perIdsAfiliados personas ON personas.perIdAfiliado = vw.perId
    WHERE vw.roaEstadoAfiliado IS NOT NULL
      AND (vw.roaEstadoAfiliado NOT IN (SELECT eap.eapEstadoAfiliacion
                                        FROM EstadoAfiliacionPersonaPensionado eap
                                        WHERE eapId = (SELECT MAX(eapId) eapId
                                                    FROM EstadoAfiliacionPersonaPensionado 
                                                    WHERE eapPersona = personas.perIdAfiliado))
          OR NOT EXISTS (SELECT 1 FROM EstadoAfiliacionPersonaPensionado 
                        WHERE eapPersona = personas.perIdAfiliado))

    END
    ELSE
    BEGIN
        --print '@perIdAfiliado' + cast(@perIdAfiliado AS VARCHAR) 
        
        INSERT EstadoAfiliacionPersonaPensionado (eapPersona,eapEstadoAfiliacion,eapFechaCambioEstado)
        SELECT vw.perId,vw.roaEstadoAfiliado,dbo.getLocalDate()
        FROM VW_EstadoAfiliacionPersonaPensionado vw
        WHERE vw.perId = @perIdAfiliado  
          AND vw.roaEstadoAfiliado IS NOT NULL
          AND (vw.roaEstadoAfiliado NOT IN (SELECT eapEstadoAfiliacion
                                            FROM EstadoAfiliacionPersonaPensionado
                                            WHERE eapId = (SELECT MAX(eapId) 
                                                           FROM EstadoAfiliacionPersonaPensionado 
                                                            WHERE eapPersona = @perIdAfiliado))                                                              
            OR NOT EXISTS (SELECT 1 FROM EstadoAfiliacionPersonaPensionado 
                            WHERE eapPersona = @perIdAfiliado))                              
    END
    --print 'TERMINA USP_REP_INS_EstadoAfiliacionPersonaPensionado'     
END;