package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.AfiliadoInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/{idAfiliado}
 */
public class ActualizarAfiliado extends ServiceClient { 
  	private Long idAfiliado;
    	private AfiliadoInDTO inDTO;
  
  
 	public ActualizarAfiliado (Long idAfiliado,AfiliadoInDTO inDTO){
 		super();
		this.idAfiliado=idAfiliado;
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAfiliado", idAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.put(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  
  	public void setInDTO (AfiliadoInDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public AfiliadoInDTO getInDTO (){
 		return inDTO;
 	}
  
}