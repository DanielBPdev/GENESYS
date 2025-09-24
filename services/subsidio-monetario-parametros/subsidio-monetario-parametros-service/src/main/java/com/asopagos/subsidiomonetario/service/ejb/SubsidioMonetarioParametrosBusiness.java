package com.asopagos.subsidiomonetario.service.ejb;

import static com.asopagos.constants.MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO;
import static com.asopagos.constants.MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO;
import static com.asopagos.constants.MensajesGeneralConstants.ERROR_TECNICO_INESPERADO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ParametrizacionCondicionesSubsidio;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ParametrizacionLiquidacionSubsidio;
import com.asopagos.entidades.transversal.core.CajaCompensacion;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.subsidiomonetario.constants.NamedQueriesConstants;
import com.asopagos.subsidiomonetario.dto.ParametrizacionCondicionesSubsidioCajaDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionCondicionesSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.service.SubsidioMonetarioParametrosService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para
 * parametrizar conceptos y condiciones en subsidios<br/>
 */

@Stateless
public class SubsidioMonetarioParametrosBusiness implements SubsidioMonetarioParametrosService {

    private static final String SELECTOR_ASOPAGOS = "asopagos";

    private final ILogger logger = LogManager.getLogger(SubsidioMonetarioParametrosBusiness.class);

