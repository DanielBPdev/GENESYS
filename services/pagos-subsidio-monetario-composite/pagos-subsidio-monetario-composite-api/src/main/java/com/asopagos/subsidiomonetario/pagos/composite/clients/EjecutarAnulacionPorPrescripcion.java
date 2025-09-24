package com.asopagos.subsidiomonetario.pagos.composite.clients;

import java.lang.Integer;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/ejecutarAnulacion/porPrescripcion
 */
public class EjecutarAnulacionPorPrescripcion extends ServiceClient { 
   	private Integer limit;
   
  
 	public EjecutarAnulacionPorPrescripcion (Integer limit){
 		super();
		this.limit=limit;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("limit", limit)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setLimit (Integer limit){
 		this.limit=limit;
 	}
 	
 	public Integer getLimit (){
 		return limit;
 	}
  
  
}