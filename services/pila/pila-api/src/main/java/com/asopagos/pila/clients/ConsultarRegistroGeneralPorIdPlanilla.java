package com.asopagos.pila.clients;

import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarRegistroGeneralPorIdPlanilla
 */
public class ConsultarRegistroGeneralPorIdPlanilla extends ServiceClient {
 
  
  	private Long idIndicePlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RegistroGeneralModeloDTO result;
  
 	public ConsultarRegistroGeneralPorIdPlanilla (Long idIndicePlanilla){
 		super();
		this.idIndicePlanilla=idIndicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idIndicePlanilla", idIndicePlanilla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RegistroGeneralModeloDTO) response.readEntity(RegistroGeneralModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RegistroGeneralModeloDTO getResult() {
		return result;
	}

 
  	public void setIdIndicePlanilla (Long idIndicePlanilla){
 		this.idIndicePlanilla=idIndicePlanilla;
 	}
 	
 	public Long getIdIndicePlanilla (){
 		return idIndicePlanilla;
 	}
  
}