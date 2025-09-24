package com.asopagos.fovis.clients;

import java.util.List;
import com.asopagos.dto.fovis.MiembroHogarDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/actualizarIngresosMiembrosHogar
 */
public class ActualizarIngresosMiembrosHogar extends ServiceClient { 
    	private List<MiembroHogarDTO> listaMiembrosHogar;
  
  
 	public ActualizarIngresosMiembrosHogar (List<MiembroHogarDTO> listaMiembrosHogar){
 		super();
		this.listaMiembrosHogar=listaMiembrosHogar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaMiembrosHogar == null ? null : Entity.json(listaMiembrosHogar));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaMiembrosHogar (List<MiembroHogarDTO> listaMiembrosHogar){
 		this.listaMiembrosHogar=listaMiembrosHogar;
 	}
 	
 	public List<MiembroHogarDTO> getListaMiembrosHogar (){
 		return listaMiembrosHogar;
 	}
  
}