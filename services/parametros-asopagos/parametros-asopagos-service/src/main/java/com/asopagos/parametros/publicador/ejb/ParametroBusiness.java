package com.asopagos.parametros.publicador.ejb;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.entidades.transversal.core.Requisito;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.listaschequeo.dto.RequisitoCajaClasificacionDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.parametros.TablaParametro;
import com.asopagos.parametros.dto.DatosTablaParametrizableDTO;
import com.asopagos.parametros.dto.ParametroReplicacionDTO;
import com.asopagos.parametros.dto.TablaParametroDTO;
import com.asopagos.parametros.enums.EnumAccion;
import com.asopagos.parametros.service.ParametroService;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.subsidiomonetario.clients.GestionarConceptos;
import com.asopagos.subsidiomonetario.clients.GestionarCondiciones;
import com.asopagos.subsidiomonetario.dto.ParametrizacionCondicionesSubsidioCajaDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <b>Descripción:</b> EJB encargada de manejar la lógica de negocio al momento
 * de realizar alguna operación CRUD en base de datos. Adicionalmente contiene
 * lógica para la replicación entre las diferentes bases de datos de las CCF.
 *
 */
@Stateless
public class ParametroBusiness implements ParametroService {
    /**
     * 
     */
    private static final String FORMATO_FECHA = "EEE, dd MMM yyyy HH:mm:ss zzz";

    /**
     * Referencia a la unidad de persistencia del servicio
     */
    @PersistenceContext(unitName = "parametros_PU")
    private EntityManager entityManager;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/topic/ReplicacionCajasActualizarListaEspecialRevisionTopic")
    private Topic actualizarListaEspecialTopic;

    @Resource(mappedName = "java:/topic/ReplicacionCajasInsertarListaEspecialRevisionTopic")
    private Topic insertarListasEspecialTopic;

    @Resource(mappedName = "java:/topic/ReplicacionParametrosAsopagosTopic")
    private Topic parametrosAsopagosTopic;

    @Resource(mappedName = "java:/topic/ReplicacionParametrosSubisidioTopic")
    private Topic parametrosSubsidioTopic;

    @Resource(mappedName = "java:/topic/ReplicacionValoresAnualesTopic")
    private Topic valoresAnualesTopic;
    
    @Resource(mappedName = "java:/topic/ReplicacionListaChequeoTopic")
    private Topic listaChequeoTopic;
    
    @Resource(mappedName = "java:/topic/ListaChequeoCreacionClasificacion")
    private Topic listaChequeoCreacionClasificacionTopic;
    
    @Resource(mappedName = "java:/topic/ListaChequeoActualizacionClasificacion")
    private Topic listaChequeoActualizacionClasificacionTopic;
    
    @Resource(mappedName = "java:/topic/ListaChequeoCreacionClasificacionPorCaja")
    private Topic listaChequeoCreacionClasificacionPorCajaTopic;
    
    @Resource(mappedName = "java:/topic/ListaChequeoActualizacionClasificacionPorCaja")
    private Topic listaChequeoActualizacionClasificacionPorCajaTopic;

