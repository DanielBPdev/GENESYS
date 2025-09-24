package com.asopagos.usuarios.ejb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import org.json.JSONObject;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.entidades.seguridad.Pregunta;
import com.asopagos.entidades.seguridad.ReferenciaToken;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.usuarios.clients.KeyCloakRestClient;
import com.asopagos.usuarios.clients.KeyCloakRestClientFactory;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.usuarios.mapper.KeyCloakMapper;
import com.asopagos.usuarios.mapper.KeycloakAdapter;
import com.asopagos.usuarios.mapper.KeycloakClientFactory;
import com.asopagos.usuarios.service.AutenticacionService;
import com.asopagos.usuarios.service.IPreguntasPersistenceServices;
import com.asopagos.usuarios.service.IReferenciaTokenPersistenceServices;
import com.asopagos.util.Interpolator;

/**
 * <b>Descripción:</b> EJB que implementa servicios de autenticación como la
 * generación de tokens de usuario. <b>Historia de Usuario:</b> Transversal
 * 
 * @author Andrey G. López <alopez@heinsohn.com.co>
 */
@Stateless
public class AutenticacionBusiness implements AutenticacionService {

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(AutenticacionBusiness.class);
	
	/**
	 * URL petición token
	 */
	private final String URL_TOKEN = "{0}realms/Integracion/protocol/openid-connect/token";

	/**
	 * Cadena con los parametros para la petición del token
	 */
	private final String PARAMS_TOKEN = "client_id=frontend&grant_type=refresh_token&refresh_token={0}";

    /**
     * Cadena con los parametros para la petición del tokenv2
     */
    private final String PARAMS_TOKEN_V2 = "client_id=Frontend-actualizacion-pantallas-local&grant_type=refresh_token&refresh_token={0}";

    /**
	 * Access token key
	 */
	private final String ACCES_TOKEN_KEY = "access_token";
	
	/**
     * Tipo de autenticación (de acuerdo al header de la petición)(Controla la cantidad de caracteres previos al token)
     */
    private static final String TIPO_AUTORIZACION = "Bearer ";

	@Inject
	private IReferenciaTokenPersistenceServices refTokenServices;

	@Inject
	private IPreguntasPersistenceServices preguntasServices;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TokenDTO generarTokenAcceso(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			Short digitoVerificacion) {
        logger.debug("Inicia generarTokenAcceso()");
		KeycloakAdapter kc = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.APP_WEB);
		AccessTokenResponse at=kc.getKc().tokenManager().getAccessToken();
		Long fechaExpira = System.currentTimeMillis() + at.getExpiresIn()*1000;
		TokenDTO token = KeyCloakMapper.toTokenDTO(at);
        
		ReferenciaToken referenciaToken = refTokenServices.buscarReferenciaToken(
                tipoIdentificacion, numeroIdentificacion, digitoVerificacion);
        
        if (referenciaToken == null) {
            referenciaToken = new ReferenciaToken();
            referenciaToken.setToken(token.getSessionId());
            referenciaToken.setTipoIdentificacion(tipoIdentificacion);
            referenciaToken.setNumeroIdentificacion(numeroIdentificacion);
            referenciaToken.setDigitoVerificacion(digitoVerificacion);		
            referenciaToken.setFechaExpiracion(new Date(fechaExpira));
            refTokenServices.persistir(referenciaToken);
        } else {
            referenciaToken.setToken(token.getSessionId());
            referenciaToken.setFechaExpiracion(new Date(fechaExpira));
            refTokenServices.actualizarReferenciaToken(referenciaToken);
        }
        logger.debug("Finaliza generarTokenAcceso()");
		return token;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void eliminarTokenAcceso(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			Short digitoVerificacion, Boolean obligarEliminar) {
		ReferenciaToken referenciaToken = refTokenServices.buscarReferenciaToken(tipoIdentificacion,
				numeroIdentificacion, digitoVerificacion);
		if (referenciaToken != null && referenciaToken.getFechaExpiracion() != null 
                && (!referenciaToken.getFechaExpiracion().before(new Date()) || obligarEliminar)) {
		    KeycloakAdapter kc = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
			kc.getKc().realm(kc.getRealm()).deleteSession(referenciaToken.getToken());
			refTokenServices.borrarReferenciaToken(referenciaToken.getIdReferenciaToken());
		}
	}

	@Override
	public List<Pregunta> consultarPreguntasPorEstado(EstadoActivoInactivoEnum estado) {
        return preguntasServices.buscarPreguntasPorEstado(estado);
	}
	
	@Override
	public TokenDTO generarTokenAccesoCore() {
        logger.debug("Inicia generarTokenAccesoCore()");
		KeycloakAdapter kc = KeycloakClientFactory.getInstance().getKeycloakClient(
                KeycloakClientFactory.USUARIOS_WEB);
        TokenDTO tokenDTO = KeyCloakMapper.toTokenDTO(kc.getKc().tokenManager().getAccessToken());
        logger.debug("Finaliza generarTokenAccesoCore()");
		return tokenDTO;
	}

