package com.asopagos.fovis.clients;

import java.lang.Long;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/calcularTotalMesesAhorroProgramado/{idPostulacionFOVIS}
 */
public class CalcularTotalMesesAhorroProgramado extends ServiceClient {
 
  	private Long idPostulacionFOVIS;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Integer result;
  
 	public CalcularTotalMesesAhorroProgramado (Long idPostulacionFOVIS){
 		super();
		this.idPostulacionFOVIS=idPostulacionFOVIS;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idPostulacionFOVIS", idPostulacionFOVIS)
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

 	public void setIdPostulacionFOVIS (Long idPostulacionFOVIS){
 		this.idPostulacionFOVIS=idPostulacionFOVIS;
 	}
 	
 	public Long getIdPostulacionFOVIS (){
 		return idPostulacionFOVIS;
 	}
  
  
}