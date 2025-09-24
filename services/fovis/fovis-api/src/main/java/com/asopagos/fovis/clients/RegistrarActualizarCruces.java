package com.asopagos.fovis.clients;

import java.util.List;
import com.asopagos.dto.CruceDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisCargue/registrarActualizarCruces
 */
public class RegistrarActualizarCruces extends ServiceClient { 
    	private List<CruceDTO> crucesDTO;
  
  
 	public RegistrarActualizarCruces (List<CruceDTO> crucesDTO){
 		super();
		this.crucesDTO=crucesDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(crucesDTO == null ? null : Entity.json(crucesDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCrucesDTO (List<CruceDTO> crucesDTO){
 		this.crucesDTO=crucesDTO;
 	}
 	
 	public List<CruceDTO> getCrucesDTO (){
 		return crucesDTO;
 	}
  
}