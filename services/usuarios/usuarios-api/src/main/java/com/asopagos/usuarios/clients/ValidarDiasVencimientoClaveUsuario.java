package com.asopagos.usuarios.clients;

import java.lang.Long;
import com.asopagos.usuarios.dto.UsuarioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/validarDiasVencimientoClaveUsuario
 */
public class ValidarDiasVencimientoClaveUsuario extends ServiceClient { 
    	private UsuarioDTO usuario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ValidarDiasVencimientoClaveUsuario (UsuarioDTO usuario){
 		super();
		this.usuario=usuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(usuario == null ? null : Entity.json(usuario));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setUsuario (UsuarioDTO usuario){
 		this.usuario=usuario;
 	}
 	
 	public UsuarioDTO getUsuario (){
 		return usuario;
 	}
  
}