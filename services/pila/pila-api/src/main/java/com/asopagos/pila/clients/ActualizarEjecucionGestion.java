package com.asopagos.pila.clients;

import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/actualizarEjecucionGestion
 */
public class ActualizarEjecucionGestion extends ServiceClient { 
   	private String proceso;
  	private Boolean activo;
  	private String estado;
   
  
 	public ActualizarEjecucionGestion (String proceso,Boolean activo,String estado){
 		super();
		this.proceso=proceso;
		this.activo=activo;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("proceso", proceso)
			.queryParam("activo", activo)
			.queryParam("estado", estado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setProceso (String proceso){
 		this.proceso=proceso;
 	}
 	
 	public String getProceso (){
 		return proceso;
 	}
  	public void setActivo (Boolean activo){
 		this.activo=activo;
 	}
 	
 	public Boolean getActivo (){
 		return activo;
 	}
  	public void setEstado (String estado){
 		this.estado=estado;
 	}
 	
 	public String getEstado (){
 		return estado;
 	}
  
  
}