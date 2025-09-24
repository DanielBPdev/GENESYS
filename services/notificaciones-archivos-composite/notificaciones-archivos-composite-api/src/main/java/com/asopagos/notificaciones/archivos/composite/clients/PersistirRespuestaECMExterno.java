package com.asopagos.notificaciones.archivos.composite.clients;

import com.asopagos.archivos.dto.RespuestaECMExternoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/notificaciones/persistirRespuestaECMExterno
 */
public class PersistirRespuestaECMExterno extends ServiceClient { 
    	private RespuestaECMExternoDTO respuesta;
  
  
 	public PersistirRespuestaECMExterno (RespuestaECMExternoDTO respuesta){
 		super();
		this.respuesta=respuesta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(respuesta == null ? null : Entity.json(respuesta));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRespuesta (RespuestaECMExternoDTO respuesta){
 		this.respuesta=respuesta;
 	}
 	
 	public RespuestaECMExternoDTO getRespuesta (){
 		return respuesta;
 	}
  
}