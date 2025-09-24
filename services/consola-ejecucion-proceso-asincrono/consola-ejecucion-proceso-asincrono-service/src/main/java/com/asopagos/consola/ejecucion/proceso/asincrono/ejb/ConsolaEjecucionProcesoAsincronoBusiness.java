package com.asopagos.consola.ejecucion.proceso.asincrono.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.consola.ejecucion.proceso.asincrono.constants.NamedQueriesConstants;
import com.asopagos.consola.ejecucion.proceso.asincrono.service.ConsolaEjecucionProcesoAsincronoService;
import com.asopagos.dto.EjecucionProcesoAsincronoDTO;
import com.asopagos.entidades.ccf.general.EjecucionProcesoAsincrono;
import com.asopagos.enumeraciones.fovis.TipoProcesoAsincronoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la afiliación de personas <b>Historia de Usuario:</b> HU-121-104
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@Stateless
public class ConsolaEjecucionProcesoAsincronoBusiness implements ConsolaEjecucionProcesoAsincronoService {

    /**
     * Referencia al logger
     */
    private static final ILogger LOGGER = LogManager.getLogger(ConsolaEjecucionProcesoAsincronoBusiness.class);

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "consolaejecucionprocesoasincrono_PU")
    private EntityManager entityManager;

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.consola.ejecucion.proceso.asincrono.service.ConsolaEjecucionProcesoAsincronoService#registrarEjecucionProcesoAsincrono(EjecucionProcesoAsincronoDTO)
     */
    @Override
    public EjecucionProcesoAsincronoDTO registrarEjecucionProcesoAsincrono(EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO) {
        LOGGER.debug("Inicia servicio registrarEjecucionProcesoAsincrono(EjecucionProcesoAsincronoDTO)");
        
        EjecucionProcesoAsincrono ejecucionProcesoAsincrono = ejecucionProcesoAsincronoDTO.convertToEntity();
        if (ejecucionProcesoAsincrono.getId() == null) {
            entityManager.persist(ejecucionProcesoAsincrono);
        }
        return EjecucionProcesoAsincronoDTO.convertEntityToDTO(ejecucionProcesoAsincrono);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.consola.ejecucion.proceso.asincrono.service.ConsolaEjecucionProcesoAsincronoService#
     *      consultarUltimaEjecucionProcesoAsincrono(com.asopagos.enumeraciones.fovis.TipoProcesoAsincronoEnum)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EjecucionProcesoAsincronoDTO consultarUltimaEjecucionProcesoAsincrono(TipoProcesoAsincronoEnum tipoProceso) {
        LOGGER.debug("Inicia servicio consultarUltimaEjecucionProcesoAsincrono(TipoProcesoAsincronoEnum)");
        EjecucionProcesoAsincronoDTO resultado = new EjecucionProcesoAsincronoDTO();
        List<EjecucionProcesoAsincrono> ejecucionProcesoAsincrono = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMA_EJECUCION_TIPO_PROCESO).setParameter("tipoProceso", tipoProceso)
                .setParameter("revisado", Boolean.FALSE).getResultList();
        if (ejecucionProcesoAsincrono != null && !ejecucionProcesoAsincrono.isEmpty()) {
            resultado = EjecucionProcesoAsincronoDTO.convertEntityToDTO(ejecucionProcesoAsincrono.get(0));
        }
        return resultado;

    }

    /**
     * (non-Javadoc)
     * 
     * @see
     *      com.asopagos.consola.ejecucion.proceso.asincrono.service.ConsolaEjecucionProcesoAsincronoService#actualizarEjecucionProcesoAsincrono(
     *      com.asopagos.dto.EjecucionProcesoAsincronoDTO)
     */
    @Override
    public void actualizarEjecucionProcesoAsincrono(EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO) {
        EjecucionProcesoAsincrono resultUpdate = null;
        if (ejecucionProcesoAsincronoDTO.getId() != null) {
            resultUpdate = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EJECUCION_BY_ID, EjecucionProcesoAsincrono.class)
                    .setParameter("idEjecucion", ejecucionProcesoAsincronoDTO.getId()).getSingleResult();
        }
        if (resultUpdate != null) {
            if (ejecucionProcesoAsincronoDTO.getFechaFin() != null) {
                resultUpdate.setFechaFin(ejecucionProcesoAsincronoDTO.getFechaFin());
            }
            if (ejecucionProcesoAsincronoDTO.getProcesoRevisado() != null) {
                resultUpdate.setProcesoRevisado(ejecucionProcesoAsincronoDTO.getProcesoRevisado());
            }
            if (ejecucionProcesoAsincronoDTO.getProcesoCancelado() != null) {
                resultUpdate.setProcesoCancelado(ejecucionProcesoAsincronoDTO.getProcesoCancelado());
            }
            if (ejecucionProcesoAsincronoDTO.getCantidadTotalProceso() != null) {
                resultUpdate.setCantidadTotalProceso(ejecucionProcesoAsincronoDTO.getCantidadTotalProceso());
            }
            if (ejecucionProcesoAsincronoDTO.getCantidadAvanceProceso() != null) {
                resultUpdate.setCantidadAvanceProceso(ejecucionProcesoAsincronoDTO.getCantidadAvanceProceso());
            }
            entityManager.merge(resultUpdate);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.consola.ejecucion.proceso.asincrono.service.ConsolaEjecucionProcesoAsincronoService#consultarUltimaEjecucionAsincrona(Long,
     *      TipoProcesoAsincronoEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EjecucionProcesoAsincronoDTO consultarUltimaEjecucionAsincrona(Long idProceso, TipoProcesoAsincronoEnum tipoProceso) {
        EjecucionProcesoAsincronoDTO resultado = new EjecucionProcesoAsincronoDTO();
        List<EjecucionProcesoAsincrono> ejecucionProcesoAsincrono = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_EJECUCION_BY_TIPO_AND_ID_PROCESO, EjecucionProcesoAsincrono.class)
                .setParameter("idProceso", idProceso).setParameter("tipoProceso", tipoProceso).getResultList();
        if (ejecucionProcesoAsincrono != null && !ejecucionProcesoAsincrono.isEmpty()) {
            resultado = EjecucionProcesoAsincronoDTO.convertEntityToDTO(ejecucionProcesoAsincrono.get(0));
        }
        return resultado;
    }

}
