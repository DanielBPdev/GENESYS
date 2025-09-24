package com.asopagos.afiliaciones.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/asociarComunicadoIntentoAfiliacion
 */
public class AsociarComunicadoIntentoAfiliacion extends ServiceClient { 
   	private Long IdIntentoAfiliacion;
  	private Long idComunicado;
   
  
 	public AsociarComunicadoIntentoAfiliacion (Long IdIntentoAfiliacion,Long idComunicado){
 		super();
		this.IdIntentoAfiliacion=IdIntentoAfiliacion;
		this.idComunicado=idComunicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("IdIntentoAfiliacion", IdIntentoAfiliacion)
			.queryParam("idComunicado", idComunicado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdIntentoAfiliacion (Long IdIntentoAfiliacion){
 		this.IdIntentoAfiliacion=IdIntentoAfiliacion;
 	}
 	
 	public Long getIdIntentoAfiliacion (){
 		return IdIntentoAfiliacion;
 	}
  	public void setIdComunicado (Long idComunicado){
 		this.idComunicado=idComunicado;
 	}
 	
 	public Long getIdComunicado (){
 		return idComunicado;
 	}
  
  
}