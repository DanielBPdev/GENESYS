package com.asopagos.aportes.clients;

import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/validarGeneracionCierre
 */
public class ValidarGeneracionCierre extends ServiceClient {
 
  
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarGeneracionCierre (Long fechaFin,Long fechaInicio){
 		super();
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {

		System.out.println(" ValidarGeneracionCierre - PATH -> "+path);

		Response response = webTarget.path(path)
									.queryParam("fechaFin", fechaFin)
						            .queryParam("fechaInicio", fechaInicio)
						            .request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
}