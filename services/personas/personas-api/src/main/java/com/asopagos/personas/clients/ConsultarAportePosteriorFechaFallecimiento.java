package com.asopagos.personas.clients;

import java.lang.Boolean;
import com.asopagos.dto.ComparacionFechasDefuncionYAporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/ConsultarAportePosteriorFechaFallecimiento
 */
public class ConsultarAportePosteriorFechaFallecimiento extends ServiceClient { 
    	private ComparacionFechasDefuncionYAporteDTO comparacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ConsultarAportePosteriorFechaFallecimiento (ComparacionFechasDefuncionYAporteDTO comparacion){
 		super();
		this.comparacion=comparacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(comparacion == null ? null : Entity.json(comparacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setComparacion (ComparacionFechasDefuncionYAporteDTO comparacion){
 		this.comparacion=comparacion;
 	}
 	
 	public ComparacionFechasDefuncionYAporteDTO getComparacion (){
 		return comparacion;
 	}
  
}