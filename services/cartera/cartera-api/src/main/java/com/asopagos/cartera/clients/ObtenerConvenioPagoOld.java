package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.cartera.dto.IntegracionCarteraDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/cartera/obtenerConvenioPagoOld
 */
public class ObtenerConvenioPagoOld extends ServiceClient {
 
  
  	private Long numeroConvenioPago;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private IntegracionCarteraDTO result;
  
 	public ObtenerConvenioPagoOld (Long numeroConvenioPago){
 		super();
		this.numeroConvenioPago=numeroConvenioPago;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroConvenioPago", numeroConvenioPago)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (IntegracionCarteraDTO) response.readEntity(IntegracionCarteraDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public IntegracionCarteraDTO getResult() {
		return result;
	}

 
  	public void setNumeroConvenioPago (Long numeroConvenioPago){
 		this.numeroConvenioPago=numeroConvenioPago;
 	}
 	
 	public Long getNumeroConvenioPago (){
 		return numeroConvenioPago;
 	}
  
}