package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.CruceDetalleDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/guardarParcialCruces
 */
public class GuardarParcialCruces extends ServiceClient { 
    	private List<CruceDetalleDTO> listCruces;
  
  
 	public GuardarParcialCruces (List<CruceDetalleDTO> listCruces){
 		super();
		this.listCruces=listCruces;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listCruces == null ? null : Entity.json(listCruces));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListCruces (List<CruceDetalleDTO> listCruces){
 		this.listCruces=listCruces;
 	}
 	
 	public List<CruceDetalleDTO> getListCruces (){
 		return listCruces;
 	}
  
}