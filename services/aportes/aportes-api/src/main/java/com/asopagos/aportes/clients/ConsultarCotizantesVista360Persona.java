package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarCotizantesVista360Persona
 */
public class ConsultarCotizantesVista360Persona extends ServiceClient {
 
  
  	private Long idPersona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public ConsultarCotizantesVista360Persona (Long idPersona){
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
		this.result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Long> getResult() {
		return result;
	}

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
}