package com.asopagos.aportes.composite.clients;

import java.lang.Long;
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
 * /rest/aporteManual/{idSolicitud}/validarRegistrarAporteCorrecion
 */
public class ValidarRegistrarAporteCorrecion extends ServiceClient { 
  	private Long idSolicitud;
   	private Long idTarea;
  	private Long instaciaProceso;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public ValidarRegistrarAporteCorrecion (Long idSolicitud,Long idTarea,Long instaciaProceso){
 		super();
		this.idSolicitud=idSolicitud;
		this.idTarea=idTarea;
		this.instaciaProceso=instaciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.queryParam("idTarea", idTarea)
			.queryParam("instaciaProceso", instaciaProceso)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
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

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  	public void setInstaciaProceso (Long instaciaProceso){
 		this.instaciaProceso=instaciaProceso;
 	}
 	
 	public Long getInstaciaProceso (){
 		return instaciaProceso;
 	}
  
  
}