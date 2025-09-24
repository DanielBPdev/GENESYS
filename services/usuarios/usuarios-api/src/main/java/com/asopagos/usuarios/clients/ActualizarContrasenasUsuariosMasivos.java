package com.asopagos.usuarios.clients;

import java.util.Map;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.lang.String;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/actualizarContrasenasUsuariosMasivos
 */
public class ActualizarContrasenasUsuariosMasivos extends ServiceClient { 
   	private String usuarios;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public ActualizarContrasenasUsuariosMasivos (String usuarios){
 		super();
		this.usuarios=usuarios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("usuarios", usuarios)
			.request(MediaType.APPLICATION_JSON)
			.get();
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,String> getResult() {
		return result;
	}
 
  	public void setUsuarios (String usuarios){
 		this.usuarios=usuarios;
 	}
 	
 	public String getUsuarios (){
 		return usuarios;
 	}

}