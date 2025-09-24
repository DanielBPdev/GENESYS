package com.asopagos.fovis.clients;

import java.lang.Long;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarAsignacionesPreviasHogar
 */
public class ConsultarCantidadAsignacionesPreviasHogar extends ServiceClient {
 
  
  	private Long identificadorPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Integer result;
  
 	public ConsultarCantidadAsignacionesPreviasHogar (Long identificadorPostulacion){
 		super();
		this.identificadorPostulacion=identificadorPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("identificadorPostulacion", identificadorPostulacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Integer) response.readEntity(Integer.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Integer getResult() {
		return result;
	}

 
  	public void setIdentificadorPostulacion (Long identificadorPostulacion){
 		this.identificadorPostulacion=identificadorPostulacion;
 	}
 	
 	public Long getIdentificadorPostulacion (){
 		return identificadorPostulacion;
 	}
  
}