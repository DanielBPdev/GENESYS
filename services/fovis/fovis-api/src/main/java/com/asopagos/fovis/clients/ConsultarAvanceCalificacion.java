package com.asopagos.fovis.clients;

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
 * /rest/fovis/consultarAvanceCalificacion
 */
public class ConsultarAvanceCalificacion extends ServiceClient {
 
  
  	private Long idCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public ConsultarAvanceCalificacion (Long idCicloAsignacion){
 		super();
		this.idCicloAsignacion=idCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCicloAsignacion", idCicloAsignacion)
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

 
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}
  
}