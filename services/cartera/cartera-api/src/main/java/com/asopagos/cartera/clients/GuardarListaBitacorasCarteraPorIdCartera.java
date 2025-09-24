package com.asopagos.cartera.clients;

import java.util.List;
import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarListaBitacorasCarteraPorIdCartera
 */
public class GuardarListaBitacorasCarteraPorIdCartera extends ServiceClient { 
    	private List<BitacoraCarteraDTO> listaBitacorasCartera;
  
  
 	public GuardarListaBitacorasCarteraPorIdCartera (List<BitacoraCarteraDTO> listaBitacorasCartera){
 		super();
		this.listaBitacorasCartera=listaBitacorasCartera;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaBitacorasCartera == null ? null : Entity.json(listaBitacorasCartera));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaBitacorasCartera (List<BitacoraCarteraDTO> listaBitacorasCartera){
 		this.listaBitacorasCartera=listaBitacorasCartera;
 	}
 	
 	public List<BitacoraCarteraDTO> getListaBitacorasCartera (){
 		return listaBitacorasCartera;
 	}
  
}