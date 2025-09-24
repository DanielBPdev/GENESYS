package com.asopagos.constantes.parametros.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.constantes.parametros.constants.NamedQueriesConstants;
import com.asopagos.constantes.parametros.service.ConfiguracionCCFService;
import com.asopagos.dto.SedeCajaCompensacionDTO;
import com.asopagos.entidades.ccf.core.SedeCajaCompensacion;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la caja de compensacion Familiar <b>Modulo:</b> Transversal
 *
 * @author Luis Arturo Zarate Ayala <a href="lzarate:lzarate@heinsohn.com.co"> lzarate</a>
 */
@Stateless
public class ConfiguracionCCFBusiness implements ConfiguracionCCFService{
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ConfiguracionCCFBusiness.class);

    /**
     * Referencia a la unidad de persistencia del servicio
     */
    @PersistenceContext(unitName = "constantes_parametros_PU")
    private EntityManager entityManager;
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.constantes.parametros.service.ConfiguracionCCFService#consultarSede(java.lang.Long)
     */
    @Override
    public SedeCajaCompensacionDTO consultarSede(Long idSede) {
        
        logger.debug("Inicia consultarSede(Long idSede)");
        SedeCajaCompensacion sede = entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_SEDE_CAJA_COMPENSACION, SedeCajaCompensacion.class)
                .setParameter("idSede", idSede).getSingleResult();
                
        if (sede == null) {
            logger.debug("Finaliza consultarSede(Long idSede)");
            return null;
        }
        
        logger.debug("Finaliza consultarSede(Long idSede)");
        return SedeCajaCompensacionDTO.convertSedeCajaCompensacionToDTO(sede);
    }

}
