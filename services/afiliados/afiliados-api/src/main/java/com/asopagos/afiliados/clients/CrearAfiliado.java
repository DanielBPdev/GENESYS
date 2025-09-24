package com.asopagos.afiliados.clients;

import com.asopagos.dto.AfiliadoInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados
 */
public class CrearAfiliado extends ServiceClient { 
    	private AfiliadoInDTO inDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AfiliadoInDTO result;
  
 	public CrearAfiliado (AfiliadoInDTO inDTO){
 		super();
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (AfiliadoInDTO) response.readEntity(AfiliadoInDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public AfiliadoInDTO getResult() {
		return result;
	}

 
  
  	public void setInDTO (AfiliadoInDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public AfiliadoInDTO getInDTO (){
 		return inDTO;
 	}
  
}