package com.asopagos.clienteanibol.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/anibol/consultaEstadoProcesamientoPrescripcion
 */
public class ConsultarEstadoProcesamientoPrescripcion extends ServiceClient {
 
  
  	private String idProceso;
  
  
 	public ConsultarEstadoProcesamientoPrescripcion (String idProceso){
 		super();
		this.idProceso=idProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idProceso", idProceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdProceso (String idProceso){
 		this.idProceso=idProceso;
 	}
 	
 	public String getIdProceso (){
 		return idProceso;
 	}
  
}