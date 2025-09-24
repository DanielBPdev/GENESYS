package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/actualizarInstanciaSolicitudGlobal
 */
public class ActualizarInstanciaSolicitudGlobal extends ServiceClient { 
   	private Long idSolicitudGlobal;
  	private Long idInstancia;
   
  
 	public ActualizarInstanciaSolicitudGlobal (Long idSolicitudGlobal,Long idInstancia){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.idInstancia=idInstancia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudGlobal", idSolicitudGlobal)
			.queryParam("idInstancia", idInstancia)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  	public void setIdInstancia (Long idInstancia){
 		this.idInstancia=idInstancia;
 	}
 	
 	public Long getIdInstancia (){
 		return idInstancia;
 	}
  
  
}