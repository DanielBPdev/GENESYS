package com.asopagos.sat.clients;

import com.asopagos.sat.dto.RespuestaEstandar;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/sat/enviarASatIndividual
 */
public class EnviarASatIndividual extends ServiceClient {
 
  
  	private String id;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaEstandar result;
  
 	public EnviarASatIndividual (String id){
 		super();
		this.id=id;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("id", id)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RespuestaEstandar) response.readEntity(RespuestaEstandar.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RespuestaEstandar getResult() {
		return result;
	}

 
  	public void setId (String id){
 		this.id=id;
 	}
 	
 	public String getId (){
 		return id;
 	}
  
}