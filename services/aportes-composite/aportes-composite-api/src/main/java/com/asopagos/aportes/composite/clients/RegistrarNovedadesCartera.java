package com.asopagos.aportes.composite.clients;

import com.asopagos.dto.cartera.NovedadCarteraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/registrarNovedadesCartera
 */
public class RegistrarNovedadesCartera extends ServiceClient { 
    	private NovedadCarteraDTO listaNovedades;
  
  
 	public RegistrarNovedadesCartera (NovedadCarteraDTO listaNovedades){
 		super();
		this.listaNovedades=listaNovedades;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaNovedades == null ? null : Entity.json(listaNovedades));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaNovedades (NovedadCarteraDTO listaNovedades){
 		this.listaNovedades=listaNovedades;
 	}
 	
 	public NovedadCarteraDTO getListaNovedades (){
 		return listaNovedades;
 	}
  
}