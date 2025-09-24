package com.asopagos.fovis.clients;

import com.asopagos.dto.CruceDetalleDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisCargue/crearRegistroCruce
 */
public class CrearRegistroCruce extends ServiceClient { 
    	private List<CruceDetalleDTO> listCruceDetalle;
  
  
 	public CrearRegistroCruce (List<CruceDetalleDTO> listCruceDetalle){
 		super();
		this.listCruceDetalle=listCruceDetalle;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listCruceDetalle == null ? null : Entity.json(listCruceDetalle));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListCruceDetalle (List<CruceDetalleDTO> listCruceDetalle){
 		this.listCruceDetalle=listCruceDetalle;
 	}
 	
 	public List<CruceDetalleDTO> getListCruceDetalle (){
 		return listCruceDetalle;
 	}
  
}