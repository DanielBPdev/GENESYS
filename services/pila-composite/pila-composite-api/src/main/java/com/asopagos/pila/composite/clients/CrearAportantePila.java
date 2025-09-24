package com.asopagos.pila.composite.clients;

import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/crearAportantePila
 */
public class CrearAportantePila extends ServiceClient { 
   	private Long idPlanilla;
   	private InconsistenciaDTO inconsistencia;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InconsistenciaDTO result;
  
 	public CrearAportantePila (Long idPlanilla,InconsistenciaDTO inconsistencia){
 		super();
		this.idPlanilla=idPlanilla;
		this.inconsistencia=inconsistencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idPlanilla", idPlanilla)
			.request(MediaType.APPLICATION_JSON)
			.post(inconsistencia == null ? null : Entity.json(inconsistencia));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (InconsistenciaDTO) response.readEntity(InconsistenciaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public InconsistenciaDTO getResult() {
		return result;
	}

 
  	public void setIdPlanilla (Long idPlanilla){
 		this.idPlanilla=idPlanilla;
 	}
 	
 	public Long getIdPlanilla (){
 		return idPlanilla;
 	}
  
  	public void setInconsistencia (InconsistenciaDTO inconsistencia){
 		this.inconsistencia=inconsistencia;
 	}
 	
 	public InconsistenciaDTO getInconsistencia (){
 		return inconsistencia;
 	}
  
}