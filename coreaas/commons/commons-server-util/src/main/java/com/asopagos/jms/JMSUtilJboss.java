package com.asopagos.jms;

import java.io.Serializable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * Clase utilitaria encargada de realizar la administración y envóo de mensajes
 * a las colas de servicio de una aplicación cuyo contenedor sea de tipo JBoss.
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
public class JMSUtilJboss {

    /**
     * Componente que genera la conexión para la obtención de las colas de
     * servicio de una aplicación.
     */
    private static ConnectionFactory connectionFactory;

    /**
     * cola
     */
    private static Queue queue;

    /**
     * Componente usado para el manejo de log de la aplicación.
     */
    private static final ILogger logger = LogManager.getLogger(JMSUtilJboss.class);

    /**
     * Método encargado de enviar mensajes a las colas de servicio de una
     * aplicación.
     * 
     * @param object
     *        , Contiene la información del objeto que se quiere enviar a
     *        una cola de servicio o MDB.
     * @param queue
     *        , Contiene la información del nombre de la cola de servicios
     *        que se quiere enviar un mensaje.
     * @throws JMSException
     */
    public static void sendMenssages(Object object, String queueName) {
        logger.debug("Inicio - sendMenssages");
        Connection connection = null;
        Session session = null;
        MessageProducer messageProducer = null;

        ObjectMessage objectMessage = null;

        try {
            /**
             * propiedades necesarias para una conexion remota Properties prop =
             * new Properties(); prop.put(Context.INITIAL_CONTEXT_FACTORY,
             * "org.jnp.interfaces.NamingContextFactory");
             * prop.put(Context.PROVIDER_URL, "localhost:1099");
             * 
             * InitialContext jndiContext = new InitialContext(prop);
             */
            InitialContext jndiContext = new InitialContext();
            // Se busca la cola para los mensajes
            queue = (Queue) jndiContext.lookup(queueName);
            connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
            objectMessage = session.createObjectMessage();
            objectMessage.setObject((Serializable) object);
            messageProducer.send(objectMessage);
            messageProducer.close();
            session.close();
            connection.close();
            logger.debug("Fin - sendMenssages");
        } catch (Exception e) {
            logger.error("Error Exception  " + e.getClass().getName());
            throw new RuntimeException("Error  JSMException  " + e.getClass().getName(), e);
        }

        logger.debug("Inicio - sendMenssages");
    }

}
