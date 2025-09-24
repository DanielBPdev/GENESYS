package com.asopagos.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.usuarios.dto.UsuarioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/consultarEmpleadoresInactivarCuentaWeb
 */
public class ConsultarEmpleadoresInactivarCuentaWeb extends ServiceClient { 
    	private List<UsuarioDTO> usuarios;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public ConsultarEmpleadoresInactivarCuentaWeb (List<UsuarioDTO> usuarios){
 		super();
		this.usuarios=usuarios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(usuarios == null ? null : Entity.json(usuarios));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 
  
  	public void setUsuarios (List<UsuarioDTO> usuarios){
 		this.usuarios=usuarios;
 	}
 	
 	public List<UsuarioDTO> getUsuarios (){
 		return usuarios;
 	}
  
}