    private static final String FORMATO_FECHA = "EEE, dd MMM yyyy HH:mm:ss zzz";

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/topic/ReplicacionParametrosSubsidioCajaTopic")
    private Topic parametrosSubsidioTopic;

    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManagerCore;

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#gestionarConceptos(java.util.List)
     */
    @Override
    public List<Long> gestionarConceptos(List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq) {
        String firmaMetodo = "SubsidioMonetarioParametrosBusiness.gestionarConceptos(List)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ArrayList<Long> identificadores = new ArrayList<>();
        for (int i = 0; i < parametrizacionesLiq.size(); i++) {
            ParametrizacionLiquidacionSubsidioModeloDTO parametrizacion = parametrizacionesLiq.get(i);
            if (parametrizacion != null) {

                Calendar c = Calendar.getInstance();
                c.setTime(parametrizacion.getFechaPeriodoFinal());
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                parametrizacion.setFechaPeriodoFinal(c.getTime());

                if (parametrizacion.getIdParametrizacionLiquidacionSubsidio() != null) {
                    Long identificador = actualizarParametrizacion(parametrizacion);
                    identificadores.add(identificador);
                }
                else {
                    Long identificador = crearParametrizacion(parametrizacion);
                    identificadores.add(identificador);
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return identificadores;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#gestionarCondiciones(java.util.List)
     */
    @Override
    public List<Long> gestionarCondiciones(ParametrizacionCondicionesSubsidioCajaDTO condicion) {
        String firmaMetodo = "SubsidioMonetarioParametrosBusiness.gestionarCondiciones(List)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        ArrayList<Long> identificadores = new ArrayList<>();
        if (condicion.getConceptos() != null) {
            for (int i = 0; i < condicion.getConceptos().size(); i++) {
                ParametrizacionCondicionesSubsidioModeloDTO condicionDTO = condicion.getConceptos().get(i);
                if (condicionDTO != null) {

                    Calendar c = Calendar.getInstance();
                    c.setTime(condicionDTO.getFechaPeriodoFinal());
                    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                    condicionDTO.setFechaPeriodoFinal(c.getTime());

                    condicionDTO.setCodigoCajaCompensacion(condicion.getCodigoCaja());

                    if (condicionDTO.getIdParametrizacionCondicionesSubsidio() != null) {
                        Long identificador = actualizarCondicion(condicionDTO);
                        identificadores.add(identificador);
                    }
                    else {
                        Long identificador = crearCondicion(condicionDTO);
                        identificadores.add(identificador);
                    }
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return identificadores;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarCondiciones()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ParametrizacionCondicionesSubsidioCajaDTO> consultarCondiciones() {
        String firmaMetodo = "SubsidioMonetarioParametrosBusiness.consultarCondiciones()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ParametrizacionCondicionesSubsidioModeloDTO> condiciones = consultarCondiciones(null);
        Map<String, String> infoCajas = consultarNombresCajas();

        List<ParametrizacionCondicionesSubsidioCajaDTO> condicionesPorCaja = new ArrayList<>();

        Map<String, Integer> cajasProcesadas = new HashMap<>();
        Integer indicadorProcesamiento = 0;
        for (ParametrizacionCondicionesSubsidioModeloDTO condicion : condiciones) {

            if (!cajasProcesadas.containsKey(condicion.getCodigoCajaCompensacion())) {

                ParametrizacionCondicionesSubsidioCajaDTO condicionCaja = new ParametrizacionCondicionesSubsidioCajaDTO();
                List<ParametrizacionCondicionesSubsidioModeloDTO> condicionesCaja = new ArrayList<>();
                condicionesCaja.add(condicion);
                condicionCaja.setConceptos(condicionesCaja);
                condicionCaja.setCodigoCaja(condicion.getCodigoCajaCompensacion());
                condicionCaja.setCaja(infoCajas.get(condicion.getCodigoCajaCompensacion()));

                cajasProcesadas.put(condicion.getCodigoCajaCompensacion(), indicadorProcesamiento);
                indicadorProcesamiento++;

                condicionesPorCaja.add(condicionCaja);
            }
            else {
                condicionesPorCaja.get(cajasProcesadas.get(condicion.getCodigoCajaCompensacion())).getConceptos().add(condicion);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return condicionesPorCaja;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarCondiciones()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ParametrizacionCondicionesSubsidioCajaDTO> consultarCondicionesPorAnio(Integer anio) {
        String firmaMetodo = "SubsidioMonetarioParametrosBusiness.consultarCondicionesPorAnio(Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ParametrizacionCondicionesSubsidioModeloDTO> condiciones = consultarCondiciones(anio);
        Map<String, String> infoCajas = consultarNombresCajas();

        List<ParametrizacionCondicionesSubsidioCajaDTO> condicionesPorCaja = new ArrayList<>();

        Map<String, Integer> cajasProcesadas = new HashMap<>();
        Integer indicadorProcesamiento = 0;
        for (ParametrizacionCondicionesSubsidioModeloDTO condicion : condiciones) {

            if (!cajasProcesadas.containsKey(condicion.getCodigoCajaCompensacion())) {

                ParametrizacionCondicionesSubsidioCajaDTO condicionCaja = new ParametrizacionCondicionesSubsidioCajaDTO();
                List<ParametrizacionCondicionesSubsidioModeloDTO> condicionesCaja = new ArrayList<>();
                condicionesCaja.add(condicion);
                condicionCaja.setConceptos(condicionesCaja);
                condicionCaja.setCodigoCaja(condicion.getCodigoCajaCompensacion());
                condicionCaja.setCaja(infoCajas.get(condicion.getCodigoCajaCompensacion()));

                cajasProcesadas.put(condicion.getCodigoCajaCompensacion(), indicadorProcesamiento);
                indicadorProcesamiento++;

                condicionesPorCaja.add(condicionCaja);
            }
            else {
                condicionesPorCaja.get(cajasProcesadas.get(condicion.getCodigoCajaCompensacion())).getConceptos().add(condicion);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return condicionesPorCaja;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#guardarPeriodosLiquidacion(java.util.List, java.lang.Long)
     */
    @Override
    public List<Integer> obtenerAniosCondicionesParametrizadosSubsidio() {
        String firmaMetodo = "SubsidioMonetarioParametrosBusiness.obtenerAniosParametrizadosSubsidio()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Integer> lstTrazabilidadSubsidio = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ANIOS_PARAMETRIZADOS_COND_SUBSIDIOS, Integer.class).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstTrazabilidadSubsidio;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarConceptos()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ParametrizacionLiquidacionSubsidioModeloDTO> consultarConceptos() {
        String firmaMetodo = "SubsidioBusiness.consultarConceptos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ParametrizacionLiquidacionSubsidioModeloDTO> result = consultarConceptos(null);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#consultarConceptos()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ParametrizacionLiquidacionSubsidioModeloDTO> consultarConceptosPorAnio(Integer anio) {
        String firmaMetodo = "SubsidioBusiness.consultarConceptos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ParametrizacionLiquidacionSubsidioModeloDTO> result = consultarConceptos(anio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#guardarPeriodosLiquidacion(java.util.List, java.lang.Long)
     */
    @Override
    public List<Integer> obtenerAniosConceptosParametrizadosSubsidio() {
        String firmaMetodo = "SubsidioMonetarioBusiness.obtenerAniosParametrizadosSubsidio()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Integer> lstTrazabilidadSubsidio = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ANIOS_PARAMETRIZADOS_CONC_SUBSIDIOS, Integer.class).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstTrazabilidadSubsidio;
    }

    @Override
    public void procesarParametrosSubsidioCaja(ParametrizacionCondicionesSubsidioCajaDTO parametrizacionCondicionesSubsidioCaja) {
        Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
        String mensaje = gson.toJson(parametrizacionCondicionesSubsidioCaja);
        gestionarCondiciones(parametrizacionCondicionesSubsidioCaja);
        String codigoCaja = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO);
        String codigo = parametrizacionCondicionesSubsidioCaja.getCodigoCaja();
        if(codigoCaja != null && codigo.equals(codigoCaja)){
            //Es una caja el envío se realiza hacia Asopagos
            codigo = SELECTOR_ASOPAGOS;
        }
        enviarMensaje(mensaje, codigo);
    }

    private Long actualizarParametrizacion(ParametrizacionLiquidacionSubsidioModeloDTO parametrizacion) {
        Long idParametrizacion = parametrizacion.getIdParametrizacionLiquidacionSubsidio();

        try {
            ParametrizacionLiquidacionSubsidio parametrizacionLiq = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACION_LIQUIDACION_ID,
                            ParametrizacionLiquidacionSubsidio.class)
                    .setParameter("idParametrizacionLiquidacionSubsidio", idParametrizacion).getSingleResult();

            //Validar que la nueva fecha para el periodo final sea superior a la fecha previamente definida
            if (parametrizacionLiq.getFechaPeriodoFinal().getTime() < parametrizacion.getFechaPeriodoFinal().getTime()) {
                parametrizacionLiq.setFechaPeriodoFinal(parametrizacion.getFechaPeriodoFinal());
                entityManagerCore.merge(parametrizacionLiq);
            }
            else {
                throw new FunctionalConstraintException(ERROR_RECURSO_INCORRECTO);
            }
        } catch (NoResultException e) {
            logger.error("La consulta no arrojó resultados", e);
            throw new FunctionalConstraintException(ERROR_RECURSO_NO_ENCONTRADO);
        } catch (AsopagosException e) {
            logger.error(ERROR_TECNICO_INESPERADO, e);
            throw e;
        }
        return idParametrizacion;
    }

    private Long crearParametrizacion(ParametrizacionLiquidacionSubsidioModeloDTO parametrizacion) {
        ParametrizacionLiquidacionSubsidio parametrizacionLiq = ParametrizacionLiquidacionSubsidioModeloDTO
                .convertToEntity(parametrizacion);
        entityManagerCore.persist(parametrizacionLiq);
        return parametrizacionLiq.getIdParametrizacionLiquidacionSubsidio();
    }

    private Long actualizarCondicion(ParametrizacionCondicionesSubsidioModeloDTO condicionDTO) {
        Long idCondicion = condicionDTO.getIdParametrizacionCondicionesSubsidio();
        try {
            ParametrizacionCondicionesSubsidio condicionLiq = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICION_LIQUIDACION_ID, ParametrizacionCondicionesSubsidio.class)
                    .setParameter("idParametrizacionCondicionesSubsidio", idCondicion).getSingleResult();

            //Validar que la nueva fecha para el periodo final sea superior a la fecha previamente definida
            if (condicionLiq.getFechaPeriodoFinal().getTime() < condicionDTO.getFechaPeriodoFinal().getTime()) {
                condicionLiq.setFechaPeriodoFinal(condicionDTO.getFechaPeriodoFinal());
                entityManagerCore.merge(condicionLiq);
            }
            else {
                throw new FunctionalConstraintException(ERROR_RECURSO_INCORRECTO);
            }
        } catch (NoResultException e) {
            logger.error(ERROR_TECNICO_INESPERADO, e);
            throw new FunctionalConstraintException(ERROR_RECURSO_NO_ENCONTRADO);
        } catch (AsopagosException e) {
            logger.error("Ocurrió un error inesperado", e);
            throw e;
        }
        return idCondicion;
    }

    private Long crearCondicion(ParametrizacionCondicionesSubsidioModeloDTO condicionDTO) {
        ParametrizacionCondicionesSubsidio condicionLiq = condicionDTO.convertToEntity();
        entityManagerCore.persist(condicionLiq);
        return condicionLiq.getIdParametrizacionCondicionesSubsidio();
    }

    private Map<String, String> consultarNombresCajas() {
        List<CajaCompensacion> cajasCompensacion = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CAJAS_DE_COMPENSACION, CajaCompensacion.class).getResultList();

        Map<String, String> infoCajas = new HashMap<>();
        for (CajaCompensacion cajaCompensacion : cajasCompensacion) {
            infoCajas.put(cajaCompensacion.getCodigo(), cajaCompensacion.getNombre());
        }
        return infoCajas;
    }

    private List<ParametrizacionCondicionesSubsidioModeloDTO> consultarCondiciones(Integer anio) {
        String firmaMetodo = "ConsultasModeloCore.consultarCondiciones()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ParametrizacionCondicionesSubsidioModeloDTO> condicionesDTO = null;
        try {
            List<ParametrizacionCondicionesSubsidio> condiciones = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONDICIONES_LIQUIDACION, ParametrizacionCondicionesSubsidio.class)
                    .setParameter("anio", anio).getResultList();

            condicionesDTO = new ArrayList<>();
            Integer ultimoAnio = 0;
            
            List<Integer> listaAnosProcesados = new ArrayList<>();
            List<String> codigosCajaAnoProcesado = new ArrayList<>();
            for (ParametrizacionCondicionesSubsidio condicion : condiciones) {
                if ((ultimoAnio.compareTo(condicion.getAnioVigenciaParametrizacion()) != 0 
                		|| !codigosCajaAnoProcesado.contains(condicion.getCodigoCajaCompensacion())) || anio != null) {
                    condicionesDTO.add(new ParametrizacionCondicionesSubsidioModeloDTO().convertToDTO(condicion));
                }
                
                ultimoAnio = condicion.getAnioVigenciaParametrizacion();
                
                if(!listaAnosProcesados.contains(ultimoAnio)){
                	listaAnosProcesados.add(ultimoAnio);
                	codigosCajaAnoProcesado.clear();
                }
                codigosCajaAnoProcesado.add(condicion.getCodigoCajaCompensacion());
            }

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return condicionesDTO;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        Collections.reverse(condicionesDTO);
        return condicionesDTO;
    }

    private List<ParametrizacionLiquidacionSubsidioModeloDTO> consultarConceptos(Integer anio) {
        String firmaMetodo = "List<ParametrizacionLiquidacionSubsidioModeloDTO> consultarConceptos(Integer anio)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesDTO = null;
        try {
            List<ParametrizacionLiquidacionSubsidio> parametrizaciones = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRIZACIONES_LIQUIDACION,
                            ParametrizacionLiquidacionSubsidio.class)
                    .setParameter("anio", anio).getResultList();

            parametrizacionesDTO = new ArrayList<>();
            Integer ultimoAnio = 0;
            for (ParametrizacionLiquidacionSubsidio parametrizacion : parametrizaciones) {
                if (ultimoAnio.compareTo(parametrizacion.getAnioVigenciaParametrizacion()) != 0 || anio != null)
                    parametrizacionesDTO.add(ParametrizacionLiquidacionSubsidioModeloDTO.convertToDTO(parametrizacion));
                ultimoAnio = parametrizacion.getAnioVigenciaParametrizacion();
            }

        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return parametrizacionesDTO;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        Collections.reverse(parametrizacionesDTO);
        return parametrizacionesDTO;
    }

    /**
     * Envia un mensaje a un topic a la cual todas las cajas y asopagos estáns suscritas,
     * con el selector de asopagos para que solo este procese el mensaje
     * @param mensaje
     *        Texto a enviar en el cuerpo del mensaje
     * @param codigoCaja
     *        Codigo de la caja a la que se envía el mensaje, si es para asopagos tendrá el valor de asopagos
     */
    private void enviarMensaje(String mensaje, String codigoCaja) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(parametrosSubsidioTopic);
            connection.start();
            TextMessage message = session.createTextMessage();
            message.setText(mensaje);
            message.setStringProperty("selector", codigoCaja);
            logger.debug("Enviando mensaje con selector="+message.getStringProperty("selector"));
            logger.debug("Texto del mensaje: "+message.getText());
            producer.send(message);
        } catch (JMSException e) {
            throw new TechnicalException("Error al enviar el mensaje: " + mensaje, e);
        }
    }
}
