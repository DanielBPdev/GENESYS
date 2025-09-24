package com.asopagos.afiliaciones.personas.clients;

import com.asopagos.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/solicitudAsociacionPersona/actualiza
 */
public class CrearSolicitudCargueMultiple extends ServiceClient { 
    	private Long inDTO;
		private Long res;
  
  
 	public CrearSolicitudCargueMultiple (Long inDTO){
 		super();
		this.inDTO=inDTO;
 	}

 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
				.resolveTemplate("idCargue", inDTO)
				.request(MediaType.APPLICATION_JSON)
				.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		res = (Long) response.readEntity(Long.class);
	}

  	public void setInDTO (Long inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public Long getInDTO (){
 		return inDTO;
 	}
	 public void setRes (Long res){
		this.res=res;
	}
	
	public Long getRes (){
		return res;
	}	
}