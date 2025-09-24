package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.VerificacionGestionPNCPostulacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/verificarSolicitudPostulacion
 */
public class VerificarSolicitudPostulacion extends ServiceClient { 
    	private VerificacionGestionPNCPostulacionDTO datosVerificacion;
  
  
 	public VerificarSolicitudPostulacion (VerificacionGestionPNCPostulacionDTO datosVerificacion){
 		super();
		this.datosVerificacion=datosVerificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosVerificacion == null ? null : Entity.json(datosVerificacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosVerificacion (VerificacionGestionPNCPostulacionDTO datosVerificacion){
 		this.datosVerificacion=datosVerificacion;
 	}
 	
 	public VerificacionGestionPNCPostulacionDTO getDatosVerificacion (){
 		return datosVerificacion;
 	}
  
}