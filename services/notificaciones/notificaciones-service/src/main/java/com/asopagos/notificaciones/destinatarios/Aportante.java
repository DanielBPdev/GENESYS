package com.asopagos.notificaciones.destinatarios;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.constants.NamedQueriesConstants;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.util.ObtenerTipoSolicitud;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
public class Aportante extends DestinatarioNotificacion {
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(Aportante.class);
    
    /**
     * Indica si se autoriza el envio del comunicado.
     */
    Boolean noAutorizaEnvio = Boolean.FALSE;
    
    /**
     * Atributo que indica si el aportante es un empleador
     */
    Boolean esEmpleador;
    
    /**
     * Atributo que indica el id correspondiente a la solicitud gestionada
     */
    Long idSolicitud;
    
    /**
     * Atributo que indica el contexto de persistencia
     */
    EntityManager em;

    /** (non-Javadoc)
     * @see com.asopagos.notificaciones.destinatarios.DestinatarioNotificacion#obtenerCorreoDestinatario(com.asopagos.enumeraciones.core.TipoTransaccionEnum, java.util.Map, javax.persistence.EntityManager)
     */
    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager emn) {
        logger.debug("Inicia obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em)");

        //Persona por solicitud  de afiliacion de una persona, 
        //persona (nombre empleador)solicitud por afiliacion de empleador, 
        //Persona por solicitud de novedad de persona
        if (!parametros.containsKey(ID_SOLICITUD)) {
            logger.error("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) :No contiene el Id de Solicitud");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
        }
        else {
        	em = emn;
        	List<CorreoPrioridadPersonaDTO> correos = new ArrayList<CorreoPrioridadPersonaDTO>();
            TipoSolicitudEnum solicitante = ObtenerTipoSolicitud.obtenerTipoSolicitudPorTipoTransaccion(tipoTx);
            idSolicitud = (Long) parametros.get(ID_SOLICITUD);
            
            switch(solicitante){
            	case APORTES:{
            		correos = obtenerCorreoPersona(NamedQueriesConstants.CONSULTAR_AUTORIZACION_APORTE,NamedQueriesConstants.CONSULTAR_APORTANTE_EMPLEADOR,NamedQueriesConstants.CONSULTAR_APORTANTE_INDP_PENS);
            		break;
            	}
            	case APORTES_CORRECCION:{
            		correos = obtenerCorreoPersona(NamedQueriesConstants.CONSULTAR_AUTORIZACION_APORTE_CORRECCION,NamedQueriesConstants.CONSULTAR_APORTANTE_CORRECCION_EMPLEADOR,NamedQueriesConstants.CONSULTAR_APORTANTE_CORRECCION_INDP_PENS);
            		break;
            	}
            	case APORTES_DEVOLUCION:{
            		correos = obtenerCorreoPersona(NamedQueriesConstants.CONSULTAR_AUTORIZACION_APORTE_DEVOLUCION,NamedQueriesConstants.CONSULTAR_APORTANTE_DEVOLUCION_EMPLEADOR,NamedQueriesConstants.CONSULTAR_APORTANTE_DEVOLUCION_INDP_PENS);
            		break;
            	}
            	default:{
            		logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em) : No se resuelve la consulta para el tipo de solicitante");
                    return null;  
            	}
            }       
            logger.debug("Finaliza obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Long> parametros, EntityManager em)");
            return correos;
        }
    }
    
    /**
     * Método encargado de convertir una lista de tipo object[] a
     * una lista de tipo CorreoPrioridadPersonaDTO
     * 
     * @author Francisco Alejandro Hoyos Rojas
     * @param destinatariosObject lista tipo object[] que contiene ubiEmail, perId, ubiAutorizacionEnvioEmail, empId(Empresa) para empresas, para independiente y pensionados no trae el empId
     * @param tipoAportante NamedQueriesConstant que varía según el tipo de aportante al que se le va a consultar el estado de afiliación
     * @return lista tipo CorreoPrioridadPersonaDTO
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<CorreoPrioridadPersonaDTO> convertToListCorreoPrioridadPersonaDTO(List<Object[]>destinatariosObject, String tipoAportante){
    	logger.debug("Inicia convertToListDestinatarioDTO(List<Object[]>destinatariosObject, String tipoAportante)");
        List<CorreoPrioridadPersonaDTO> correos = new ArrayList<>();
        try {
        	for(Object[] destinatarioObject: destinatariosObject){
        		String email = (String) destinatarioObject[0];
        		Long perId =  destinatarioObject[1] !=null ? ((BigInteger) destinatarioObject[1]).longValue() : null;
        		Boolean autorizacionEnvioEmail = (Boolean) destinatarioObject[2];
        		Long empId = destinatarioObject.length == 4 ? destinatarioObject[3] != null ? ((BigInteger) destinatarioObject[3]).longValue() : null : null;
        		// El aportante presenta una ubicación que contiene un email
        		if(email != null && !email.isEmpty()) {
        			correos.add(new CorreoPrioridadPersonaDTO(email, perId, null, empId, autorizacionEnvioEmail != null ? autorizacionEnvioEmail : noAutorizaEnvio));
        		}else{
        			// El aportante no presenta ninguna ubicación o su email es nulo, se verifica si su estado es no formalizado sin afiliacion con aportes
        			agregarInformacionNotificacionAportanteNoFormalizadoConAportes(perId,empId,correos,tipoAportante);
        		}
        	}
        }catch(Exception e) {
        	logger.error("Finaliza convertToListCorreoPrioridadPersonaDTO(List<Object[]>destinatariosObject, String tipoAportante) :Error técnico inesperado: "+e);
        	return correos;
        }
        logger.debug("Finaliza convertToListCorreoPrioridadPersonaDTO(List<Object[]>destinatariosObject, String tipoAportante)");
        return correos;    
    }
    
    /**
     * Métdodo encargado de agregar el perId y empId(Empresa) en caso de que el estado del aportante sea no formalizado sin afiliacion con aportes
     * esto es necesario para la visualización de las notificaciones por vista 360
     * 
     * @author Francisco Alejandro Hoyos Rojas
     * @param perId perId del aportante
     * @param tipoAportante NamedQueriesConstant que varía según el tipo de aportante al que se le va a consultar el estado de afiliación
     * @param correos lista de correos priorizados
     */
    private void agregarInformacionNotificacionAportanteNoFormalizadoConAportes(Long perId,Long empId,List<CorreoPrioridadPersonaDTO> correos, String tipoAportante) {
    	logger.debug("Inicia agregarInformacionNotificacionAportanteNoFormalizadoConAportes(Long perId, Long empId, List<CorreoPrioridadPersonaDTO> correos, String tipoAportante)");
    	try {
    		String estadoEmpleador, estadoIndependiente, estadoPensionado;
    		EstadoAfiliadoEnum estadoAfiliadoEmpleador = null, estadoAfiliadoIndependiente = null, estadoAfiliadoPensionado = null;
    		Object[] estados =  (Object[]) em.createNamedQuery(tipoAportante).setParameter("idPersona", perId).getSingleResult();
    		if(estados.length==1) {
        		estadoEmpleador = (String) estados[0];
        		estadoAfiliadoEmpleador = estadoEmpleador != null && !estadoEmpleador.isEmpty() ? EstadoAfiliadoEnum.valueOf(estadoEmpleador) : null;
    		}else {
    			estadoIndependiente = (String) estados[0];
    			estadoAfiliadoIndependiente = estadoIndependiente != null && !estadoIndependiente.isEmpty() ? EstadoAfiliadoEnum.valueOf(estadoIndependiente) : null;
    			estadoPensionado = (String) estados[1];
    			estadoAfiliadoPensionado = estadoIndependiente != null && !estadoPensionado.isEmpty() ? EstadoAfiliadoEnum.valueOf(estadoPensionado) : null;
    		}
    		if(esEmpleador && estadoAfiliadoEmpleador == EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES) {
    			// El aportante es un empleador se agrega el perId, empId para la creación de la notificación, no se agrega ningún correo
    			correos.add(new CorreoPrioridadPersonaDTO(perId,empId,Boolean.TRUE));
    		}else if(!esEmpleador && estadoAfiliadoIndependiente == EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES || estadoAfiliadoPensionado == EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES ) {
    			// El aportante es independiente o pensionado se agrega el perId para la creación de la notificación, no se agrega ningún correo
    			correos.add(new CorreoPrioridadPersonaDTO(perId,Boolean.TRUE));
    		}
    		logger.debug("Finaliza agregarInformacionNotificacionAportanteNoFormalizadoConAportes(Long perId,Long empId, List<CorreoPrioridadPersonaDTO> correos, String tipoAportante)");
    	}catch(NoResultException e) {
			logger.error("Finaliza agregarInformacionNotificacionAportanteNoFormalizadoConAportes(Long perId,Long empId, List<CorreoPrioridadPersonaDTO> correos, String tipoAportante) :Error técnico inesperado No se pudo obtener el estado de la persona: "+e);
		}
    }
    
    /**
     *  Método encargado de obtener una lista de correos priorizados por el tipo de transacción y el tipo de aportante
     *  
     * @author Francisco Alejandro Hoyos Rojas
     * @param tipoAutorizacion tipo de autorización que depende del tipo de solicitud
     * @param tipoTransaccionEmpleador tipo de transacción para cuando el aportante sea un empleador 
     * @param tipoTransaccionIndPens tipo de transacción para cuando el aportante sea independiente o pensionado
     * @return Lista de correos priorizados
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<CorreoPrioridadPersonaDTO> obtenerCorreoPersona(String tipoAutorizacion, String tipoTransaccionEmpleador, String tipoTransaccionIndPens) {
    	logger.debug("Inicia obtenerCorreoPersona(String tipoAutorizacion, String tipoTransaccionEmpleador, String tipoTransaccionIndPens)");
    	List<CorreoPrioridadPersonaDTO> correos = new ArrayList<>();
    	try {
    		try {
    			em.createNamedQuery(tipoAutorizacion, Long.class).setParameter("idSolicitud", idSolicitud).getSingleResult();
       		 	esEmpleador = Boolean.TRUE;
    		}catch (Exception e) {
    			esEmpleador = Boolean.FALSE;
            }
            if(esEmpleador.equals(true)){
            	correos = convertToListCorreoPrioridadPersonaDTO(em.createNamedQuery(tipoTransaccionEmpleador).setParameter("idSolicitud", idSolicitud).getResultList(),NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_EMPLEADOR);
            } else {
            	correos = convertToListCorreoPrioridadPersonaDTO(em.createNamedQuery(tipoTransaccionIndPens).setParameter("idSolicitud", idSolicitud).getResultList(),NamedQueriesConstants.CONSULTAR_ESTADO_AFILIACION_PERSONA);
            }
        } catch (Exception e){
            logger.error("Finaliza obtenerCorreoPersona(String tipoAutorizacion, String tipoTransaccionEmpleador, String tipoTransaccionIndPens) :Error técnico inesperado:"+e);
            return null;
        }
    	logger.debug("Finaliza obtenerCorreoPersona(String tipoAutorizacion, String tipoTransaccionEmpleador, String tipoTransaccionIndPens)");
    	return correos;
    }
}
