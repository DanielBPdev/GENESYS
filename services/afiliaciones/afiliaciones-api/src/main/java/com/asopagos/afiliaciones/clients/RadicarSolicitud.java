package com.asopagos.afiliaciones.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/radicarSolicitud/{idSolicitud}
 */
public class RadicarSolicitud extends ServiceClient { 
  	private Long idSolicitud;
   	private String sede;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public RadicarSolicitud (Long idSolicitud,String sede){
 		super();
		this.idSolicitud=idSolicitud;
		this.sede=sede;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.queryParam("sede", sede)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setSede (String sede){
 		this.sede=sede;
 	}
 	
 	public String getSede (){
 		return sede;
 	}
  
  
}