    @EJB
    private ConsultaTablasParametrizables consultaTablaParametrizables;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ParametroBusiness.class);

    /**
     * Objeto utilizado para identificar la clase correspondiente a la cual
     * pertenece el String JSON recibido.
     */
    private ObjectMapper objectMapper;
    /**
     * Objeto utilizar para encapsular la información y el evento a replicar
     * en los demas sistemas de las CCF.
     */
    private ParametroReplicacionDTO parametroReplicacion;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Referencia al logger
     * 
     * @return
     */

    /**
     * Metodo que permite la creación de parametros de forma genérica.
     * 
     * @param parametro:
     *        Datos de la entidad a modificar. Es obligatorio que venga el
     *        Id.
     * @param nombreClase:
     *        Nombre de la clase tipo entity que se va a persistir en base
     *        de datos.
     * @return respuesta estandar según documento de lineamientos de servicios
     *         REST.
     */
    @Override
    public void crearParametro(String parametro, String nombreTabla) {
        try {
            String nombreClase = obtenerNombreClase(nombreTabla);
            Object entity = objectMapper.readValue(parametro, Class.forName(nombreClase));
            entityManager.persist(entity);
            entityManager.flush();
            sendMessage(parametrosAsopagosTopic, parametro, nombreClase, EnumAccion.CREAR);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al crear el parametro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Metodo que permite la modificación de parametros de forma genérica
     * 
     * @param parametro:
     *        Datos de la entidad a modificar. Es obligatorio que venga el
     *        Id del elemento a modificar.
     * @param nombreClase:
     *        Nombre de la clase tipo entity que se va a persistir en base
     *        de datos.
     */
    @Override
    public void modificarParametro(String parametro, String nombreTabla) {
        try {
            String nombreClase = obtenerNombreClase(nombreTabla);
            Object entity = objectMapper.readValue(parametro, Class.forName(nombreClase));
            entityManager.merge(entity);
            entityManager.flush();
            sendMessage(parametrosAsopagosTopic, parametro, nombreClase, EnumAccion.ACTUALIZAR);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al modificar el parametro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Metodo que permite eliminar parametros de forma genérica
     * 
     * @param idEntidad:
     *        Datos de la entidad a modificar. Es obligatorio que contenga
     *        el Id del elemento a eliminar.
     * @param nombreClase:
     *        Nombre de la clase tipo entity que se va a persistir en base
     *        de datos.
     * @return respuesta estandar según documento de lineamientos de servicios
     *         REST.
     */
    @Override
    public void eliminarParametro(String idEntidad, String nombreTabla) {
        try {
            String nombreClase = obtenerNombreClase(nombreTabla);
            Object entity = consultarParametroPorID(idEntidad, nombreClase);
            entityManager.remove(entity);
            entityManager.flush();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al eliminar el parametro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Metodo que permite la modificación de parametros de forma genérica
     * 
     * @param idEntidad:
     *        id de la entidad a consultar.
     * @param nombreClase:
     *        Nombre de la clase tipo entity que se va a persistir en base
     *        de datos.
     */
    @Override
    public Object consultarParametroPorID(String idEntidad, String nombreClase) {
        try {
            // Se parsea idEntidad al tipo de dato marcado como @Id de la clase @Entity
            Object idParam = getValueWithIdType(Class.forName(nombreClase), idEntidad);
            return entityManager.find(Class.forName(nombreClase), idParam);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al consultar el parametro", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Metodo auxiliar que devuelve un objeto parseado al tipo de dato
     * del campo marcado con la anotacion @Id de una clase @Entity
     * @param clazz
     * @return
     */
    private Object getValueWithIdType(Class<?> clazz, Object paramToCast) {
        Object parametroCasteado = null;
        try {
            //Se obtiene el tipo de dato del elemento @Id del entity
            Class<?> type = entityManager.getMetamodel().entity(clazz).getIdType().getJavaType();

            Method[] metodos = type.getMethods();
            //Se obtiene el metodo "Parse" para realizar el casting
            for (int i = 0; i < metodos.length; i++) {
                if (metodos[i].getName().contains("parse") && metodos[i].getParameterCount() == 1) {
                    parametroCasteado = metodos[i].invoke(type, paramToCast);
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al consultar obtener el valor con el tipo del ID", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return parametroCasteado;
    }

    /**
     * Obtener nombre de la clase asociada (ruta qualificada entidad )a la tabla
     * @param nombreTabla
     * @return
     */
    public String obtenerNombreClase(String nombreTabla) {
        return consultaTablaParametrizables.getTablasParametros().get(nombreTabla).getNombreClase();
    }

    public ParametroReplicacionDTO getParametroReplicacion() {
        return parametroReplicacion;
    }

    public void setParametroReplicacion(ParametroReplicacionDTO parametroReplicacion) {
        this.parametroReplicacion = parametroReplicacion;
    }

    /**
     * Nombres de las tablas parametricas
     * @see com.asopagos.parametros.service.ParametroService#listaTablaParametros()
     */
    @Override
    public List<ElementoListaDTO> listaTablaParametros() {

        List<Integer> indices = new ArrayList<>();
        List<ElementoListaDTO> lista = consultaTablaParametrizables.listaTablaParametros();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdentificador() != null && (lista.get(i).getIdentificador().equals("Requisito")
                    || lista.get(i).getIdentificador().equals("RequisitoCajaClasificacion"))) {
                indices.add(i);
            }
        }

        if (!indices.isEmpty()) {
            lista.remove(indices.get(0).intValue());
            lista.remove(indices.get(1).intValue() - 1);
        }

        return lista;
    }

    /**
     * Informacion necesaria para el tratamiento dinamico de una tabla parametrizable
     * @see com.asopagos.parametros.service.ParametroService#informacionTablasParametricas(java.lang.String)
     */
    @Override
    public TablaParametroDTO informacionTablasParametricas(String nombreTabla) {

        return consultaTablaParametrizables.informacionTablasParametricas(nombreTabla);

    }

    /**
     * Método que obtiene los registros de una entidad marcada como parametrica dentro de la aplicacion
     * @see com.asopagos.parametros.service.ParametroService#datosTablasParametricas(java.lang.String)
     */
    @Override
    public List<Object> datosTablasParametricas(String nombreTabla, UriInfo uri, HttpServletResponse response) {
        TablaParametro tablaParametroCache = consultaTablaParametrizables.getTablasParametros().get(nombreTabla);
        
        if(tablaParametroCache == null){
            return Collections.emptyList();
        }
        
        List<Object> listaResultados = new ArrayList<>();    
        List<String> atributosConsulta = tablaParametroCache.getNombresAtributos();
        List<Object[]> resultados = JPAUtils.consultaEntidad(entityManager, tablaParametroCache.getNombreClase(), atributosConsulta);
        for (Object[] row : resultados) {
            List<DatosTablaParametrizableDTO> listaDatos = new ArrayList<>();
            for (int i = 0; i < row.length; i++) {
                if (row[i] == null) {
                    listaDatos.add(new DatosTablaParametrizableDTO(atributosConsulta.get(i), null));
                }
                else {
                    listaDatos.add(new DatosTablaParametrizableDTO(atributosConsulta.get(i), row[i].toString()));
                }
            }
            listaResultados.add(listaDatos);
        }
        return listaResultados;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.parametros.service.ParametroService#replicacionParametrosSubsidio(ParametrizacionCondicionesSubsidioCajaDTO)
     */
    @Override
    public void replicacionParametrosSubsidio(ParametrizacionCondicionesSubsidioCajaDTO parametrizacionCondicionesSubsidioCaja) {
        try {
            Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
            String mensaje = gson.toJson(parametrizacionCondicionesSubsidioCaja);
            GestionarCondiciones gestionarCondiciones = new GestionarCondiciones(parametrizacionCondicionesSubsidioCaja);
            gestionarCondiciones.execute();
            sendMessage(parametrosSubsidioTopic, mensaje);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al replicar parametro de subsidio", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.parametros.service.ParametroService#replicacionValoresAnuales(java.util.List)
     */
    @Override
    public void replicacionValoresAnuales(List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq) {
        try {
            Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
            String mensaje = gson.toJson(parametrizacionesLiq);
            GestionarConceptos gestionarConceptos = new GestionarConceptos(parametrizacionesLiq);
            gestionarConceptos.execute();
            sendMessage(valoresAnualesTopic, mensaje);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al replicar valores anuales", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void replicarInsercionCajaListaEspecialRevision(String mensaje) {
        try {
            sendMessage(insertarListasEspecialTopic, mensaje);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al enviar mensaje para inserción en lista de especial revisión", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void replicarActualizacionListaEspecialRevision(String mensaje) {
        try {
            sendMessage(actualizarListaEspecialTopic, mensaje);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al enviar mensaje para actualización en lista de especial revisión", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    @Override
    public void replicarListaChequeo(Requisito requisito) {
        try {
            Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
            String mensaje = gson.toJson(requisito);
            sendMessage(listaChequeoTopic, mensaje);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado al enviar mensaje para actualización en lista de especial revisión", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void replicarCreacionListaChequeoClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
        Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
        String mensaje = gson.toJson(requisitosCajaClasificacion);
        sendMessage(listaChequeoCreacionClasificacionTopic, mensaje);
    }

    @Override
    public void replicarActualizacionListaChequeoClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
        Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
        String mensaje = gson.toJson(requisitosCajaClasificacion);
        sendMessage(listaChequeoActualizacionClasificacionTopic, mensaje);
    }

    @Override
	public void replicarCreacionListaChequeoClasificacionPorCaja(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
    	Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
    	List<RequisitoCajaClasificacionDTO> lista = new ArrayList<>();
    	String mensaje;
    	for (RequisitoCajaClasificacionDTO rccDTO : requisitosCajaClasificacion) {
			lista.add(rccDTO);
			mensaje = gson.toJson(rccDTO);
			sendMessage(listaChequeoCreacionClasificacionPorCajaTopic, mensaje);
			lista.clear();
		}
	}

	@Override
	public void replicarActualizacionListaChequeoClasificacionPorCaja(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
		Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
    	List<RequisitoCajaClasificacionDTO> lista = new ArrayList<>();
    	String mensaje;
    	for (RequisitoCajaClasificacionDTO rccDTO : requisitosCajaClasificacion) {
			lista.add(rccDTO);
			mensaje = gson.toJson(rccDTO);
			sendMessage(listaChequeoActualizacionClasificacionPorCajaTopic, mensaje);
			lista.clear();
		}
		
	}

    private void sendMessage(Topic topic, String mensaje) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);
            connection.start();
            TextMessage message = session.createTextMessage();
            message.setText(mensaje);
            producer.send(message);
        } catch (JMSException e) {
            throw new TechnicalException("Error al enviar el mensaje: " + mensaje, e);
        }
    }

    private void sendMessage(Topic topic, String mensaje, String nombreEntity, EnumAccion accion) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);
            connection.start();
            TextMessage message = session.createTextMessage();
            message.setStringProperty("entidad", nombreEntity);
            message.setStringProperty("accion", accion.name());
            message.setStringProperty("JMSXGroupID", nombreEntity);
            message.setText(mensaje);
            producer.send(message);
        } catch (JMSException e) {
            throw new TechnicalException("Error al enviar el mensaje: " + mensaje, e);
        }
    }
}