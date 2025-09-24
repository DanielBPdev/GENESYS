package com.asopagos.aportes.clients;

import java.util.List;
import com.asopagos.dto.modelo.InformacionFaltanteAportanteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearInfoFaltante
 */
public class CrearInfoFaltante extends ServiceClient { 
    	private List<InformacionFaltanteAportanteModeloDTO> infoFaltanteDTO;
  
  
 	public CrearInfoFaltante (List<InformacionFaltanteAportanteModeloDTO> infoFaltanteDTO){
 		super();
		this.infoFaltanteDTO=infoFaltanteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(infoFaltanteDTO == null ? null : Entity.json(infoFaltanteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInfoFaltanteDTO (List<InformacionFaltanteAportanteModeloDTO> infoFaltanteDTO){
 		this.infoFaltanteDTO=infoFaltanteDTO;
 	}
 	
 	public List<InformacionFaltanteAportanteModeloDTO> getInfoFaltanteDTO (){
 		return infoFaltanteDTO;
 	}
  
}