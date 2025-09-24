package com.asopagos.aportes.masivos.service.business.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.aportes.masivos.service.business.interfaces.IConsultasModeloStaging;
import com.asopagos.aportes.masivos.service.constants.NamedQueriesConstants;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.RecaudoCotizanteDTO;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.entidades.pila.staging.Transaccion;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Stateless
public class ConsultasModeloStaging implements IConsultasModeloStaging, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloStaging.class);

    /**
     * Entity Manager
     */
    @PersistenceContext(unitName = "aportes_masivos_pilaStaging_PU")
    private EntityManager entityManagerStaging;


    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#completarDatosAporteOriginal(com.asopagos.aportes.dto.CuentaAporteDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CuentaAporteDTO consultarDatosPlanillaAporte(Long registroGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.completarDatosAporteOriginal(CuentaAporteDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Object[] resultQuery = null;
        
        CuentaAporteDTO r = new CuentaAporteDTO();

        try {
            resultQuery = (Object[]) entityManagerStaging.createNamedQuery(NamedQueriesConstants.CONSULTA_DATOS_PLANILLA_APORTE_MASIVO)
                    .setParameter("idRegistroGeneral", registroGeneral).getSingleResult();

            if (resultQuery != null) {               
                r.setRegistroControl(resultQuery[0]!=null?Long.parseLong(resultQuery[0].toString()):null);
                r.setEstadoArchivo(resultQuery[1]!=null?EstadoProcesoArchivoEnum.valueOf(resultQuery[1].toString()):null);               
            }
        } catch (NoResultException e) {
            logger.debug(firmaMetodo + " :: sin datos");
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Hubo un error en la consulta: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return r;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloStaging#consultarRegistrosGeneralesPorListaId(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<Long, RegistroGeneralModeloDTO> consultarRegistrosGeneralesPorListaId(List<Long> idsRegistrosGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.eliminarRegistroGeneralId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, RegistroGeneralModeloDTO> result = new HashMap<>();

        int max = 1000;
        int count = 0;
        List<Long> idsRegistrosGeneralTMP = new ArrayList<Long>();
        for(Long id : idsRegistrosGeneral) {
        	count++;
        	idsRegistrosGeneralTMP.add(id);
        	
        	if(count == max) {
            	result.putAll(consultarRegistrosGeneralesPorIds(idsRegistrosGeneralTMP));
            	count = 0;
                idsRegistrosGeneralTMP = new ArrayList<Long>();
            }
        }
        
        if(count > 0 ) {
        	result.putAll(consultarRegistrosGeneralesPorIds(idsRegistrosGeneralTMP));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /**
     * Realiza la consulta por la lista de identificadores de registros generales
     * @param idsRegistrosGeneral
     * @return
     */
    private Map<Long, RegistroGeneralModeloDTO> consultarRegistrosGeneralesPorIds(List<Long> idsRegistrosGeneral) {
        String firmaMetodo = "ConsultasModeloStaging.consultarRegistrosGeneralesPorIds(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<Long, RegistroGeneralModeloDTO> result = new HashMap<>();

        List<RegistroGeneralModeloDTO> resultQuery = entityManagerStaging
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_REGISTRO_GENERAL_POR_LISTADO_ID, RegistroGeneralModeloDTO.class)
                .setParameter("idsRegistrosGeneral", idsRegistrosGeneral).getResultList();

        if (!resultQuery.isEmpty()) {
            for (RegistroGeneralModeloDTO registroGeneral : resultQuery) {
                result.put(registroGeneral.getId(), registroGeneral);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
}