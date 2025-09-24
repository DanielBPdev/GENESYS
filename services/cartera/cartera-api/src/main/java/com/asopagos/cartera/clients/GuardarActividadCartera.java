package com.asopagos.cartera.clients;

import java.util.List;
import com.asopagos.dto.modelo.ActividadCarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarActividadCartera
 */
public class GuardarActividadCartera extends ServiceClient { 
    	private List<ActividadCarteraModeloDTO> actividadesCarteraDTO;
  
  
 	public GuardarActividadCartera (List<ActividadCarteraModeloDTO> actividadesCarteraDTO){
 		super();
		this.actividadesCarteraDTO=actividadesCarteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(actividadesCarteraDTO == null ? null : Entity.json(actividadesCarteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setActividadesCarteraDTO (List<ActividadCarteraModeloDTO> actividadesCarteraDTO){
 		this.actividadesCarteraDTO=actividadesCarteraDTO;
 	}
 	
 	public List<ActividadCarteraModeloDTO> getActividadesCarteraDTO (){
 		return actividadesCarteraDTO;
 	}
  
}