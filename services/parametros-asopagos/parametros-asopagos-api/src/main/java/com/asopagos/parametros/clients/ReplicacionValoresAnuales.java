package com.asopagos.parametros.clients;

import java.util.List;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/parametros/valoresAnuales
 */
public class ReplicacionValoresAnuales extends ServiceClient { 
    	private List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq;
  
  
 	public ReplicacionValoresAnuales (List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq){
 		super();
		this.parametrizacionesLiq=parametrizacionesLiq;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionesLiq == null ? null : Entity.json(parametrizacionesLiq));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setParametrizacionesLiq (List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq){
 		this.parametrizacionesLiq=parametrizacionesLiq;
 	}
 	
 	public List<ParametrizacionLiquidacionSubsidioModeloDTO> getParametrizacionesLiq (){
 		return parametrizacionesLiq;
 	}
  
}