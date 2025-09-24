package com.asopagos.personas.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/personas/consultarPersonaJefeHogar
 */
public class ConsultarPersonaJefeHogar extends ServiceClient {
 
  
  	private Long idJefeHogar;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private PersonaModeloDTO result;
  
 	public ConsultarPersonaJefeHogar (Long idJefeHogar){
 		super();
		this.idJefeHogar=idJefeHogar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idJefeHogar", idJefeHogar)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (PersonaModeloDTO) response.readEntity(PersonaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public PersonaModeloDTO getResult() {
		return result;
	}

 
  	public void setIdJefeHogar (Long idJefeHogar){
 		this.idJefeHogar=idJefeHogar;
 	}
 	
 	public Long getIdJefeHogar (){
 		return idJefeHogar;
 	}
  
}