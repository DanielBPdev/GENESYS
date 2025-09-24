package com.asopagos.cartera.composite.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/{numeroRadicacion}/tramitarDesafiliacion
 */
public class TramitarDesafiliacion extends ServiceClient { 
  	private String numeroRadicacion;
   	private Long idTarea;
  	private String observacionCoordinador;
  	private boolean aprobado;
   
  
 	public TramitarDesafiliacion (String numeroRadicacion,Long idTarea,String observacionCoordinador,boolean aprobado){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idTarea=idTarea;
		this.observacionCoordinador=observacionCoordinador;
		this.aprobado=aprobado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("idTarea", idTarea)
			.queryParam("observacionCoordinador", observacionCoordinador)
			.queryParam("aprobado", aprobado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  	public void setObservacionCoordinador (String observacionCoordinador){
 		this.observacionCoordinador=observacionCoordinador;
 	}
 	
 	public String getObservacionCoordinador (){
 		return observacionCoordinador;
 	}
  	public void setAprobado (boolean aprobado){
 		this.aprobado=aprobado;
 	}
 	
 	public boolean getAprobado (){
 		return aprobado;
 	}
  
  
}