package com.asopagos.fovis.clients;

import java.util.List;
import com.asopagos.dto.modelo.ParametrizacionModalidadModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/actualizarModalidades
 */
public class ActualizarModalidades extends ServiceClient { 
    	private List<ParametrizacionModalidadModeloDTO> modalidadesFOVIS;
  
  
 	public ActualizarModalidades (List<ParametrizacionModalidadModeloDTO> modalidadesFOVIS){
 		super();
		this.modalidadesFOVIS=modalidadesFOVIS;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(modalidadesFOVIS == null ? null : Entity.json(modalidadesFOVIS));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setModalidadesFOVIS (List<ParametrizacionModalidadModeloDTO> modalidadesFOVIS){
 		this.modalidadesFOVIS=modalidadesFOVIS;
 	}
 	
 	public List<ParametrizacionModalidadModeloDTO> getModalidadesFOVIS (){
 		return modalidadesFOVIS;
 	}
  
}