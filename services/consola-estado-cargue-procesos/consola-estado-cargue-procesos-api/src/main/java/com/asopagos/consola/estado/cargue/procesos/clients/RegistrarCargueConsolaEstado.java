package com.asopagos.consola.estado.cargue.procesos.clients;

import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/consolaEstadoCargueProcesos/registrarCargueConsola
 */
public class RegistrarCargueConsolaEstado extends ServiceClient { 
    	private ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO;
  
  
 	public RegistrarCargueConsolaEstado (ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO){
 		super();
		this.consolaEstadoCargueProcesoDTO=consolaEstadoCargueProcesoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consolaEstadoCargueProcesoDTO == null ? null : Entity.json(consolaEstadoCargueProcesoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setConsolaEstadoCargueProcesoDTO (ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO){
 		this.consolaEstadoCargueProcesoDTO=consolaEstadoCargueProcesoDTO;
 	}
 	
 	public ConsolaEstadoCargueProcesoDTO getConsolaEstadoCargueProcesoDTO (){
 		return consolaEstadoCargueProcesoDTO;
 	}
  
}