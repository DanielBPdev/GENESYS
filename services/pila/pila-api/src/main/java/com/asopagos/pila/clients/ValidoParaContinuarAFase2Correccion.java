package com.asopagos.pila.clients;

import java.lang.Long;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/validoParaContinuarAFase2Correccion
 */
public class ValidoParaContinuarAFase2Correccion extends ServiceClient {
 
  
  	private Long idPlanillaCorreccion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public ValidoParaContinuarAFase2Correccion (Long idPlanillaCorreccion){
 		super();
		this.idPlanillaCorreccion=idPlanillaCorreccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPlanillaCorreccion", idPlanillaCorreccion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,String> getResult() {
		return result;
	}

 
  	public void setIdPlanillaCorreccion (Long idPlanillaCorreccion){
 		this.idPlanillaCorreccion=idPlanillaCorreccion;
 	}
 	
 	public Long getIdPlanillaCorreccion (){
 		return idPlanillaCorreccion;
 	}
  
}