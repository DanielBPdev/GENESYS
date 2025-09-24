package com.asopagos.tareashumanas.clients;

import java.lang.Long;
import java.util.Map;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/tareasHumanas/obtenerTareaActiva
 */
public class ObtenerTareaActiva extends ServiceClient {
 
  
  	private Long idInstanciaProceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public ObtenerTareaActiva (Long idInstanciaProceso){
 		super();
		this.idInstanciaProceso=idInstanciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idInstanciaProceso", idInstanciaProceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,Object>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,Object> getResult() {
		return result;
	}

 
  	public void setIdInstanciaProceso (Long idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public Long getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  
}