package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarMetodoCriterioGestionCobro
 */
public class ConsultarMetodoCriterioGestionCobro extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private MetodoAccionCobroEnum result;
  
 	public ConsultarMetodoCriterioGestionCobro (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (MetodoAccionCobroEnum) response.readEntity(MetodoAccionCobroEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public MetodoAccionCobroEnum getResult() {
		return result;
	}

 
  
}