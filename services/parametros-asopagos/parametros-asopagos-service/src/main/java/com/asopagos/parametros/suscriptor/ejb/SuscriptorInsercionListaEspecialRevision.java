package com.asopagos.parametros.suscriptor.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.parametros.service.ParametroService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@MessageDriven(name = "ReplicacionInsercionListaEspecialRevisionTopic", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "ReplicacionInsercionListaEspecialRevisionTopic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "clientId", propertyValue = "${ccf.clientId}_InsercionListaEspecialRevision"),
        @ActivationConfigProperty(propertyName = "maxSessions" , propertyValue="1"),
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "${ccf.clientId}_InsercionListaEspecialRevision") })

public class SuscriptorInsercionListaEspecialRevision implements MessageListener {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(SuscriptorInsercionListaEspecialRevision.class);

    /**
     * Referencia a la unidad de persistencia del servicio
     */
    @PersistenceContext(unitName = "parametros_PU")
    private EntityManager entityManager;
    
    @EJB
    private ParametroService parametros;
    
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
                ListaEspecialRevision lista = gson.fromJson(obj, ListaEspecialRevision.class);
                
                if(lista.getIdListaEspecialRevision()!= null){
                    lista.setIdListaEspecialRevision(null);
                }
                entityManager.persist(lista);
                parametros.replicarInsercionCajaListaEspecialRevision(obj);
            } else {
                logger.debug("Mensaje incorrecto" + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
