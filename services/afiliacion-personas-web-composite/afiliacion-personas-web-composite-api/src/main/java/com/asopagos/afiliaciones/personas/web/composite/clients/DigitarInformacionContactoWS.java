package com.asopagos.afiliaciones.personas.web.composite.clients;

import java.util.Map;
import com.asopagos.dto.AfiliadoInDTO;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 */
public class DigitarInformacionContactoWS extends ServiceClient { 
    	private AfiliadoInDTO inDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public DigitarInformacionContactoWS (AfiliadoInDTO inDTO){
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
		result = (Map<String,Object>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,Object> getResult() {
		return result;
	}

 
  
  	public void setInDTO (AfiliadoInDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public AfiliadoInDTO getInDTO (){
 		return inDTO;
 	}
  
}