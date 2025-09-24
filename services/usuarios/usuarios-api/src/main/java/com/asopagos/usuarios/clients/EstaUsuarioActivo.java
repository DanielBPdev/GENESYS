package com.asopagos.usuarios.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/usuarios/estaActivo
 */
public class EstaUsuarioActivo extends ServiceClient {
 
  
  	private String nombreUsuario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private boolean result;
  
 	public EstaUsuarioActivo (String nombreUsuario){
 		super();
		this.nombreUsuario=nombreUsuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombreUsuario", nombreUsuario)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (boolean) response.readEntity(boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public boolean getResult() {
		return result;
	}

 
  	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
}