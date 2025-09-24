package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.aportes.dto.ConsultaPlanillaResultDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarPlanilla/{idPlanilla}
 */
public class ConsultarPlanilla extends ServiceClient {
 
  	private Long idPlanilla;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConsultaPlanillaResultDTO result;
  
 	public ConsultarPlanilla (Long idPlanilla){
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
		this.result = (ConsultaPlanillaResultDTO) response.readEntity(ConsultaPlanillaResultDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConsultaPlanillaResultDTO getResult() {
		return result;
	}

 	public void setIdPlanilla (Long idPlanilla){
 		this.idPlanilla=idPlanilla;
 	}
 	
 	public Long getIdPlanilla (){
 		return idPlanilla;
 	}
  
  
}