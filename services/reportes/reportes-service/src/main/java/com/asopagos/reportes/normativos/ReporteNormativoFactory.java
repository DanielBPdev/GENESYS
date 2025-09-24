package com.asopagos.reportes.normativos;

import javax.persistence.EntityManager;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * @author sbrinez
 */
public class ReporteNormativoFactory {
    
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ReporteNormativoFactory.class);
    
    public static ReporteNormativoAbstract getReporteNormativo(ReporteNormativoEnum reporteNormativo, EntityManager entityManager) {
        try {
            return (ReporteNormativoAbstract) Class.forName(reporteNormativo.getImplClass())
                    .getConstructor(EntityManager.class).newInstance(entityManager);
        } catch (Exception ex) {
            String errorMsg = "No se pudo encontrar la implementación de reporte normativo " + reporteNormativo.getImplClass();
            logger.error(errorMsg, ex);
            throw new TechnicalException(errorMsg, ex);
        }        
    }
    
}
