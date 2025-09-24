package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.dto.RespuestaGenericaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/seleccionarPersona/consultarEstado/{personaId}
 */
public class SeleccionarPersona extends ServiceClient {
 
  	private Long personaId;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaGenericaDTO result;
  
 	public SeleccionarPersona (Long personaId){
 		super();
		this.personaId=personaId;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("personaId", personaId)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RespuestaGenericaDTO) response.readEntity(RespuestaGenericaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RespuestaGenericaDTO getResult() {
		return result;
	}

 	public void setPersonaId (Long personaId){
 		this.personaId=personaId;
 	}
 	
 	public Long getPersonaId (){
 		return personaId;
 	}
  
  
}