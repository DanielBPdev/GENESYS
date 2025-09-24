package com.asopagos.afiliaciones.empleadores.web.composite.clients;

import com.asopagos.afiliaciones.empleadores.web.composite.dto.AnalizarSolicitudDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/analizarSolicitud
 */
public class AnalizarSolicitud extends ServiceClient { 
    	private AnalizarSolicitudDTO entrada;
  
  
 	public AnalizarSolicitud (AnalizarSolicitudDTO entrada){
 		super();
		this.entrada=entrada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entrada == null ? null : Entity.json(entrada));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEntrada (AnalizarSolicitudDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public AnalizarSolicitudDTO getEntrada (){
 		return entrada;
 	}
  
}