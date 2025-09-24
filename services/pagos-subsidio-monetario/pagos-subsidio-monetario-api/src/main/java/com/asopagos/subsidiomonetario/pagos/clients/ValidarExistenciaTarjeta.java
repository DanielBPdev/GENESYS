package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.Boolean;

import com.asopagos.services.common.ServiceClient;

public class ValidarExistenciaTarjeta extends ServiceClient{

    private String numeroExpedido;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarExistenciaTarjeta (String numeroExpedido){
 		super();
		this.numeroExpedido=numeroExpedido;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroExpedido", numeroExpedido)
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
    
}
