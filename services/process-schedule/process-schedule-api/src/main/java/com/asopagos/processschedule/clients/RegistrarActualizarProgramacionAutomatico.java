package com.asopagos.processschedule.clients;

import java.util.List;
import com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/processSchedule/registrarActualizarProgramacion
 */
public class RegistrarActualizarProgramacionAutomatico extends ServiceClient { 
    	private List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones;
  
  
 	public RegistrarActualizarProgramacionAutomatico (List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones){
 		super();
		this.programaciones=programaciones;
 	}
  
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(programaciones == null ? null : Entity.json(programaciones));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setProgramaciones (List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones){
 		this.programaciones=programaciones;
 	}
 	
 	public List<ParametrizacionEjecucionProgramadaModeloDTO> getProgramaciones (){
 		return programaciones;
 	}
  
}