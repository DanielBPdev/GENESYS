    package com.asopagos.parametros.suscriptor.ejb;

import java.io.IOException;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import com.asopagos.afiliaciones.clients.CambiarEstadoRegistroLista;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.databind.ObjectMapper;

@MessageDriven(
        name = "ReplicacionListaEspecialRevision",
        activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(
                        propertyName = "destination",
                        propertyValue = "ReplicacionListaEspecialRevision"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
                @ActivationConfigProperty(propertyName = "clientId", propertyValue = "${ccf.clientId}_Lista"),
                @ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "1"),
                @ActivationConfigProperty(
                        propertyName = "subscriptionName",
                        propertyValue = "${ccf.clientId}_Lista") })

public class ReplicacionListaEspecialRevision implements MessageListener {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ReplicacionListaEspecialRevision.class);

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

                if (lista.getCajaCompensacion() != null && lista.getCajaCompensacion() != Integer
                        .valueOf(CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID).toString())) {
                    CambiarEstadoRegistroLista registrar = new CambiarEstadoRegistroLista(lista);
                    registrar.execute();
                }
            }
            else {
                logger.debug("Mensaje incorrecto" + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new TechnicalException("Error al leer el mensaje de la cola", e);
        } catch ( IOException e) {
            throw new TechnicalException("Error al convertir el mensaje a un objeto", e);        
        }
    }
}