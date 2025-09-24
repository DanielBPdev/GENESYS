package com.asopagos.parametros.suscriptor.ejb;

import java.lang.reflect.Type;
import java.util.List;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import com.asopagos.listaschequeo.clients.ActualizarRequisitosCajaClasificacion;
import com.asopagos.listaschequeo.dto.RequisitoCajaClasificacionDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import main.java.com.asopagos.parametros.api.AbstractGeneradorUsuario;

@MessageDriven(name = "ListaChequeoActualizacionClasificacion", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "ListaChequeoActualizacionClasificacion"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "clientId", propertyValue = "${ccf.clientId}_listaChequeoActualizacion"),
        @ActivationConfigProperty(propertyName = "maxSessions" , propertyValue="1"),
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "${ccf.clientId}_listaChequeoActualizacion") })

public class ListaChequeoActualizacionClasificacion extends AbstractGeneradorUsuario implements MessageListener {

    private static final String FORMATO_FECHA = "EEE, dd MMM yyyy HH:mm:ss zzz";
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ListaChequeoActualizacionClasificacion.class);
    
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
                        .setDateFormat(FORMATO_FECHA).create();
                Type listType = new TypeToken<List<RequisitoCajaClasificacionDTO>>(){}.getType();
                List<RequisitoCajaClasificacionDTO> requisitoClasificacion = gson.fromJson(obj, listType);
                
                initContextUsuarioSistema();
                
                ActualizarRequisitosCajaClasificacion actualizarRequisitosCajaClasificacion = new ActualizarRequisitosCajaClasificacion(requisitoClasificacion);
                actualizarRequisitosCajaClasificacion.execute();
            } else {
                logger.debug("Mensaje incorrecto" + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new TechnicalException("No se pudo gestionar conceptos", e);
        }
    }
}
