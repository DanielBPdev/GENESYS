package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.DatoTemporalCarteraModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarDatoTemporalCartera
 */
public class ConsultarDatoTemporalCartera extends ServiceClient {
 
  
  	private Long numeroOperacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatoTemporalCarteraModeloDTO result;
  
 	public ConsultarDatoTemporalCartera (Long numeroOperacion){
 		super();
		this.numeroOperacion=numeroOperacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroOperacion", numeroOperacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DatoTemporalCarteraModeloDTO) response.readEntity(DatoTemporalCarteraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DatoTemporalCarteraModeloDTO getResult() {
		return result;
	}

 
  	public void setNumeroOperacion (Long numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public Long getNumeroOperacion (){
 		return numeroOperacion;
 	}
  
}