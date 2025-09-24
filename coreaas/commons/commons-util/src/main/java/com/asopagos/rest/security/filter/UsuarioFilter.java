package com.asopagos.rest.security.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidades.auditoria.ThreadContext;
import com.asopagos.rest.exception.RecursoNoAutorizadoException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rest.security.enums.HeadersEnum;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;

/**
 * <b>Descripción:</b> Clase Provider de JAX-RS que actúa como filtro para
 * extraer los datos del usuario recibido en el Token
 * <b>Historia de Usuario: </b>Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class UsuarioFilter implements ContainerRequestFilter {

    public static final String SEDE_CLAIM = "sede";
    private static final String USERNAME_CLAIM = "preferred_username";
    private static final String EMAIL_CLAIM = "email";
    private static final String GRUPOS = "grupos";
    private static final String CIUDAD_SEDE = "ciudadSede";
    private static final String SEDE_SYSTEM;

    static {
    	SEDE_SYSTEM = (String) CacheManager.getConstante(ConstantesSistemaConstants.SEDE_USUARIO_SYSTEM);
    }
    /**
     * Implementación del método filter para extrar el usuario del token
     * recobido en la petición HTTP
     * @param requestContext
     * @throws java.io.IOException
     */

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        UserDTO userDTO = new UserDTO();
        String accessToken = getAccessToken(requestContext);
        String tokenID = getTokenId(requestContext);
        if (accessToken != null) {
            JwtConsumer jwtConsumer = new JwtConsumerBuilder().setSkipSignatureVerification().setSkipAllDefaultValidators().build();
            try {
                //se setean el usuario
                JwtClaims jwtClaims = jwtConsumer.processToClaims(accessToken);
                userDTO.setSedeCajaCompensacion(jwtClaims.getClaimValue(SEDE_CLAIM) !=null ? (String) jwtClaims.getClaimValue(SEDE_CLAIM) : SEDE_SYSTEM);
                userDTO.setNombreUsuario((String) jwtClaims.getClaimValue(USERNAME_CLAIM));
                userDTO.setEmail((String) jwtClaims.getClaimValue(EMAIL_CLAIM));
                userDTO.setCiudadSedeCajaCompensacion((String) jwtClaims.getClaimValue(CIUDAD_SEDE));
                if (tokenID!=null && !tokenID.isEmpty()) {
                    jwtClaims = jwtConsumer.processToClaims(tokenID);
                    if (jwtClaims.getClaimValue(GRUPOS)!=null) {
                        ArrayList<String> grupos = (ArrayList<String>) jwtClaims.getClaimValue(GRUPOS);
                        userDTO.setGrupos(grupos);
                    }
                }                
                //se setea el token
                ResteasyProviderFactory.pushContext(AccessToken.class, new AccessToken(accessToken));
            } catch (InvalidJwtException e) {
                throw new RecursoNoAutorizadoException(MensajesGeneralConstants.TOKEN_INVALIDO, e);
            }
        }
        //se valida si la invocación al servicio se encuentra dentro del contexto 
        //de una transaccion y se adiciona la información de auditoria
        userDTO.getAuditoria().setIp(getIP(requestContext));
        userDTO.getAuditoria().setTxId(getTxId(requestContext));

        ResteasyProviderFactory.pushContext(UserDTO.class, userDTO);

        ThreadContext contexto = ThreadContext.get();
        contexto.setUserName(userDTO.getNombreUsuario());
        contexto.setRequestId(getRequestId(requestContext));
        contexto.setIp(userDTO.getAuditoria().getIp());
        contexto.setTimeStamp(System.currentTimeMillis());
    }

    private String getAccessToken(ContainerRequestContext requestContext) {
        String token = null;
        String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            token = header.substring(7);
        }

        return token;
    }
    
    private String getTokenId(ContainerRequestContext requestContext) {
        String token = null;
        String header = requestContext.getHeaderString(HeadersEnum.PROFILE.getName()); 
        if (header != null) {
            token = header.substring(7);
            System.out.println("PROFILEEEE " +header+ token);
        }
        return token;
    }

    private String getTxId(ContainerRequestContext requestContext) {
        String txId = requestContext.getHeaderString(HeadersEnum.TX_ID.getName());
        return txId;
    }

    private String getIP(ContainerRequestContext requestContext) {
        String ip = requestContext.getHeaderString(HeadersEnum.IP_ORIGIN.getName());
        if (ip == null) {
            ip = requestContext.getHeaderString(HttpHeaders.HOST);
        }
        return ip;
    }

    private String getRequestId(ContainerRequestContext requestContext) {
        String requestId = requestContext.getHeaderString(HeadersEnum.REQUEST_ID.getName());
        return requestId;
    }

}
