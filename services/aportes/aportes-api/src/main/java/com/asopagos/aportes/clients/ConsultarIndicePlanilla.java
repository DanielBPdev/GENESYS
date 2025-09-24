package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarIndicePlanilla/{idPlanilla}
 */
public class ConsultarIndicePlanilla extends ServiceClient {
 
  	private Long idPlanilla;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private IndicePlanillaModeloDTO result;
  
 	public ConsultarIndicePlanilla (Long idPlanilla){
 		super();
		this.idPlanilla=idPlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idPlanilla", idPlanilla)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (IndicePlanillaModeloDTO) response.readEntity(IndicePlanillaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public IndicePlanillaModeloDTO getResult() {
		return result;
	}

 	public void setIdPlanilla (Long idPlanilla){
 		this.idPlanilla=idPlanilla;
 	}
 	
 	public Long getIdPlanilla (){
 		return idPlanilla;
 	}
  
  
}