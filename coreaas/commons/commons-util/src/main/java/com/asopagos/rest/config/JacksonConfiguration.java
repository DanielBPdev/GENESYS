package com.asopagos.rest.config;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

/**
 * <b>Descripción:</b> Clase Provider de jAX-RS para configuraciones generales 
 * de la mensajería JSON con Jackson2
 * <b>Historia de Usuario: </b>Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfiguration implements ContextResolver<ObjectMapper> {
    
	/**
     * Referencia al ObjectMapper de Jackson
     */
    private final ObjectMapper objectMapper;
    
    /**
     * Constructor de la clase
     */
    public JacksonConfiguration() {
        
        objectMapper = new ObjectMapper();
        //objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa"));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        //Se necesita registrar el serializador para las instancias de java.sql.Date
        SimpleModule module = new SimpleModule();
        module.addSerializer(java.sql.Date.class, new DateSerializer()); 
        objectMapper.registerModule(module);     
    }
    
    /**
     * Método que retorna el objectMapper creado
     * @param objectType
     * @return El objeto objectMapper
     */
    @Override
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
    
}
