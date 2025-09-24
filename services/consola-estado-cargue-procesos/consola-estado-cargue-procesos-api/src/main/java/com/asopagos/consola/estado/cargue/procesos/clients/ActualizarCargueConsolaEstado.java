package com.asopagos.consola.estado.cargue.procesos.clients;

import java.lang.Long;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/consolaEstadoCargueProcesos/actualizarCargueConsola/{idCargue}
 */
public class ActualizarCargueConsolaEstado extends ServiceClient { 
  	private Long idCargue;
    	private ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO;
  
  
 	public ActualizarCargueConsolaEstado (Long idCargue,ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO){
 		super();
		this.idCargue=idCargue;
		this.consolaEstadoCargueProcesoDTO=consolaEstadoCargueProcesoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idCargue", idCargue)
			.request(MediaType.APPLICATION_JSON)
			.put(consolaEstadoCargueProcesoDTO == null ? null : Entity.json(consolaEstadoCargueProcesoDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdCargue (Long idCargue){
 		this.idCargue=idCargue;
 	}
 	
 	public Long getIdCargue (){
 		return idCargue;
 	}
  
  
  	public void setConsolaEstadoCargueProcesoDTO (ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO){
 		this.consolaEstadoCargueProcesoDTO=consolaEstadoCargueProcesoDTO;
 	}
 	
 	public ConsolaEstadoCargueProcesoDTO getConsolaEstadoCargueProcesoDTO (){
 		return consolaEstadoCargueProcesoDTO;
 	}
  
}