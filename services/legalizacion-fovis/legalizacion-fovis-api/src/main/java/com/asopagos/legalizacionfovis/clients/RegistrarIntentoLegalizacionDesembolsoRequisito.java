package com.asopagos.legalizacionfovis.clients;

import java.util.List;
import com.asopagos.dto.modelo.IntentoLegalizacionDesembolsoRequisitoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/registrarIntentoLegalizacionDesembolsoRequisito
 */
public class RegistrarIntentoLegalizacionDesembolsoRequisito extends ServiceClient { 
    	private List<IntentoLegalizacionDesembolsoRequisitoModeloDTO> intentoLegalizacionDesembolsoRequisitosDTO;
  
  
 	public RegistrarIntentoLegalizacionDesembolsoRequisito (List<IntentoLegalizacionDesembolsoRequisitoModeloDTO> intentoLegalizacionDesembolsoRequisitosDTO){
 		super();
		this.intentoLegalizacionDesembolsoRequisitosDTO=intentoLegalizacionDesembolsoRequisitosDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(intentoLegalizacionDesembolsoRequisitosDTO == null ? null : Entity.json(intentoLegalizacionDesembolsoRequisitosDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIntentoLegalizacionDesembolsoRequisitosDTO (List<IntentoLegalizacionDesembolsoRequisitoModeloDTO> intentoLegalizacionDesembolsoRequisitosDTO){
 		this.intentoLegalizacionDesembolsoRequisitosDTO=intentoLegalizacionDesembolsoRequisitosDTO;
 	}
 	
 	public List<IntentoLegalizacionDesembolsoRequisitoModeloDTO> getIntentoLegalizacionDesembolsoRequisitosDTO (){
 		return intentoLegalizacionDesembolsoRequisitosDTO;
 	}
  
}