package com.asopagos.aportes.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/conciliarAporteGeneral
 */
public class ConciliarAporteGeneral extends ServiceClient { 
    	private String idsAporteGeneral;
  
  
 	public ConciliarAporteGeneral (String idsAporteGeneral){
 		super();
		this.idsAporteGeneral=idsAporteGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idsAporteGeneral == null ? null : Entity.json(idsAporteGeneral));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIdsAporteGeneral (String idsAporteGeneral){
 		this.idsAporteGeneral=idsAporteGeneral;
 	}
 	
 	public String getIdsAporteGeneral (){
 		return idsAporteGeneral;
 	}
  
}