package com.asopagos.tareashumanas.client;

import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.rest.security.filter.AddHeaderAuthenticationToken;

public class JBPMClientFactory {
	/**
     * Retorna el cliente REST que representa el API 
     * @return
     */
	public static JBPMClient getJBPMClient() {
		String endpoint=(String) CacheManager.getConstante(ConstantesSistemaConstants.BPMS_BUSINESS_CENTRAL_ENDPOINT);
    	ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(endpoint);
        
        //se adiciona el filtro que adiciona en todas la peticiones el filtro con el
        //token de autenticación
        client.register(new AddHeaderAuthenticationToken());
        JBPMClient jbpm = target.proxy(JBPMClient.class);
		return jbpm;
	}
	
	/**
     * Retorna el cliente REST que representa el API 
     * @return
     */
	public static JBPMClient getJBPMClient(MultivaluedMap<String, Object> queryParams) {
		String endpoint=(String) CacheManager.getConstante(ConstantesSistemaConstants.BPMS_BUSINESS_CENTRAL_ENDPOINT);
    	ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(endpoint);
        
        //se adiciona el filtro que adiciona en todas la peticiones el filtro con el
        //token de autenticación
       
        client.register(new AddHeaderAuthenticationToken());
        JBPMClient jbpm = target.queryParams(queryParams).proxy(JBPMClient.class);
		return jbpm;
	}
}
