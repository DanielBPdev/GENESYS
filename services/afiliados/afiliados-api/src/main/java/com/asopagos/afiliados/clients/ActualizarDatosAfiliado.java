package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/actualizarDatosAfiliado
 */
public class ActualizarDatosAfiliado extends ServiceClient { 
    	private AfiliadoModeloDTO afiliadoModeloDTO;
  
  
 	public ActualizarDatosAfiliado (AfiliadoModeloDTO afiliadoModeloDTO){
 		super();
		this.afiliadoModeloDTO=afiliadoModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(afiliadoModeloDTO == null ? null : Entity.json(afiliadoModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAfiliadoModeloDTO (AfiliadoModeloDTO afiliadoModeloDTO){
 		this.afiliadoModeloDTO=afiliadoModeloDTO;
 	}
 	
 	public AfiliadoModeloDTO getAfiliadoModeloDTO (){
 		return afiliadoModeloDTO;
 	}
  
}