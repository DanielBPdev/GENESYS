package com.asopagos.fovis.clients;

import java.util.List;
import com.asopagos.dto.PostulacionAsignacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarListaPostulacionAsignacion
 */
public class CrearActualizarListaPostulacionAsignacion extends ServiceClient { 
    	private List<PostulacionAsignacionDTO> listPostulaciones;
  
  
 	public CrearActualizarListaPostulacionAsignacion (List<PostulacionAsignacionDTO> listPostulaciones){
 		super();
		this.listPostulaciones=listPostulaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listPostulaciones == null ? null : Entity.json(listPostulaciones));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListPostulaciones (List<PostulacionAsignacionDTO> listPostulaciones){
 		this.listPostulaciones=listPostulaciones;
 	}
 	
 	public List<PostulacionAsignacionDTO> getListPostulaciones (){
 		return listPostulaciones;
 	}
  
}