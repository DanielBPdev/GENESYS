package com.asopagos.personas.clients;

import java.util.List;
import com.asopagos.dto.modelo.PersonaDetalleModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/actualizarPersonaDetalle
 */
public class ActualizarPersonaDetalle extends ServiceClient { 
    	private List<PersonaDetalleModeloDTO> listaPersonaDetalleDTO;
  
  
 	public ActualizarPersonaDetalle (List<PersonaDetalleModeloDTO> listaPersonaDetalleDTO){
 		super();
		this.listaPersonaDetalleDTO=listaPersonaDetalleDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaPersonaDetalleDTO == null ? null : Entity.json(listaPersonaDetalleDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaPersonaDetalleDTO (List<PersonaDetalleModeloDTO> listaPersonaDetalleDTO){
 		this.listaPersonaDetalleDTO=listaPersonaDetalleDTO;
 	}
 	
 	public List<PersonaDetalleModeloDTO> getListaPersonaDetalleDTO (){
 		return listaPersonaDetalleDTO;
 	}
  
}