package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarDetalleCicloAportante/{numeroRadicado}
 */
public class ConsultarDetalleCicloAportante extends ServiceClient {
 
  	private String numeroRadicado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SimulacionDTO result;
  
 	public ConsultarDetalleCicloAportante (String numeroRadicado){
 		super();
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicado", numeroRadicado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SimulacionDTO) response.readEntity(SimulacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SimulacionDTO getResult() {
		return result;
	}

 	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  
}