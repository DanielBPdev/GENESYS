package com.asopagos.pila.clients;

import com.asopagos.dto.modelo.EstadoArchivoPorBloqueModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarEstadosIndicePlanillaPorId
 */
public class ConsultarEstadosIndicePlanillaPorId extends ServiceClient {
 
  
  	private Long idPlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoArchivoPorBloqueModeloDTO result;
  
 	public ConsultarEstadosIndicePlanillaPorId (Long idPlanilla){
 		super();
		this.idPlanilla=idPlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPlanilla", idPlanilla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EstadoArchivoPorBloqueModeloDTO) response.readEntity(EstadoArchivoPorBloqueModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EstadoArchivoPorBloqueModeloDTO getResult() {
		return result;
	}

 
  	public void setIdPlanilla (Long idPlanilla){
 		this.idPlanilla=idPlanilla;
 	}
 	
 	public Long getIdPlanilla (){
 		return idPlanilla;
 	}
  
}