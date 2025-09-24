package com.asopagos.sat.clients;

import com.asopagos.sat.dto.RespuestaEstandar;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/sat/registrarMotivoRechazo
 */
public class RegistrarMotivoRechazo extends ServiceClient {
 
  
  	private String id;
  	private String motivoRechazo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaEstandar result;
  
 	public RegistrarMotivoRechazo (String id,String motivoRechazo){
 		super();
		this.id=id;
		this.motivoRechazo=motivoRechazo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("id", id)
						.queryParam("motivoRechazo", motivoRechazo)
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
  	public void setMotivoRechazo (String motivoRechazo){
 		this.motivoRechazo=motivoRechazo;
 	}
 	
 	public String getMotivoRechazo (){
 		return motivoRechazo;
 	}
  
}