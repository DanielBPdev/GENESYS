package com.asopagos.cartera.clients;

import java.lang.String;
import com.asopagos.enumeraciones.cartera.ParametrizacionEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/parametrizacion/consultarDatoTemporalParametrizacion
 */
public class ConsultarDatoTemporalParametrizacion extends ServiceClient {
 
  
  	private ParametrizacionEnum parametrizacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ConsultarDatoTemporalParametrizacion (ParametrizacionEnum parametrizacion){
 		super();
		this.parametrizacion=parametrizacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("parametrizacion", parametrizacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}

 
  	public void setParametrizacion (ParametrizacionEnum parametrizacion){
 		this.parametrizacion=parametrizacion;
 	}
 	
 	public ParametrizacionEnum getParametrizacion (){
 		return parametrizacion;
 	}
  
}