package com.asopagos.subsidiomonetario.clients;

import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/cancelarMasivaActualizarObservacionesProceso
 */
public class CancelarMasivaActualizarObservacionesProceso extends ServiceClient { 
   	private String numeroSolicitud;
   	private String observacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public CancelarMasivaActualizarObservacionesProceso (String numeroSolicitud,String observacion){
 		super();
		this.numeroSolicitud=numeroSolicitud;
		this.observacion=observacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroSolicitud", numeroSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(observacion == null ? null : Entity.json(observacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,String> getResult() {
		return result;
	}

 
  	public void setNumeroSolicitud (String numeroSolicitud){
 		this.numeroSolicitud=numeroSolicitud;
 	}
 	
 	public String getNumeroSolicitud (){
 		return numeroSolicitud;
 	}
  
  	public void setObservacion (String observacion){
 		this.observacion=observacion;
 	}
 	
 	public String getObservacion (){
 		return observacion;
 	}
  
}