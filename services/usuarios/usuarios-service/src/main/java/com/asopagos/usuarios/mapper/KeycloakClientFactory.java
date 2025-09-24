package com.asopagos.usuarios.mapper;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.rest.exception.TechnicalException;

public class KeycloakClientFactory {

    public static final String APP_WEB = "app_web";
    public static final String INTEGRACION = "Integracion";
    public static final String USUARIOS_WEB = "usuarios_web";
    public static final String SYSTEM = "system";
    public static final String APP_WEB_ADMIN = "app_web_admin";
    private static final KeycloakClientFactory factory = new KeycloakClientFactory();
    

    public static KeycloakClientFactory getInstance() {
        return factory;
    }

    private KeycloakClientFactory() {
    }

    public KeycloakAdapter getKeycloakClient(String clienId) {
        switch (clienId) {
            case APP_WEB:
                return getWebClient();
            case APP_WEB_ADMIN:
                return getWebClientAdmin();
            case INTEGRACION:
                return getIntegracionDomainClient();
            case USUARIOS_WEB:
                return getWebPublicClient();
            case SYSTEM:
                return getSystemClient();
            default:
                throw new TechnicalException("El cliente solicitado no se encuentra configurado");
        }
    }
    
    private KeycloakAdapter getWebClient() {
        KeycloakAdapter adapter = new KeycloakAdapter();
        String serverUrl = (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_SERVER_URL);
        String realmName = (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_CLIENT_WEB_DOMAIN_NAME);
        String clientId = (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_CLIENT_WEB_CLIENT_ID);
        String clientSecret = (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_CLIENT_WEB_CLIENT_SECRET);

        //se valida que los parametros esten configurados
        validateClientCredentials(serverUrl, realmName, clientId, clientSecret);

        Keycloak kc = crearConexionConKeycloak(serverUrl, realmName, clientId, clientSecret);
        adapter.setKc(kc);
        adapter.setRealm(realmName);
        return adapter;
    }
    
    private KeycloakAdapter getWebClientAdmin() {
        KeycloakAdapter adapter = new KeycloakAdapter();
        String serverUrl = (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_SERVER_URL);
        String realmName = (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_CLIENT_WEB_DOMAIN_NAME);
        String clientId = (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_PUBLIC_CLIENT_ADMIN_ID);
        String clientSecret = (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_CLIENT_WEB_CLIENT_ADMIN_SECRET);

        //se valida que los parametros esten configurados
        validateClientCredentials(serverUrl, realmName, clientId, clientSecret);

        Keycloak kc = crearConexionConKeycloak(serverUrl, realmName, clientId, clientSecret);
        adapter.setKc(kc);
        adapter.setRealm(realmName);
        return adapter;
    }


    private KeycloakAdapter getIntegracionDomainClient() {
        KeycloakAdapter adapter = new KeycloakAdapter();
        String serverUrl = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_SERVER_URL);
        String realmName = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME);
        String clientId = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_INTEGRATION_WEB_CLIENT_ID);
        String clientSecret = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_INTEGRATION_WEB_CLIENT_SECRET);

        //se valida que los parametros esten configurados
        validateClientCredentials(serverUrl, realmName, clientId, clientSecret);

        Keycloak kc = crearConexionConKeycloak(serverUrl, realmName, clientId, clientSecret);
        adapter.setKc(kc);
        adapter.setRealm(realmName);
        return adapter;
    }

    private KeycloakAdapter getSystemClient() {
        KeycloakAdapter adapter = new KeycloakAdapter();
        String serverUrl = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_SERVER_URL);
        String realmName = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME);
        String clientId = (String) CacheManager.getConstante(
      	        ConstantesSistemaConstants.IDM_INTEGRATION_WEB_SYSTEM_PUBLIC_CLIENT_ID);
        String clientSecret = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_INTEGRATION_WEB_SYSTEM_PUBLIC_CLIENT_SECRET);
        //se valida que los parametros esten configurados
        validateClientCredentials(serverUrl, realmName, clientId, clientSecret);

        Keycloak kc = crearConexionConKeycloak(serverUrl, realmName, clientId, clientSecret);
        adapter.setKc(kc);
        adapter.setRealm(realmName);
        return adapter;
    }

    private KeycloakAdapter getWebPublicClient() {
        KeycloakAdapter adapter = new KeycloakAdapter();
        String serverUrl = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_SERVER_URL);
        String realmName = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME);
        String clientId = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_INTEGRATION_WEB_PUBLIC_CLIENT_ID);
        String clientSecret = (String) CacheManager.getConstante(
                ConstantesSistemaConstants.IDM_INTEGRATION_WEB_PUBLIC_CLIENT_SECRET);
        //se valida que los parametros esten configurados
        validateClientCredentials(serverUrl, realmName, clientId, clientSecret);

        Keycloak kc = crearConexionConKeycloak(serverUrl, realmName, clientId, clientSecret);
        adapter.setKc(kc);
        adapter.setRealm(realmName);
        return adapter;
    }
    
    private Keycloak crearConexionConKeycloak(String serverUrl, String realmName, String clientId, String clientSecret) {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realmName)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

    private void validateClientCredentials(String serverUrl, String realmName, String clientId, String clientSecret) {
        if (clientSecret == null) {
            throw new TechnicalException("No se ha configurado un \"secret\" para el cliente");
        } else if (serverUrl == null || realmName == null || clientId == null || clientSecret == null) {
            throw new TechnicalException(
                    "No se han configurados los parametros de conexion al IDM:"
                    + " serverUrl={0},realmName={1},clientId={2}",
                    serverUrl, realmName, clientId, clientSecret);
        }
    }
}
