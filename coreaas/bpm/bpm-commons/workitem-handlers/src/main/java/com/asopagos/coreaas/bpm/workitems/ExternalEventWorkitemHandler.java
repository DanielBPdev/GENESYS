package com.asopagos.coreaas.bpm.workitems;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jbpm.process.workitem.jms.JMSSendTaskWorkItemHandler;
import org.kie.api.runtime.process.WorkItem;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExternalEventWorkitemHandler extends org.jbpm.process.workitem.jms.JMSSendTaskWorkItemHandler {

    private static final String DATA = "Data";
	private static final String EVENTO = "evento";
	private static ILogger logger = LogManager.getLogger(JMSSendTaskWorkItemHandler.class);
    
    public ExternalEventWorkitemHandler(String connectionFactoryName, String destinationName) {
        super(connectionFactoryName, destinationName);
    }
    
    @Override
    protected Message createMessage(WorkItem workItem, Session session) throws JMSException {
    	TextMessage message = session.createTextMessage();
        Object data = workItem.getParameter(DATA);
        String body = null;
        if(data instanceof Map){
            Map<String, String> dataMap = (Map<String, String>)data;
            String evento = dataMap.get(EVENTO);
            addPropertyIfExists(EVENTO, evento, message);
            dataMap.remove(EVENTO);
            try {
                body = new ObjectMapper().writeValueAsString(dataMap);
                message.setText(body);
                logger.debug(body);
            } catch (JsonProcessingException e) {
                logger.error("Error convirtiendo los datos del evento", e);
            }
        }
        return message;
    }
}
