package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.Response;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/exportarRetirosAbonosEstadoSolicitado
 */
public class ExportarRetirosAbonosEstadoSolicitado extends ServiceClient { 
   	private Long fechaInicial;
  	private Long fechaFinal;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public ExportarRetirosAbonosEstadoSolicitado (Long fechaInicial,Long fechaFinal){
 		super();
		this.fechaInicial=fechaInicial;
		this.fechaFinal=fechaFinal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("fechaInicial", fechaInicial)
			.queryParam("fechaFinal", fechaFinal)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Response) response.readEntity(Response.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Response getResult() {
		return result;
	}

 
  	public void setFechaInicial (Long fechaInicial){
 		this.fechaInicial=fechaInicial;
 	}
 	
 	public Long getFechaInicial (){
 		return fechaInicial;
 	}
  	public void setFechaFinal (Long fechaFinal){
 		this.fechaFinal=fechaFinal;
 	}
 	
 	public Long getFechaFinal (){
 		return fechaFinal;
 	}
  
  
}