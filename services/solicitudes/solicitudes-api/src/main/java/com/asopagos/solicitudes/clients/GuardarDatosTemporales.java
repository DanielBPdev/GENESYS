package com.asopagos.solicitudes.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/datosTemporales
 */
public class GuardarDatosTemporales extends ServiceClient { 
   	private Long idSolicitudGlobal;
   	private String jsonPayload;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public GuardarDatosTemporales (Long idSolicitudGlobal,String jsonPayload){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.jsonPayload=jsonPayload;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.post(jsonPayload == null ? null : Entity.json(jsonPayload));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  	public void setJsonPayload (String jsonPayload){
 		this.jsonPayload=jsonPayload;
 	}
 	
 	public String getJsonPayload (){
 		return jsonPayload;
 	}
  
}