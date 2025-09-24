package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/aprobarRegistroAporteConInconsistencia
 */
public class AprobarRegistroAporteConInconsistencia extends ServiceClient { 
    	private InconsistenciaRegistroAporteDTO inconsistenciaRegistroAporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InconsistenciaRegistroAporteDTO result;
  
 	public AprobarRegistroAporteConInconsistencia (InconsistenciaRegistroAporteDTO inconsistenciaRegistroAporteDTO){
 		super();
		this.inconsistenciaRegistroAporteDTO=inconsistenciaRegistroAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inconsistenciaRegistroAporteDTO == null ? null : Entity.json(inconsistenciaRegistroAporteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (InconsistenciaRegistroAporteDTO) response.readEntity(InconsistenciaRegistroAporteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public InconsistenciaRegistroAporteDTO getResult() {
		return result;
	}

 
  
  	public void setInconsistenciaRegistroAporteDTO (InconsistenciaRegistroAporteDTO inconsistenciaRegistroAporteDTO){
 		this.inconsistenciaRegistroAporteDTO=inconsistenciaRegistroAporteDTO;
 	}
 	
 	public InconsistenciaRegistroAporteDTO getInconsistenciaRegistroAporteDTO (){
 		return inconsistenciaRegistroAporteDTO;
 	}
  
}