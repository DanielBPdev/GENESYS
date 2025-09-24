package com.asopagos.parametros.publicador.ejb;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import com.asopagos.parametros.publicador.service.ParametrosService;
import com.asopagos.rest.exception.TechnicalException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParametrosBusiness implements ParametrosService {

    private static final String FORMATO_FECHA = "EEE, dd MMM yyyy HH:mm:ss zzz";

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/topic/ReplicacionCambioEstadoListaEspecialRevisionTopic")
    private Topic cambioEstadoListaTopic;

    @Resource(mappedName = "java:/topic/ReplicacionInsercionListaEspecialRevisionTopic")
    private Topic insercionListaTopic;

    @Override
    public void replicarInsercionListaEspecialRevision(ListaEspecialRevision listaEspecialRevision) {
        Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
        String mensaje = gson.toJson(listaEspecialRevision);
        enviarMensaje(this.insercionListaTopic, mensaje);
    }

    @Override
    public void replicarCambioEstadoListaEspecialRevision(ListaEspecialRevision listaEspecialRevision) {
        listaEspecialRevision.setIdListaEspecialRevision(null);
        Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
        String mensaje = gson.toJson(listaEspecialRevision);
        enviarMensaje(this.cambioEstadoListaTopic, mensaje);
    }

    private void enviarMensaje(Topic topic, String mensaje) {
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
}
