package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.GestionCicloManualDTO;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/guardarGestionCicloManualTemporal/{numeroOperacion}
 */
public class GuardarGestionCicloManualTemporal extends ServiceClient { 
  	private Long numeroOperacion;
   	private Boolean tieneSolicitudManual;
   	private GestionCicloManualDTO gestionCicloDTO;
  
  
 	public GuardarGestionCicloManualTemporal (Long numeroOperacion,Boolean tieneSolicitudManual,GestionCicloManualDTO gestionCicloDTO){
 		super();
		this.numeroOperacion=numeroOperacion;
		this.tieneSolicitudManual=tieneSolicitudManual;
		this.gestionCicloDTO=gestionCicloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroOperacion", numeroOperacion)
			.queryParam("tieneSolicitudManual", tieneSolicitudManual)
			.request(MediaType.APPLICATION_JSON)
			.post(gestionCicloDTO == null ? null : Entity.json(gestionCicloDTO));
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
  
  	public void setGestionCicloDTO (GestionCicloManualDTO gestionCicloDTO){
 		this.gestionCicloDTO=gestionCicloDTO;
 	}
 	
 	public GestionCicloManualDTO getGestionCicloDTO (){
 		return gestionCicloDTO;
 	}
  
}