package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import com.asopagos.bandejainconsistencias.dto.CreacionAportanteDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/crearAportante
 */
public class CrearAportante extends ServiceClient { 
   	private Long numeroPlanilla;
   	private InconsistenciaDTO inconsistencia;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CreacionAportanteDTO result;
  
 	public CrearAportante (Long numeroPlanilla,InconsistenciaDTO inconsistencia){
 		super();
		this.numeroPlanilla=numeroPlanilla;
		this.inconsistencia=inconsistencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroPlanilla", numeroPlanilla)
			.request(MediaType.APPLICATION_JSON)
			.post(inconsistencia == null ? null : Entity.json(inconsistencia));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (CreacionAportanteDTO) response.readEntity(CreacionAportanteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public CreacionAportanteDTO getResult() {
		return result;
	}

 
  	public void setNumeroPlanilla (Long numeroPlanilla){
 		this.numeroPlanilla=numeroPlanilla;
 	}
 	
 	public Long getNumeroPlanilla (){
 		return numeroPlanilla;
 	}
  
  	public void setInconsistencia (InconsistenciaDTO inconsistencia){
 		this.inconsistencia=inconsistencia;
 	}
 	
 	public InconsistenciaDTO getInconsistencia (){
 		return inconsistencia;
 	}
  
}