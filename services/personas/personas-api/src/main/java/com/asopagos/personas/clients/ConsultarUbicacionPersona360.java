package com.asopagos.personas.clients;

import java.lang.Long;
import com.asopagos.personas.dto.Ubicacion360DTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/personas/consultarUbicacionPersona360
 */
public class ConsultarUbicacionPersona360 extends ServiceClient {
 
  
  	private Long idPersona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Ubicacion360DTO result;
  
 	public ConsultarUbicacionPersona360 (Long idPersona){
 		super();
		this.idPersona=idPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPersona", idPersona)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Ubicacion360DTO) response.readEntity(Ubicacion360DTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Ubicacion360DTO getResult() {
		return result;
	}

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
}