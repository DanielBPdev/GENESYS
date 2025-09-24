package com.asopagos.usuarios.clients;

import java.util.Map;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/recuperarContrasena
 */
public class RecuperarContrasena extends ServiceClient { 
   	private String nombreUsuario;
   	private Map<String,String> respuestas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public RecuperarContrasena (String nombreUsuario,Map<String,String> respuestas){
 		super();
		this.nombreUsuario=nombreUsuario;
		this.respuestas=respuestas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("nombreUsuario", nombreUsuario)
			.request(MediaType.APPLICATION_JSON)
			.post(respuestas == null ? null : Entity.json(respuestas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
  	public void setRespuestas (Map<String,String> respuestas){
 		this.respuestas=respuestas;
 	}
 	
 	public Map<String,String> getRespuestas (){
 		return respuestas;
 	}
  
}