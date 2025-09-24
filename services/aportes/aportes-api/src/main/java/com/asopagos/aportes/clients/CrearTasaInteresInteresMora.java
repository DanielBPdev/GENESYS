package com.asopagos.aportes.clients;

import java.lang.Boolean;
import com.asopagos.aportes.dto.ModificarTasaInteresMoraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearTasaInteresInteresMora
 */
public class CrearTasaInteresInteresMora extends ServiceClient { 
    	private ModificarTasaInteresMoraDTO nuevaTasa;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public CrearTasaInteresInteresMora (ModificarTasaInteresMoraDTO nuevaTasa){
 		super();
		this.nuevaTasa=nuevaTasa;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(nuevaTasa == null ? null : Entity.json(nuevaTasa));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setNuevaTasa (ModificarTasaInteresMoraDTO nuevaTasa){
 		this.nuevaTasa=nuevaTasa;
 	}
 	
 	public ModificarTasaInteresMoraDTO getNuevaTasa (){
 		return nuevaTasa;
 	}
  
}