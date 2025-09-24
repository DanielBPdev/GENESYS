package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.ResultadoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/usuarios/terceros
 */
public class GestionActualizarTerceros extends ServiceClient { 
    	
    private UsuarioCCF usuario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoDTO result;
  
 	public GestionActualizarTerceros (UsuarioCCF usuario){
 		super();
		this.usuario=usuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(usuario == null ? null : Entity.json(usuario));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (ResultadoDTO) response.readEntity(ResultadoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResultadoDTO getResult() {
		return result;
	}

 
  
  	public void setUsuario (UsuarioCCF usuario){
 		this.usuario=usuario;
 	}
 	
 	public UsuarioCCF getUsuario (){
 		return usuario;
 	}
  
}