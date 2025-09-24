package com.asopagos.cartera.composite.clients;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/{numeroRadicacion}/actualizarGestionCobro
 */
public class ActualizarGestionCobro extends ServiceClient { 
  	private String numeroRadicacion;
   	private Long idTarea;
  	private Boolean actualizacionEfectiva;
   
  
 	public ActualizarGestionCobro (String numeroRadicacion,Long idTarea,Boolean actualizacionEfectiva){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idTarea=idTarea;
		this.actualizacionEfectiva=actualizacionEfectiva;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("idTarea", idTarea)
			.queryParam("actualizacionEfectiva", actualizacionEfectiva)
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
  	public void setActualizacionEfectiva (Boolean actualizacionEfectiva){
 		this.actualizacionEfectiva=actualizacionEfectiva;
 	}
 	
 	public Boolean getActualizacionEfectiva (){
 		return actualizacionEfectiva;
 	}
  
  
}