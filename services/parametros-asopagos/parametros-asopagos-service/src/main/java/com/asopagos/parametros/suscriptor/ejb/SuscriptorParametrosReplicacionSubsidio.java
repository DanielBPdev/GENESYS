package com.asopagos.parametros.suscriptor.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.subsidiomonetario.clients.GestionarCondiciones;
import com.asopagos.subsidiomonetario.dto.ParametrizacionCondicionesSubsidioCajaDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@MessageDriven(name = "ReplicacionParametrosSubsidioCajaTopic", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "ReplicacionParametrosSubsidioCajaTopic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "clientId", propertyValue = "${ccf.clientId}_ParametrosSubisidio"),
        @ActivationConfigProperty(propertyName = "maxSessions" , propertyValue="1"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "selector = '${ccf.codigo}'"),
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "${ccf.clientId}_ParametrosSubisidio") })

public class SuscriptorParametrosReplicacionSubsidio  implements MessageListener {

    private static final ILogger logger = LogManager.getLogger(SuscriptorParametrosReplicacionSubsidio.class);
    
    @Override
    public void onMessage(Message rcvMessage) {
        logger.debug("Recibí un mensaje :) " + rcvMessage);
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                logger.debug("Mensaje recibido" + msg.getText());
                String obj = msg.getText();
                Gson gson = new GsonBuilder()
                        .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
                ParametrizacionCondicionesSubsidioCajaDTO parametrizacionSubsidio = gson.fromJson(obj, ParametrizacionCondicionesSubsidioCajaDTO.class);
                
                GestionarCondiciones gestionarCondiciones = new GestionarCondiciones(parametrizacionSubsidio);
                gestionarCondiciones.execute();
                
            } else {
                logger.debug("Mensaje incorrecto" + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new TechnicalException("No se pudo obtener el texto del mensaje", e);
        }
    }
}
