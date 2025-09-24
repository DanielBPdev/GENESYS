package com.asopagos.afiliaciones.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio DELETE
 * /rest/afiliaciones/{idSolicitudAfiliacion}/productoNoConforme/{idProductoNoConforme}
 */
public class EliminarProductoNoConforme extends ServiceClient {
 
  	private Long idSolicitudAfiliacion;
  	private Long idProductoNoConforme;
  
  
  
 	public EliminarProductoNoConforme (Long idSolicitudAfiliacion,Long idProductoNoConforme){
 		super();
		this.idSolicitudAfiliacion=idSolicitudAfiliacion;
		this.idProductoNoConforme=idProductoNoConforme;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudAfiliacion", idSolicitudAfiliacion)
						.resolveTemplate("idProductoNoConforme", idProductoNoConforme)
									.request(MediaType.APPLICATION_JSON).delete();
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitudAfiliacion (Long idSolicitudAfiliacion){
 		this.idSolicitudAfiliacion=idSolicitudAfiliacion;
 	}
 	
 	public Long getIdSolicitudAfiliacion (){
 		return idSolicitudAfiliacion;
 	}
  	public void setIdProductoNoConforme (Long idProductoNoConforme){
 		this.idProductoNoConforme=idProductoNoConforme;
 	}
 	
 	public Long getIdProductoNoConforme (){
 		return idProductoNoConforme;
 	}
  
  
}