package com.asopagos.novedades.composite.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/aceptarResultadoArchivoActualizacion
 */
public class InsercionMonitoreoLogs extends ServiceClient { 
   	private String puntoEjecucion;
	private String ubicacion;
   
  
 	public InsercionMonitoreoLogs (String puntoEjecucion, String ubicacion){
 		super();
		this.puntoEjecucion=puntoEjecucion;
		this.ubicacion=ubicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("puntoEjecucion", puntoEjecucion)
			.queryParam("ubicacion",ubicacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	
  	public void setPuntoEjecucion (String puntoEjecucion){
 		this.puntoEjecucion=puntoEjecucion;
 	}
 	
 	public String getPuntoEjecucion (){
 		return puntoEjecucion;
 	}

	 public void setUbicacion (String ubicacion){
		this.ubicacion=ubicacion;
	}
	
	public String getUbicacion (){
		return ubicacion;
	}
  
}