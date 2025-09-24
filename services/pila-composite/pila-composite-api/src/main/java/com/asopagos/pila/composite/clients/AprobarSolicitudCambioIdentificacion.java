package com.asopagos.pila.composite.clients;

import java.util.List;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/aprobarSolicitudCambioIdentificacion
 */
public class AprobarSolicitudCambioIdentificacion extends ServiceClient { 
    	private List<SolicitudCambioNumIdentAportante> solicitudes;
  
  
 	public AprobarSolicitudCambioIdentificacion (List<SolicitudCambioNumIdentAportante> solicitudes){
 		super();
		this.solicitudes=solicitudes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudes == null ? null : Entity.json(solicitudes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSolicitudes (List<SolicitudCambioNumIdentAportante> solicitudes){
 		this.solicitudes=solicitudes;
 	}
 	
 	public List<SolicitudCambioNumIdentAportante> getSolicitudes (){
 		return solicitudes;
 	}
  
}