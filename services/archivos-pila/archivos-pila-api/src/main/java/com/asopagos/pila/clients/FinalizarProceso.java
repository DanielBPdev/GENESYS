package com.asopagos.pila.clients;

import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/finalizarProceso
 */
public class FinalizarProceso extends ServiceClient { 
   	private EstadoProcesoValidacionEnum estadoProceso;
  	private Long idProceso;
   
  
 	public FinalizarProceso (EstadoProcesoValidacionEnum estadoProceso,Long idProceso){
 		super();
		this.estadoProceso=estadoProceso;
		this.idProceso=idProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("estadoProceso", estadoProceso)
			.queryParam("idProceso", idProceso)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setEstadoProceso (EstadoProcesoValidacionEnum estadoProceso){
 		this.estadoProceso=estadoProceso;
 	}
 	
 	public EstadoProcesoValidacionEnum getEstadoProceso (){
 		return estadoProceso;
 	}
  	public void setIdProceso (Long idProceso){
 		this.idProceso=idProceso;
 	}
 	
 	public Long getIdProceso (){
 		return idProceso;
 	}
  
  
}