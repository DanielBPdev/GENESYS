package com.asopagos.afiliaciones.personas.web.composite.clients;

import com.asopagos.afiliaciones.personas.web.composite.dto.ReintegraAfiliadoDTO;
import java.util.Map;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/reintegrarIndependienteWeb
 */
public class ReintegrarIndependienteWeb extends ServiceClient { 
    	private ReintegraAfiliadoDTO reintegraAfiliadoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public ReintegrarIndependienteWeb (ReintegraAfiliadoDTO reintegraAfiliadoDTO){
 		super();
		this.reintegraAfiliadoDTO=reintegraAfiliadoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(reintegraAfiliadoDTO == null ? null : Entity.json(reintegraAfiliadoDTO));
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

 
  
  	public void setReintegraAfiliadoDTO (ReintegraAfiliadoDTO reintegraAfiliadoDTO){
 		this.reintegraAfiliadoDTO=reintegraAfiliadoDTO;
 	}
 	
 	public ReintegraAfiliadoDTO getReintegraAfiliadoDTO (){
 		return reintegraAfiliadoDTO;
 	}
  
}