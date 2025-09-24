package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.CarteraModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarInformacionCarteraPorNumeroOperacion
 */
public class ConsultarInformacionCarteraPorNumeroOperacion extends ServiceClient {
 
  
  	private Long numeroOperacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CarteraModeloDTO result;
  
 	public ConsultarInformacionCarteraPorNumeroOperacion (Long numeroOperacion){
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
		this.result = (CarteraModeloDTO) response.readEntity(CarteraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CarteraModeloDTO getResult() {
		return result;
	}

 
  	public void setNumeroOperacion (Long numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public Long getNumeroOperacion (){
 		return numeroOperacion;
 	}
  
}