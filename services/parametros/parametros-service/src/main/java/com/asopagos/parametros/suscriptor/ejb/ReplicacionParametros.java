package com.asopagos.parametros.suscriptor.ejb;

import java.io.IOException;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.parametros.enums.EnumAccion;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.asopagos.parametros.api.AbstractGeneradorUsuario;

@MessageDriven(name = "ReplicacionParametrosAsopagosTopic", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "ReplicacionParametrosAsopagosTopic"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "${ccf.clientId}_ParametrosAsopagos"),
		@ActivationConfigProperty(propertyName = "maxSessions" , propertyValue="1"),
		@ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "${ccf.clientId}_ParametrosAsopagos") })

public class ReplicacionParametros extends AbstractGeneradorUsuario implements MessageListener {
	
	/**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ReplicacionParametros.class);
    
    /**
	 * Referencia a la unidad de persistencia del servicio
	 */
	@PersistenceContext(unitName = "parametros_PU")
	private EntityManager entityManager;
	
	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message rcvMessage) {
		logger.debug("Recibí un mensaje :) " + rcvMessage);
		TextMessage msg;
		try {
			if (rcvMessage instanceof TextMessage) {
				msg = (TextMessage) rcvMessage;
				logger.debug("Mensaje recibido" + msg.getText());
				
				String nombreClase = msg.getStringProperty("entidad");
				String accion = msg.getStringProperty("accion");
				EnumAccion enumAccion = EnumAccion.valueOf(accion);
				String obj = msg.getText();

				ObjectMapper objectMapper = new ObjectMapper();
				Object entity = objectMapper.readValue(obj, Class.forName(nombreClase));								
				
				initContextUsuarioSistema();
				
				switch (enumAccion) {
                    case CREAR:				    
                        entityManager.persist(entity);					
                        break;
                    case ACTUALIZAR:
                        entityManager.merge(entity);										
                        break;
                    case ELIMINAR:
                        entityManager.remove(entity);
                        break;
                    default:
                        throw new IllegalStateException(enumAccion + " operación no permitida");
				}
				
			} else {
				logger.debug("Mensaje incorrecto" + rcvMessage.getClass().getName());
			}
		} catch (JMSException e) {
			throw new TechnicalException("Error al leer el mensaje de la cola", e);
		} catch ( IOException e) {
		    throw new TechnicalException("Error al convertir el mensaje a un objeto", e);		 
		} catch (ClassNotFoundException e) {
		    throw new TechnicalException("No se encontró la clase enviada en el mensaje", e);
		}
	}

}
