package com.asopagos.aportes.clients;

import java.util.List;
import com.asopagos.dto.AportanteDiaVencimientoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/actualizarDiaHabilVencimientoAporte
 */
public class ActualizarDiaHabilVencimientoAporte extends ServiceClient { 
    	private List<AportanteDiaVencimientoDTO> aportantesPorActualizar;
  
  
 	public ActualizarDiaHabilVencimientoAporte (List<AportanteDiaVencimientoDTO> aportantesPorActualizar){
 		super();
		this.aportantesPorActualizar=aportantesPorActualizar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aportantesPorActualizar == null ? null : Entity.json(aportantesPorActualizar));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAportantesPorActualizar (List<AportanteDiaVencimientoDTO> aportantesPorActualizar){
 		this.aportantesPorActualizar=aportantesPorActualizar;
 	}
 	
 	public List<AportanteDiaVencimientoDTO> getAportantesPorActualizar (){
 		return aportantesPorActualizar;
 	}
  
}