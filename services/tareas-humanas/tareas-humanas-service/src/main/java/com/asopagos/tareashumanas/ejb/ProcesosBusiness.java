package com.asopagos.tareashumanas.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.kie.services.client.serialization.jaxb.impl.process.JaxbProcessInstanceResponse;
import org.kie.services.client.serialization.jaxb.rest.JaxbGenericResponse;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.tareashumanas.client.JBPMClient;
import com.asopagos.tareashumanas.client.JBPMClientFactory;
import com.asopagos.tareashumanas.service.ProcesosService;
import com.asopagos.util.Interpolator;

/**
 * <b>Descripción:</b> IEJB que implementa los métodos de negocio relacionados
 * con los procesos en el BPM <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
//El control de las excepciones de este servicio se realiza en el mapper.
@Stateless
public class ProcesosBusiness implements ProcesosService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesosBusiness.class);

    /**
     * Método que envía una señal a una instancia de proceso con parametros de
     * tipo de dato simples
     * 
     * @param proceso
     * @param tipoSenal
     * @param idInstanciaProceso
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void enviarSenal(ProcesoEnum proceso, String tipoSenal, Long idInstanciaProceso, String event, UserDTO user) {
        logger.info(Interpolator.interpolate("Enviando senal con proceso : {0}, tipoSenal : {1}, idInstanciaProceso : {2}, event : {3}", proceso, tipoSenal, idInstanciaProceso, event));
        JBPMClient client = JBPMClientFactory.getJBPMClient();
        String deploymentId = (String) CacheManager.getConstante(proceso.getDeploymentParamName());
        client.signalProcess(deploymentId, idInstanciaProceso, tipoSenal, event);
        logger.info(Interpolator.interpolate("Senal Enviada proceso : {0}, tipoSenal : {1}, idInstanciaProceso : {2}, event : {3}", proceso, tipoSenal, idInstanciaProceso, event));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.tareashumanas.service.TareasHumanasService#iniciarProceso(
     * com.asopagos.enumeraciones.core.ProcesoEnum, java.util.Map)
     */
    @Override
    public Long iniciarProceso(ProcesoEnum proceso, Map<String, Object> params, UserDTO user) {
        String depId = (String) CacheManager.getConstante(proceso.getDeploymentParamName());
        System.out.println("**__**iniciarProcesoAnalisisNovedad iniciarProceso: "+proceso+" depId: "+depId );
        JaxbProcessInstanceResponse instancia = null;
        try {
            JBPMClient client = JBPMClientFactory.getJBPMClient();
            if (params != null && !params.isEmpty()) {
                MultivaluedMap<String, Object> processParams = new MultivaluedHashMap<>();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    if (value instanceof Integer) {
                        value = value + "i";
                    }
                    else if (value instanceof String) {
                        value = "\"" + value + "\"";
                    }
                    processParams.add("map_" + entry.getKey(), value);
                }
                client = JBPMClientFactory.getJBPMClient(processParams);
                processParams.forEach((k, v) -> logger.info("Key: " + k + ": Value: " + v));
            }
            logger.info("Iniciando proceso: " + proceso.getNombreProcesoBPM() + " con deploymentId: " + depId);
            instancia = client.startProcess(depId, proceso.getNombreProcesoBPM());
        } catch (Exception e) {
            try{
                JBPMClient client = JBPMClientFactory.getJBPMClient();
                if (params != null && !params.isEmpty()) {
                    MultivaluedMap<String, Object> processParams = new MultivaluedHashMap<>();
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        Object value = entry.getValue();
                        if (value instanceof Integer) {
                            value = value + "i";
                        }
                        else if (value instanceof String) {
                            value = "\"" + value + "\"";
                        }
                        processParams.add("map_" + entry.getKey(), value);
                    }
                    client = JBPMClientFactory.getJBPMClient(processParams);
                    processParams.forEach((k, v) -> logger.info("Key: " + k + ": Value: " + v));
                }
                logger.info("Iniciando proceso: " + proceso.getNombreProcesoBPM() + " con deploymentId: " + depId);
                instancia = client.startProcess(depId, proceso.getNombreProcesoBPM());
                return instancia.getId();
            }catch (Exception x){
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,x);
            }
        }
        System.out.println("**__**iniciarProcesoAnalisisNovedad instancia: "+instancia.getId() );
        return instancia.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.tareashumanas.service.ProcesosService#abortarProceso(com.asopagos.enumeraciones.core.ProcesoEnum, java.lang.Long,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public String abortarProceso(ProcesoEnum proceso, Long idInstanciaProceso, UserDTO user) {
        String depId = (String) CacheManager.getConstante(proceso.getDeploymentParamName());
        JBPMClient client = JBPMClientFactory.getJBPMClient();
        JaxbGenericResponse response = client.abortProcessInstance(depId, idInstanciaProceso);
        return response.getMessage();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.tareashumanas.service.ProcesosService#abortarProcesos(com.asopagos.enumeraciones.core.ProcesoEnum, java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void abortarProcesos(ProcesoEnum proceso, List<Long> idInstanciaProceso, UserDTO user) {
        logger.debug("inicia metodo abortarProcesos(ProcesoEnum proceso, List<Long> idInstanciaProceso, UserDTO user)");
        for (Long idInstancia : idInstanciaProceso) {
            /* Se realiza llamado al metodo abortar proceso */
            abortarProceso(proceso, idInstancia, user);
        }
        logger.debug("Finaliza metodo abortarProcesos(ProcesoEnum proceso, List<Long> idInstanciaProceso, UserDTO user)");
    }

}
