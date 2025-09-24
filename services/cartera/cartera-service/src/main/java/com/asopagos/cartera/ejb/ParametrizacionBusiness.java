package com.asopagos.cartera.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.asopagos.cartera.constants.NamedQueriesConstants;
import com.asopagos.cartera.service.PrametrizacionService;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.ccf.general.DatoTemporalParametrizacion;
import com.asopagos.enumeraciones.cartera.ParametrizacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * parametrización temporal<b>
 * 
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 */
@Stateless
public class ParametrizacionBusiness implements PrametrizacionService {

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "cartera_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger.
     */
    private final ILogger logger = LogManager.getLogger(ParametrizacionBusiness.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.cartera.service.PrametrizacionService#consultarParametrizacion(com.asopagos.enumeraciones.cartera.TipoParametrizacionCarteraEnum)
     */
    @Override
    public String consultarDatoTemporalParametrizacion(ParametrizacionEnum parametrizacion) {
        logger.debug("Inicia consultarParametrizacion(ParametrizacionCarteraEnum tipoParametrizacion)");
        try {
            DatoTemporalParametrizacion datoTemporal = buscarDatoTemporalParametrizacion(parametrizacion);
            if (datoTemporal != null) {
                logger.debug("Finaliza consultarParametrizacion(ParametrizacionCarteraEnum tipoParametrizacion)");
                return datoTemporal.getJsonPayload();
            }
            else {
                logger.debug("Finaliza consultarParametrizacion(ParametrizacionCarteraEnum tipoParametrizacion)");
                return null;
            }
        } catch (Exception e) {
            logger.error("Finaliza con error consultarParametrizacion(ParametrizacionCarteraEnum tipoParametrizacion) ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.cartera.service.PrametrizacionService#guardarParametrizacion(java.lang.String)
     */
    @Override
    public void guardarDatoTemporalParametrizacion(ParametrizacionEnum parametrizacion, String jsonPayload) {
        logger.debug("Inicio guardarDatoTemporalParametrizacion(ParametrizacionEnum,String)");
        try {
            DatoTemporalParametrizacion datoTemporal = null;
            if (parametrizacion != null) {
                datoTemporal = buscarDatoTemporalParametrizacion(parametrizacion);
            }
            if (datoTemporal != null) {
                datoTemporal.setJsonPayload(jsonPayload);
                entityManager.merge(datoTemporal);
            }
            else {
                datoTemporal = new DatoTemporalParametrizacion();
                datoTemporal.setJsonPayload(jsonPayload);
                datoTemporal.setParametrizacion(parametrizacion);
                entityManager.persist(datoTemporal);
            }
            logger.debug("Finaliza guardarDatoTemporalParametrizacion(ParametrizacionEnum,String)");
        } catch (Exception e) {
            logger.debug(
                    "Finaliza con error guardarDatoTemporalParametrizacion(ParametrizacionEnum,String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
 
    /**
     * Método que busca los datos temporales de una parametrización
     * 
     * @return entidad que contiene los datos temporales de una parametrización
     */
    private DatoTemporalParametrizacion buscarDatoTemporalParametrizacion(ParametrizacionEnum parametrizacion) {
        logger.debug("Inicia buscarDatoTemporalParametrizacion(prametrizacion)");
         Query q = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATO_TEMPORAL_PARAMETRIZACION)
                .setParameter("parametrizacion", parametrizacion);
        DatoTemporalParametrizacion datoTemporal;
        try {
            datoTemporal = (DatoTemporalParametrizacion) q.getSingleResult();
        } catch (NoResultException e) {
            datoTemporal = null;
        } 
        logger.debug("Finaliza buscarDatoTemporalParametrizacion(prametrizacion)");
        return datoTemporal;
    }

}
