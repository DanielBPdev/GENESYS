package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.ResultadoDTO;
import com.asopagos.usuarios.dto.CambiarContrasenaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/cambiarContrasena
 */
public class CambiarContrasena extends ServiceClient { 
    	private CambiarContrasenaDTO dto;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoDTO result;
  
 	public CambiarContrasena (CambiarContrasenaDTO dto){
 		super();
		this.dto=dto;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(dto == null ? null : Entity.json(dto));
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

 
  
  	public void setDto (CambiarContrasenaDTO dto){
 		this.dto=dto;
 	}
 	
 	public CambiarContrasenaDTO getDto (){
 		return dto;
 	}
  
}