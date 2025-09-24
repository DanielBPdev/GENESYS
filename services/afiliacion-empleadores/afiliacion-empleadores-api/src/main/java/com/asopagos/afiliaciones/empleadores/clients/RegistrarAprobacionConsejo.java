package com.asopagos.afiliaciones.empleadores.clients;

import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/solicitudAfiliacionEmpleador/registrarAprobacionConsejo
 */
public class RegistrarAprobacionConsejo extends ServiceClient { 
   	private String numeroActoAdministrativo;
  	private Long fechaAprobacionConsejo;
   	private List<Long> solicitudes;
  
  
 	public RegistrarAprobacionConsejo (String numeroActoAdministrativo,Long fechaAprobacionConsejo,List<Long> solicitudes){
 		super();
		this.numeroActoAdministrativo=numeroActoAdministrativo;
		this.fechaAprobacionConsejo=fechaAprobacionConsejo;
		this.solicitudes=solicitudes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroActoAdministrativo", numeroActoAdministrativo)
			.queryParam("fechaAprobacionConsejo", fechaAprobacionConsejo)
			.request(MediaType.APPLICATION_JSON)
			.put(solicitudes == null ? null : Entity.json(solicitudes));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroActoAdministrativo (String numeroActoAdministrativo){
 		this.numeroActoAdministrativo=numeroActoAdministrativo;
 	}
 	
 	public String getNumeroActoAdministrativo (){
 		return numeroActoAdministrativo;
 	}
  	public void setFechaAprobacionConsejo (Long fechaAprobacionConsejo){
 		this.fechaAprobacionConsejo=fechaAprobacionConsejo;
 	}
 	
 	public Long getFechaAprobacionConsejo (){
 		return fechaAprobacionConsejo;
 	}
  
  	public void setSolicitudes (List<Long> solicitudes){
 		this.solicitudes=solicitudes;
 	}
 	
 	public List<Long> getSolicitudes (){
 		return solicitudes;
 	}
  
}