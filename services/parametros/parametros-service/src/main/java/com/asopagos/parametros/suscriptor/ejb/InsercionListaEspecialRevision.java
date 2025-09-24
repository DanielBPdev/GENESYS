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
import com.asopagos.afiliaciones.clients.RegistrarPersonaEnListaEspecialRevision;
import com.asopagos.afiliaciones.dto.ListaEspecialRevisionDTO;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.asopagos.parametros.api.AbstractGeneradorUsuario;

@MessageDriven(name = "ReplicacionCajasInsertarListaEspecialRevisionTopic", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "ReplicacionCajasInsertarListaEspecialRevisionTopic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "clientId", propertyValue = "${ccf.clientId}_InsertarLista"),
        @ActivationConfigProperty(propertyName = "maxSessions" , propertyValue="1"),
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "${ccf.clientId}_InsertarLista") })

public class InsercionListaEspecialRevision extends AbstractGeneradorUsuario implements MessageListener {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ActualizacionListaEspecialRevision.class);
    
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

                String obj = msg.getText();

                ObjectMapper objectMapper = new ObjectMapper();
                ListaEspecialRevision lista = objectMapper.readValue(obj, ListaEspecialRevision.class);                                
                
                if(lista.getCajaCompensacion()!= null && lista.getCajaCompensacion() != Integer.valueOf(CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID).toString())){
                    
                	initContextUsuarioSistema();
                	
                    ListaEspecialRevisionDTO lerDTO = ListaEspecialRevisionDTO.convertListaEspecialRevisionDTO(lista);
                    RegistrarPersonaEnListaEspecialRevision registrar = new RegistrarPersonaEnListaEspecialRevision(lerDTO);
                    registrar.execute();
                    registrar.getResult();
                }
                
                
            } else {
                logger.debug("Mensaje incorrecto" + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new TechnicalException("Error al leer el mensaje de la cola", e);
        } catch ( IOException e) {
            throw new TechnicalException("Error al convertir el mensaje a un objeto", e);        
        }

    }
}