package com.asopagos.comunicados.clients;

import com.asopagos.dto.JsonPayloadDatoTemporalComunicadoDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/generarYGuardarDatoTemporalComunicado
 */
public class GenerarYGuardarDatoTemporalComunicado extends ServiceClient { 
    	private JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicadoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public GenerarYGuardarDatoTemporalComunicado (JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicadoDTO){
 		super();
		this.jsonPayloadDatoTemporalComunicadoDTO=jsonPayloadDatoTemporalComunicadoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(jsonPayloadDatoTemporalComunicadoDTO == null ? null : Entity.json(jsonPayloadDatoTemporalComunicadoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 
  
  	public void setJsonPayloadDatoTemporalComunicadoDTO (JsonPayloadDatoTemporalComunicadoDTO jsonPayloadDatoTemporalComunicadoDTO){
 		this.jsonPayloadDatoTemporalComunicadoDTO=jsonPayloadDatoTemporalComunicadoDTO;
 	}
 	
 	public JsonPayloadDatoTemporalComunicadoDTO getJsonPayloadDatoTemporalComunicadoDTO (){
 		return jsonPayloadDatoTemporalComunicadoDTO;
 	}
  
}