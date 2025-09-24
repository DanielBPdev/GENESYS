package com.asopagos.reportes.giass;

import javax.persistence.EntityManager;

import com.asopagos.enumeraciones.reportes.ReporteGiassEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;


public class ReporteGiassFactory {
	 /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ReporteGiassFactory.class);
    
    public static ReporteGiassAbstract getReporteGiass(ReporteGiassEnum reporteGiass, EntityManager entityManager) {
        try {
            return (ReporteGiassAbstract) Class.forName(reporteGiass.getImplClass())
                    .getConstructor(EntityManager.class).newInstance(entityManager);
        } catch (Exception ex) {
            String errorMsg = "No se pudo encontrar la implementación de reporte Giass " + reporteGiass.getImplClass();
            logger.error(errorMsg, ex);
            throw new TechnicalException(errorMsg, ex);
        }        
    }
    
}
