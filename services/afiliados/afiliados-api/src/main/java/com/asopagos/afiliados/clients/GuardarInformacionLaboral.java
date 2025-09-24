package com.asopagos.afiliados.clients;

import com.asopagos.dto.InformacionLaboralTrabajadorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/informacionLaboral
 */
public class GuardarInformacionLaboral extends ServiceClient { 
    	private InformacionLaboralTrabajadorDTO inDTO;
  
  
 	public GuardarInformacionLaboral (InformacionLaboralTrabajadorDTO inDTO){
 		super();
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInDTO (InformacionLaboralTrabajadorDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public InformacionLaboralTrabajadorDTO getInDTO (){
 		return inDTO;
 	}
  
}