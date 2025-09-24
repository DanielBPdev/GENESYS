-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18 
-- Description:	
-- =============================================
CREATE TRIGGER TRG_AF_INS_SolicitudLiquidacionSubsidio
ON aud.SolicitudLiquidacionSubsidio_aud
AFTER INSERT AS
BEGIN
    SET NOCOUNT ON; 

    INSERT HistoricoSolicitudLiquidacionSubsidio 
            (hslEstadoLiquidacion,hslSolicitudLiquidacionSubsidio,hslTimeStamp,hslNombreUsuario,hslObservacionesSegundoNivel,hslRazonRechazoLiquidacion)
    SELECT sls.slsEstadoLiquidacion,sls.slsId,rev.revTimeStamp,rev.revNombreUsuario,sls.slsObservacionesSegundoNivel,sls.slsRazonRechazoLiquidacion
    FROM INSERTED sls
    INNER JOIN aud.Revision rev ON rev.revId = sls.REV
    WHERE sls.slsEstadoLiquidacion IS NOT NULL
END