package com.asopagos.aportes.clients;

import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/{idTransaccion}/registrarRelacionarAportes
 */
public class RegistrarRelacionarAportes extends ServiceClient { 
  	private Long idTransaccion;
   	private Boolean esProcesoManual;
  	private Boolean esSimulado;
   
  
 	public RegistrarRelacionarAportes (Long idTransaccion,Boolean esProcesoManual,Boolean esSimulado){
 		super();
		this.idTransaccion=idTransaccion;
		this.esProcesoManual=esProcesoManual;
		this.esSimulado=esSimulado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTransaccion", idTransaccion)
			.queryParam("esProcesoManual", esProcesoManual)
			.queryParam("esSimulado", esSimulado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdTransaccion (Long idTransaccion){
 		this.idTransaccion=idTransaccion;
 	}
 	
 	public Long getIdTransaccion (){
 		return idTransaccion;
 	}
  
  	public void setEsProcesoManual (Boolean esProcesoManual){
 		this.esProcesoManual=esProcesoManual;
 	}
 	
 	public Boolean getEsProcesoManual (){
 		return esProcesoManual;
 	}
  	public void setEsSimulado (Boolean esSimulado){
 		this.esSimulado=esSimulado;
 	}
 	
 	public Boolean getEsSimulado (){
 		return esSimulado;
 	}
  
  
}