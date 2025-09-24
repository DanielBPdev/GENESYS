package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.enumeraciones.aportes.TipoInconsistenciasEnum;
import javax.ws.rs.core.GenericType;
import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/accionBandejaInconsistencias
 */
public class AccionBandejaInconsistencias extends ServiceClient { 
    	private InconsistenciaDTO inconsistencia;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TipoInconsistenciasEnum> result;
  
 	public AccionBandejaInconsistencias (InconsistenciaDTO inconsistencia){
 		super();
		this.inconsistencia=inconsistencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inconsistencia == null ? null : Entity.json(inconsistencia));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<TipoInconsistenciasEnum>) response.readEntity(new GenericType<List<TipoInconsistenciasEnum>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<TipoInconsistenciasEnum> getResult() {
		return result;
	}

 
  
  	public void setInconsistencia (InconsistenciaDTO inconsistencia){
 		this.inconsistencia=inconsistencia;
 	}
 	
 	public InconsistenciaDTO getInconsistencia (){
 		return inconsistencia;
 	}
  
}