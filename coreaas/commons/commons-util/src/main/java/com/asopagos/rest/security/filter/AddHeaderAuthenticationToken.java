/**
 * 
 */
package com.asopagos.rest.security.filter;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * Filtro usado en todas las peticiones enviadas a los servicio para 
 * setear en las cabeceras el token de autenticación
 * @author alopez
 *
 */
public class AddHeaderAuthenticationToken implements ClientRequestFilter {
	
	
	private String token;


	public AddHeaderAuthenticationToken() {
		AccessToken token=ResteasyProviderFactory.getContextData(AccessToken.class);
		if(token != null){
			this.token = token.getToken();
		}
	}


	@Override
	public void filter(ClientRequestContext context) throws IOException {
		if(token!=null){
			final String bearer = "Bearer ";
			context.getHeaders().add(HttpHeaders.AUTHORIZATION, bearer+token);
		}
	}

}
