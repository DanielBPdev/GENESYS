package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.RegistroRemisionAportantesDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/{numeroRadicacion}/consultarAportantesEdictos
 */
public class ConsultarAportantesEdictos extends ServiceClient { 
  	private String numeroRadicacion;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RegistroRemisionAportantesDTO result;
  
 	public ConsultarAportantesEdictos (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (RegistroRemisionAportantesDTO) response.readEntity(RegistroRemisionAportantesDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RegistroRemisionAportantesDTO getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
  
}