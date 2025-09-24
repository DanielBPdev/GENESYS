package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.LineaCobroModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarLineaCobro
 */
public class GuardarLineaCobro extends ServiceClient { 
    	private LineaCobroModeloDTO lineaCobroModeloDTO;
  
  
 	public GuardarLineaCobro (LineaCobroModeloDTO lineaCobroModeloDTO){
 		super();
		this.lineaCobroModeloDTO=lineaCobroModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(lineaCobroModeloDTO == null ? null : Entity.json(lineaCobroModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setLineaCobroModeloDTO (LineaCobroModeloDTO lineaCobroModeloDTO){
 		this.lineaCobroModeloDTO=lineaCobroModeloDTO;
 	}
 	
 	public LineaCobroModeloDTO getLineaCobroModeloDTO (){
 		return lineaCobroModeloDTO;
 	}
  
}