package com.asopagos.correspondencia.clients;

import java.lang.String;
import com.asopagos.dto.afiliaciones.RecepcionSolicitudDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/{numeroRadicado}/registrarRecepcion
 */
public class RegistrarRecepcionSolicitud extends ServiceClient { 
  	private String numeroRadicado;
    	private RecepcionSolicitudDTO datosRecepcionSolicitud;
  
  
 	public RegistrarRecepcionSolicitud (String numeroRadicado,RecepcionSolicitudDTO datosRecepcionSolicitud){
 		super();
		this.numeroRadicado=numeroRadicado;
		this.datosRecepcionSolicitud=datosRecepcionSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicado", numeroRadicado)
			.request(MediaType.APPLICATION_JSON)
			.post(datosRecepcionSolicitud == null ? null : Entity.json(datosRecepcionSolicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  
  	public void setDatosRecepcionSolicitud (RecepcionSolicitudDTO datosRecepcionSolicitud){
 		this.datosRecepcionSolicitud=datosRecepcionSolicitud;
 	}
 	
 	public RecepcionSolicitudDTO getDatosRecepcionSolicitud (){
 		return datosRecepcionSolicitud;
 	}
  
}