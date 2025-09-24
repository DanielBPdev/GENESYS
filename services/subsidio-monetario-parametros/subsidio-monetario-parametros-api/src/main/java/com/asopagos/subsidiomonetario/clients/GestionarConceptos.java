package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/gestionarConceptos
 */
public class GestionarConceptos extends ServiceClient { 
    	private List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public GestionarConceptos (List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq){
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
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 
  
  	public void setParametrizacionesLiq (List<ParametrizacionLiquidacionSubsidioModeloDTO> parametrizacionesLiq){
 		this.parametrizacionesLiq=parametrizacionesLiq;
 	}
 	
 	public List<ParametrizacionLiquidacionSubsidioModeloDTO> getParametrizacionesLiq (){
 		return parametrizacionesLiq;
 	}
  
}