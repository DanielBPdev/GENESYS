package com.asopagos.fovis.clients;

import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/{idPostulacion}/estadoHogar
 */
public class ActualizarEstadoHogar extends ServiceClient { 
  	private Long idPostulacion;
   	private EstadoHogarEnum estadoHogar;
   
  
 	public ActualizarEstadoHogar (Long idPostulacion,EstadoHogarEnum estadoHogar){
 		super();
		this.idPostulacion=idPostulacion;
		this.estadoHogar=estadoHogar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idPostulacion", idPostulacion)
			.queryParam("estadoHogar", estadoHogar)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  
  	public void setEstadoHogar (EstadoHogarEnum estadoHogar){
 		this.estadoHogar=estadoHogar;
 	}
 	
 	public EstadoHogarEnum getEstadoHogar (){
 		return estadoHogar;
 	}
  
  
}