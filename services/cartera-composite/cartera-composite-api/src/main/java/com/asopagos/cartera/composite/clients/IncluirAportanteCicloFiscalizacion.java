package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/incluirAportante
 */
public class IncluirAportanteCicloFiscalizacion extends ServiceClient { 
   	private ProcesoEnum proceso;
   	private SimulacionDTO simulacionDTO;
  
  
 	public IncluirAportanteCicloFiscalizacion (ProcesoEnum proceso,SimulacionDTO simulacionDTO){
 		super();
		this.proceso=proceso;
		this.simulacionDTO=simulacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("proceso", proceso)
			.request(MediaType.APPLICATION_JSON)
			.post(simulacionDTO == null ? null : Entity.json(simulacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  
  	public void setSimulacionDTO (SimulacionDTO simulacionDTO){
 		this.simulacionDTO=simulacionDTO;
 	}
 	
 	public SimulacionDTO getSimulacionDTO (){
 		return simulacionDTO;
 	}
  
}