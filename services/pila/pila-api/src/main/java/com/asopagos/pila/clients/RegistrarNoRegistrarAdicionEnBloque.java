package com.asopagos.pila.clients;

import java.util.List;
import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/registrarNoRegistrarAdicionEnBloque
 */
public class RegistrarNoRegistrarAdicionEnBloque extends ServiceClient { 
    	private List<RegistrarCorreccionAdicionDTO> registrosAdicion;
  
  
 	public RegistrarNoRegistrarAdicionEnBloque (List<RegistrarCorreccionAdicionDTO> registrosAdicion){
 		super();
		this.registrosAdicion=registrosAdicion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registrosAdicion == null ? null : Entity.json(registrosAdicion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRegistrosAdicion (List<RegistrarCorreccionAdicionDTO> registrosAdicion){
 		this.registrosAdicion=registrosAdicion;
 	}
 	
 	public List<RegistrarCorreccionAdicionDTO> getRegistrosAdicion (){
 		return registrosAdicion;
 	}
  
}