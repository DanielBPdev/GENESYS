package com.asopagos.notificaciones.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.constants.ConstansNotification;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.enums.TipoNotificacionEnum;
import com.asopagos.notificaciones.service.SendNotificacionesService;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * Colas de servicio de norificaciones
 * 
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = ConstansNotification.JAVAX_JSM_QUEUE),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = ConstansNotification.QUEUE_MAILS_NAME),
		@ActivationConfigProperty(propertyName = "DLQMaxResent", propertyValue = "0"), 
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSessions" , propertyValue="${property.mdb.mails.maxsessions:3}")})
public class NotificacionesMDB implements MessageListener {

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(NotificacionesMDB.class);

	@Inject
	private SendNotificacionesService sendNotificacionesService;

	/**
	 * Método que obtiene el mensaje de la cola y lanza el envío del email
	 * 
	 * @param Message
	 *            (Map&lt;String, Object&gt;) keys:
	 *            ConstansNotification.NOTIFICACION_PARAM : NotificacionDTO
	 *            ConstansNotification.USER_PARAM : UserDTO
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		logger.debug("Inicia onMessage(Message)");
		if (message instanceof ObjectMessage) {
			try {
				ObjectMessage objMessage = (ObjectMessage) message;
				Map<String, Object> messageMap;
				messageMap = (Map<String, Object>) objMessage.getObject();
				
				UserDTO userDTO;
				if (messageMap.containsKey(ConstansNotification.USER_PARAM)) {
                    userDTO = (UserDTO) messageMap.get(ConstansNotification.USER_PARAM);
                    
                    TipoNotificacionEnum tipoBotificacionParametrizada = null;
                    if (messageMap.containsKey(ConstansNotification.TIPO_NOTIFICACION_PARAMETRIZADA)) {
    				    tipoBotificacionParametrizada = (TipoNotificacionEnum) messageMap.get(ConstansNotification.TIPO_NOTIFICACION_PARAMETRIZADA);
    				}
    				
    				if (tipoBotificacionParametrizada != null) {
    					switch(tipoBotificacionParametrizada) {
    					case SIMPLE: 
    						NotificacionDTO notificacion;
    						if (messageMap.containsKey(ConstansNotification.NOTIFICACION_PARAM)) {
    		                    notificacion = (NotificacionDTO) messageMap.get(ConstansNotification.NOTIFICACION_PARAM);
    		                    if(notificacion != null && userDTO != null) {
        		                    enviarCorreo(notificacion, userDTO);
        		                }
    		                } else {
    		                    logger.error("Finaliza onMessage(Message): No parámetros no válidos");
    		                }
    						break;
    					case PARAMETRIZADA:
    						NotificacionParametrizadaDTO notificacionParametrizada;
    						if (messageMap.containsKey(ConstansNotification.NOTIFICACION_PARAM)) {
    					        notificacionParametrizada = (NotificacionParametrizadaDTO) messageMap.get(ConstansNotification.NOTIFICACION_PARAM);
    					        if(notificacionParametrizada != null && userDTO != null) {
        	                        enviarCorreoParametrizado(notificacionParametrizada, userDTO);
        	                    }
    	                    } else {
    	                        logger.error("Finaliza onMessage(Message): No parámetros no válidos");
    	                    }
    						break;
    					case MULTIPLE_POR_CONEXION:
    						logger.debug("OnMessage 1");
    						List<NotificacionParametrizadaDTO> notificacionesParametrizadas;
    						
    						if (messageMap.containsKey(ConstansNotification.NOTIFICACION_PARAM)) {
    							logger.debug("Las notificaciones fueron seteadas OK en el map");
    							notificacionesParametrizadas = (List<NotificacionParametrizadaDTO>) messageMap.get(ConstansNotification.NOTIFICACION_PARAM);
								if (notificacionesParametrizadas != null
										&& !notificacionesParametrizadas.isEmpty()
										&& userDTO != null) {
									enviarMultiplesCorreosPorConexion(notificacionesParametrizadas, userDTO);
        	                    }
								
    	                    } else {
    	                        logger.error("Finaliza onMessage(Message): No parámetros no válidos");
    	                    }
    						break;
    					default: logger.error("Finaliza onMessage(Message): No parámetros no válidos");
    					}
    				}
                } else {
                    logger.error("Finaliza onMessage(Message): No parámetros no válidos");
                }
			} catch (JMSException e) {
				logger.error("Finaliza onMessage(Message): No parámetros no válidos");
			} catch (Exception e) {
	            logger.error("Finaliza onMessage(Message): Error inesperado", e);
	        }
		} else {
			logger.error("Finaliza onMessage(Message): No parámetros no válidos");
		}
		logger.debug("Finaliza onMessage(Message)");
	}

	/**
	 * Método que se encarga de envíar un corre a la dirección especificada
	 * 
	 * @param notificacion
	 * @param userDTO
	 */
	private void enviarCorreo(NotificacionDTO notificacion, UserDTO userDTO) {
		logger.debug("Inicia enviarCorreo(NotificacionDTO, UserDTO)");
		sendNotificacionesService.enviarEmail(notificacion, userDTO);
		logger.debug("Finaliza enviarCorreo(NotificacionDTO, UserDTO)");
	}
	
	/**
     * Método que se encarga de envíar un corre a la dirección parametrizada
     * 
     * @param notificacion
     * @param userDTO
     */
    private void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion, UserDTO userDTO) {
        logger.debug("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");
        sendNotificacionesService.enviarEmailParametrizado(notificacion, userDTO);
        logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO, UserDTO)");
    }
    
    /**
     * Método que se encarga de envíar un corre a la dirección parametrizada
     * 
     * @param notificacion
     * @param userDTO
     */
    private void enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO> notificaciones, UserDTO userDTO) {
        logger.debug("Inicia enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO>, UserDTO)");
        sendNotificacionesService.enviarMultiplesCorreosPorConexion(notificaciones, userDTO);
        logger.debug("Finaliza enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO>, UserDTO)");
    }
}
