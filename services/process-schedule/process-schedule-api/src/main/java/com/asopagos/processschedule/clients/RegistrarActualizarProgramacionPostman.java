package com.asopagos.processschedule.clients;

import java.util.List;
import com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.rest.security.dto.UserDTO;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/processSchedule/registrarActualizarProgramacion
 */
public class RegistrarActualizarProgramacionPostman extends ServiceClient { 
    	private List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones;
		private String user;
		private String pass;
  
 	public RegistrarActualizarProgramacionPostman (List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones,String user, String pass){
 		super();
		this.user=user;
		this.pass=pass;
		this.programaciones=programaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("user", user)
			.resolveTemplate("pass", pass)
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