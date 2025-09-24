package com.asopagos.consola.estado.cargue.procesos.clients;

import java.lang.Long;
import com.asopagos.dto.ConsolaEstadoProcesoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.dto.ConsolaEstadoProcesoDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.dto.ConsolaEstadoProcesoDTO;
/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/consolaEstadoCargueProcesos/actualizarCargueConsola/{idCargue}
 */
public class ActualizarProcesoConsolaEstado extends ServiceClient { 
  	private Long idConsolaEstadoProcesoMasivo;
    	private ConsolaEstadoProcesoDTO consolaEstadoCargueProcesoDTO;
  
  
 	public ActualizarProcesoConsolaEstado (Long idConsolaEstadoProcesoMasivo,ConsolaEstadoProcesoDTO consolaEstadoCargueProcesoDTO){
 		super();
		this.idConsolaEstadoProcesoMasivo=idConsolaEstadoProcesoMasivo;
		this.consolaEstadoCargueProcesoDTO=consolaEstadoCargueProcesoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("IdConsolaEstadoProcesoMasivo", idConsolaEstadoProcesoMasivo)
			.request(MediaType.APPLICATION_JSON)
			.put(consolaEstadoCargueProcesoDTO == null ? null : Entity.json(consolaEstadoCargueProcesoDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdConsolaEstadoProcesoMasivo (Long idConsolaEstadoProcesoMasivo){
 		this.idConsolaEstadoProcesoMasivo=idConsolaEstadoProcesoMasivo;
 	}
 	
 	public Long getIdConsolaEstadoProcesoMasivo (){
 		return idConsolaEstadoProcesoMasivo;
 	}
  
  
  	public void setConsolaEstadoCargueProcesoDTO (ConsolaEstadoProcesoDTO consolaEstadoCargueProcesoDTO){
 		this.consolaEstadoCargueProcesoDTO=consolaEstadoCargueProcesoDTO;
 	}
 	
 	public ConsolaEstadoProcesoDTO getConsolaEstadoCargueProcesoDTO (){
 		return consolaEstadoCargueProcesoDTO;
 	}
  
}