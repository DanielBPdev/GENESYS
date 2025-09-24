package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.usuarios.dto.UsuarioDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/usuarios/usuariosEnSesion
 */
public class UsuariosEnSesion extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<UsuarioDTO> result;
  
 	public UsuariosEnSesion (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<UsuarioDTO>) response.readEntity(new GenericType<List<UsuarioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<UsuarioDTO> getResult() {
		return result;
	}

 
  
}