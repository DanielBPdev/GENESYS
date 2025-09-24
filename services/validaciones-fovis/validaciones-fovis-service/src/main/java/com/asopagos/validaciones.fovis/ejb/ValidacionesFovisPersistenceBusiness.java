package com.asopagos.validaciones.fovis.ejb;

import java.util.Date;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.transversal.core.DatosRegistroValidacion;
import com.asopagos.entidades.transversal.core.HistoriaResultadoValidacion;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.validaciones.fovis.dto.HistoricoDTO;
import com.asopagos.validaciones.fovis.service.ValidacionesFovisPersistenceService;

/**
 * contiene los servicios de almacenamiento de información del procesamiento de validaciones
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
@Stateless
public class ValidacionesFovisPersistenceBusiness implements ValidacionesFovisPersistenceService {

    /**
     * Instancia del gestor de registro de eventos
     */
    private static final ILogger logger = LogManager.getLogger(ValidacionesFovisPersistenceBusiness.class);

    /**
     * Instancia del EntityManager
     */
    @PersistenceContext(unitName = "validaciones_PU")
    private EntityManager entityManager;

    /**
     * (non-Javadoc)
     * @see com.asopagos.validaciones.service.ValidacionesPersistenceService#persistirHistoricoValidaciones(HistoricoDTO)
     */
    @Asynchronous
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void persistirHistoricoValidaciones(HistoricoDTO datoHistorico) {
        DatosRegistroValidacion datos = new DatosRegistroValidacion();
        datos.setDatosValidacion(datoHistorico.getDatosValidacion());
        datos.setFechaValidacion(new Date());
        entityManager.persist(datos);
        HistoriaResultadoValidacion hist;
        for (ValidacionDTO validacionAfiliacionDTO : datoHistorico.getResultadoValidacion()) {
            if (validacionAfiliacionDTO != null) {
                hist = new HistoriaResultadoValidacion();
                hist.setDetalle(validacionAfiliacionDTO.getDetalle());
                hist.setResultado(validacionAfiliacionDTO.getResultado());
                hist.setValidacion(validacionAfiliacionDTO.getValidacion());
                hist.setDatosRegistro(datos);
                entityManager.persist(hist);
                datos.getHistorico().add(hist);
            }
        }
    }
}
