package com.asopagos.constantes.parametros.clients;

import com.asopagos.constantes.parametros.dto.ConstantesCajaCompensacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/constantesparametros/constantesCaja
 */
public class EstablecerConstantesCaja extends ServiceClient { 
    	private ConstantesCajaCompensacionDTO inDTO;
  
  
 	public EstablecerConstantesCaja (ConstantesCajaCompensacionDTO inDTO){
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
	

 
  
  	public void setInDTO (ConstantesCajaCompensacionDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public ConstantesCajaCompensacionDTO getInDTO (){
 		return inDTO;
 	}
  
}