package com.asopagos.consola.estado.cargue.procesos.clients;

import com.asopagos.dto.ConsolaEstadoProcesoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/consolaEstadoCargueProcesos/registrarCargueConsola
 */
public class RegistrarCargueProcesoMasivo extends ServiceClient { 
    	private ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO;

		private Long result;
  
  
 	public RegistrarCargueProcesoMasivo (ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO){
 		super();
		this.consolaEstadoProcesoDTO=consolaEstadoProcesoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consolaEstadoProcesoDTO == null ? null : Entity.json(consolaEstadoProcesoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}
	

 
  
  	public void setConsolaEstadoProcesoDTO (ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO){
 		this.consolaEstadoProcesoDTO=consolaEstadoProcesoDTO;
 	}
 	
 	public ConsolaEstadoProcesoDTO getConsolaEstadoProcesoDTO (){
 		return consolaEstadoProcesoDTO;
 	}


	public Long getResult() {
		return this.result;
	}

  
}