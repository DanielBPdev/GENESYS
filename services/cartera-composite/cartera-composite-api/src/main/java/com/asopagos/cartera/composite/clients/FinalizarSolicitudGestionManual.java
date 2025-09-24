package com.asopagos.cartera.composite.clients;

import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/finalizarSolicitudGestionManual
 */
public class FinalizarSolicitudGestionManual extends ServiceClient { 
   	private Long numeroOperacion;
  	private Boolean tieneSolicitudManual;
  	private Boolean finalizar;
   
  
 	public FinalizarSolicitudGestionManual (Long numeroOperacion,Boolean tieneSolicitudManual,Boolean finalizar){
 		super();
		this.numeroOperacion=numeroOperacion;
		this.tieneSolicitudManual=tieneSolicitudManual;
		this.finalizar=finalizar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroOperacion", numeroOperacion)
			.queryParam("tieneSolicitudManual", tieneSolicitudManual)
			.queryParam("finalizar", finalizar)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroOperacion (Long numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public Long getNumeroOperacion (){
 		return numeroOperacion;
 	}
  	public void setTieneSolicitudManual (Boolean tieneSolicitudManual){
 		this.tieneSolicitudManual=tieneSolicitudManual;
 	}
 	
 	public Boolean getTieneSolicitudManual (){
 		return tieneSolicitudManual;
 	}
  	public void setFinalizar (Boolean finalizar){
 		this.finalizar=finalizar;
 	}
 	
 	public Boolean getFinalizar (){
 		return finalizar;
 	}
  
  
}