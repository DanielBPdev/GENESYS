package com.asopagos.parametros.suscriptor.ejb;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.listaschequeo.clients.CrearRequisitosCajaClasificacion;
import com.asopagos.listaschequeo.dto.RequisitoCajaClasificacionDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import main.java.com.asopagos.parametros.api.AbstractGeneradorUsuario;

@MessageDriven(name = "ListaChequeoCreacionClasificacionPorCaja", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "ListaChequeoCreacionClasificacionPorCaja"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "clientId", propertyValue = "${ccf.clientId}_listaChequeoCreacionClasificacionPorCaja"),
        @ActivationConfigProperty(propertyName = "maxSessions" , propertyValue="1"),
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "${ccf.clientId}_listaChequeoCreacionClasificacionPorCaja") })

public class InsercionListaChequeoCaja extends AbstractGeneradorUsuario implements MessageListener {

	private static final String FORMATO_FECHA = "EEE, dd MMM yyyy HH:mm:ss zzz";
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(InsercionListaChequeoCaja.class);
    
	
	@Override
	public void onMessage(Message rcvMessage) {
		logger.debug("Recibí un mensaje :) " + rcvMessage);
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                logger.info("Mensaje recibido" + msg.getText());
                String obj = msg.getText();
                Gson gson = new GsonBuilder()
                        .setDateFormat(FORMATO_FECHA).create();
                Type listType = new TypeToken<List<RequisitoCajaClasificacionDTO>>(){}.getType();
                List<RequisitoCajaClasificacionDTO> requisitoClasificacion = gson.fromJson(obj, listType);

                List<RequisitoCajaClasificacionDTO> requisito;
                
                if(!requisitoClasificacion.isEmpty()){
                	initContextUsuarioSistema();
                	for (RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO : requisitoClasificacion) {
                		if(requisitoCajaClasificacionDTO.getIdCajaCompensacion() != null && verificarRequisitos(Integer.valueOf(requisitoCajaClasificacionDTO.getIdCajaCompensacion()))){
                        	
                        	requisito = new ArrayList<>();
                        	requisito.add(requisitoCajaClasificacionDTO);
                        	CrearRequisitosCajaClasificacion crearRequisitosCajaClasificacion = new CrearRequisitosCajaClasificacion(requisito);
        	                crearRequisitosCajaClasificacion.execute();
                        }
					}
                }
            } else {
                logger.info("Mensaje incorrecto" + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new TechnicalException("No se pudo gestionar conceptos", e);
        }
		
	}
	
    /**
     * Compara el id de la caja que viene en la lista de requisitos con el id parametrizado en la caja
     * para determinar si son iguales.
     * 
     * @param codigoCajaDatosEntrada
     * 			el código de la caja entrante.
     * 
     * @return true si la el id de la caja entrante es igual a la caja actual. false en caso contrario.
     */
    private boolean verificarRequisitos(Integer codigoCajaDatosEntrada) {
    	Integer codigoCajaActual = Integer.valueOf(CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID).toString());
        return codigoCajaDatosEntrada != null &&  codigoCajaActual != null && codigoCajaActual.intValue() == codigoCajaDatosEntrada.intValue();
    }

}