    /* (non-Javadoc)
     * @see com.asopagos.usuarios.service.AutenticacionService#validarCredencialesUsuario(java.lang.String, java.lang.String)
     */
    @Override
    public Boolean validarCredencialesUsuario(String userName, String password) {
        KeyCloakRestClient client = KeyCloakRestClientFactory.getKeyCloakRestClient(
                (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME));
        return client.autenticarUsuario(userName, password);
    }

	@Override
	public TokenDTO generarTokenAccesoSystem() {
		logger.debug("Inicia generarTokenAccesoSystem()");
		KeycloakAdapter kc = KeycloakClientFactory.getInstance().getKeycloakClient(
                KeycloakClientFactory.SYSTEM);
        TokenDTO tokenDTO = KeyCloakMapper.toTokenDTO(kc.getKc().tokenManager().getAccessToken());
        logger.debug("Finaliza generarTokenAccesoSystem()");
		return tokenDTO;
	}

    @Override
    public Boolean validarTokenAccesoTemporal(String sessionId) {
        ReferenciaToken refToken = refTokenServices.buscarReferenciaToken(sessionId);
        return refToken != null && refToken.getFechaExpiracion().after(new Date());
    }
    
    @Override
    public void cerrarSesionesUsuario(String userName) {
		logger.debug("Inicia cerrarSesionUsuario(String userName)");
		KeycloakAdapter kc = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        RealmResource realm = kc.getKc().realm(kc.getRealm());
        UserRepresentation userRepresentation = realm.users().search(userName, null, null).get(0);
        realm.users().get(userRepresentation.getId()).logout();
        logger.debug("Finaliza cerrarSesionUsuario(String userName)");
    }
    
    @Override
    public void actualizarTiempoTokenWeb(Integer tiempoToken) {
        KeycloakAdapter kc = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.APP_WEB_ADMIN);
        RealmResource realm = kc.getKc().realm(kc.getRealm());
        RealmRepresentation realmRepresentation = realm.toRepresentation();
        realmRepresentation.setAccessTokenLifespan(tiempoToken);
        realm.update(realmRepresentation);
    }
    
    @Override
    public String obtenerTokenAcceso(String refreshToken) {
        logger.debug("Inicia actualizarTokenAcceso(String refreshToken)");
        String tokenStr = refreshToken.substring(TIPO_AUTORIZACION.length(), refreshToken.length());
        String urlParameters = Interpolator.interpolate(PARAMS_TOKEN, tokenStr);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        HttpURLConnection conn = null;
        String serverKC = CacheManager.getConstante(ParametrosSistemaConstants.IDM_SERVER_URL).toString();
        try {
            String request = Interpolator.interpolate(URL_TOKEN, serverKC);
            URL url = new URL(request);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
        } catch (Exception e) {
            logger.debug("Finaliza actualizarTokenAcceso(String refreshToken) 1");
            e.printStackTrace();
            return null;
        }

        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json = new JSONObject(response.toString());
                String token = json.getString(ACCES_TOKEN_KEY);

                logger.debug("Finaliza actualizarTokenAcceso(String refreshToken) 2");
                return token;
            }
            else {
                logger.debug("Finaliza actualizarTokenAcceso(String refreshToken) 3");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("Finaliza actualizarTokenAcceso(String refreshToken) 4");
            return null;
        }
    }

    @Override
    public String obtenerTokenAccesoV2(String refreshToken) {
        logger.debug("Inicia actualizarTokenAccesoV2(String refreshToken)");
        String tokenStr = refreshToken.substring(TIPO_AUTORIZACION.length(), refreshToken.length());
        String urlParameters = Interpolator.interpolate(PARAMS_TOKEN_V2, tokenStr);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        HttpURLConnection conn = null;
        String serverKC = CacheManager.getConstante(ParametrosSistemaConstants.IDM_SERVER_URL).toString();
        try {
            String request = Interpolator.interpolate(URL_TOKEN, serverKC);
            URL url = new URL(request);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
        } catch (Exception e) {
            logger.debug("Finaliza actualizarTokenAccesoV2(String refreshToken) 1");
            e.printStackTrace();
            return null;
        }

        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json = new JSONObject(response.toString());
                String token = json.getString(ACCES_TOKEN_KEY);

                logger.debug("Finaliza actualizarTokenAccesoV2(String refreshToken) 2");
                return token;
            }
            else {
                logger.debug("Finaliza actualizarTokenAccesoV2(String refreshToken) 3");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("Finaliza actualizarTokenAccesoV2(String refreshToken) 4");
            return null;
        }
    }
}
