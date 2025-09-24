package com.asopagos.fovis.clients;

import java.util.List;
import com.asopagos.dto.modelo.IntentoPostulacionRequisitoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/registrarIntentoPostulacionRequisito
 */
public class RegistrarIntentoPostulacionRequisito extends ServiceClient { 
    	private List<IntentoPostulacionRequisitoModeloDTO> intentoPostulacionRequisitosDTO;
  
  
 	public RegistrarIntentoPostulacionRequisito (List<IntentoPostulacionRequisitoModeloDTO> intentoPostulacionRequisitosDTO){
 		super();
		this.intentoPostulacionRequisitosDTO=intentoPostulacionRequisitosDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(intentoPostulacionRequisitosDTO == null ? null : Entity.json(intentoPostulacionRequisitosDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIntentoPostulacionRequisitosDTO (List<IntentoPostulacionRequisitoModeloDTO> intentoPostulacionRequisitosDTO){
 		this.intentoPostulacionRequisitosDTO=intentoPostulacionRequisitosDTO;
 	}
 	
 	public List<IntentoPostulacionRequisitoModeloDTO> getIntentoPostulacionRequisitosDTO (){
 		return intentoPostulacionRequisitosDTO;
 	}
  
}