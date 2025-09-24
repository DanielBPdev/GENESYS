/**
 * 
 */
package com.asopagos.rest.security.filter;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

/**
 * Filtro usado en todas las peticiones enviadas al JBPM para 
 * setear en las cabeceras el token de autenticación
 * @author alopez
 *
 */
public class AddAuthenticationToken implements ClientRequestFilter {
	
	private String token;

	public AddAuthenticationToken(String token) {
		this.token=token;
	}

	@Override
	public void filter(ClientRequestContext context) throws IOException {
		if(token!=null){
			final String bearer = "Bearer ";
			context.getHeaders().add(HttpHeaders.AUTHORIZATION, bearer+token);
		}
	}

}
