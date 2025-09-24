package com.asopagos.parametros.suscriptor.ejb;

import java.util.Date;
import java.util.List;
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
import com.asopagos.enumeraciones.personas.EstadoListaEspecialRevisionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.parametros.service.ParametroService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@MessageDriven(name = "ReplicacionCambioEstadoListaEspecialRevisionTopic", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "ReplicacionCambioEstadoListaEspecialRevisionTopic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "clientId", propertyValue = "${ccf.clientId}_CambioEstadoListaEspecialRevision"),
        @ActivationConfigProperty(propertyName = "maxSessions" , propertyValue="1"),
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "${ccf.clientId}_CambioEstadoListaEspecialRevision") })

public class SuscriptorUpdateListaEspecialRevision implements MessageListener {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(SuscriptorUpdateListaEspecialRevision.class);
    
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
                ListaEspecialRevision listaActualizar = gson.fromJson(obj, ListaEspecialRevision.class);
                
                List<ListaEspecialRevision> listadoEspecialRevision = (List<ListaEspecialRevision>) entityManager.createNamedQuery(com.asopagos.parametros.suscriptor.constants.NamedQueriesConstants.BUSCAR_REGISTRO_LISTA_ESPECIAL_REVISION)
                        .setParameter("tipoIdentificacion", listaActualizar.getTipoIdentificacion())
                        .setParameter("numeroIdentificacion", listaActualizar.getNumeroIdentificacion())
                        .getResultList();
                
                if(listadoEspecialRevision != null && !listadoEspecialRevision.isEmpty()){
                    
                    ListaEspecialRevision lista = listadoEspecialRevision.get(0);
                    
                    actualizarRegistroLER(listaActualizar, lista);
                    
                    parametros.replicarActualizacionListaEspecialRevision(obj);
                }
                
                parametros.replicarActualizacionListaEspecialRevision(obj);
                
            } else {
                logger.debug("Mensaje incorrecto" + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param listaActualizar
     * @param lista
     */
    private void actualizarRegistroLER(ListaEspecialRevision listaActualizar, ListaEspecialRevision lista) {
        if (listaActualizar.getEstado() != null) {
            lista.setEstado(listaActualizar.getEstado());
        }
        if (listaActualizar.getComentario() != null) {
            lista.setComentario(listaActualizar.getComentario());
        }
        if (listaActualizar.getEstado().equals(EstadoListaEspecialRevisionEnum.INCLUIDO)) {
            lista.setFechaInicioInclusion(new Date());
            lista.setFechaFinInclusion(null);
        } else {
            lista.setFechaFinInclusion(new Date());
        }
        entityManager.merge(lista);
    }
}
