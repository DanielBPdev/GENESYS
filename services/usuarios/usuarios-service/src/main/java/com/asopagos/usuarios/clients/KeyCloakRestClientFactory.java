package com.asopagos.usuarios.clients;

import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;

public class KeyCloakRestClientFactory {

    /**
     * Retorna el cliente REST que representa el API
     * @return
     */
    public static KeyCloakRestClient getKeyCloakRestClient(String realm) {
        String endpoint = ((String) CacheManager.getConstante(ConstantesSistemaConstants.KEYCLOAK_ENDPOINT)).replace("{realm}", realm);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(endpoint);
        KeyCloakRestClient keyCloakRest = target.proxy(KeyCloakRestClient.class);
        return keyCloakRest;
    }

    /**
     * Retorna el cliente REST que representa el API
     * @return
     */
    public static KeyCloakRestClient getKeyCloakRestClient(MultivaluedMap<String, Object> queryParams) {
        String endpoint = (String) CacheManager.getConstante(ConstantesSistemaConstants.KEYCLOAK_ENDPOINT);
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(endpoint);
        KeyCloakRestClient jbpm = target.queryParams(queryParams).proxy(KeyCloakRestClient.class);
        return jbpm;
    }

}
