package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultaParametrizacionAnexoLiquidacion
 */
public class ConsultaParametrizacionAnexoLiquidacion extends ServiceClient {
 
  
  	private TipoAccionCobroEnum accionCobro;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private boolean result;
  
 	public ConsultaParametrizacionAnexoLiquidacion (TipoAccionCobroEnum accionCobro){
 		super();
		this.accionCobro=accionCobro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("accionCobro", accionCobro)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (boolean) response.readEntity(boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public boolean getResult() {
		return result;
	}

 
  	public void setAccionCobro (TipoAccionCobroEnum accionCobro){
 		this.accionCobro=accionCobro;
 	}
 	
 	public TipoAccionCobroEnum getAccionCobro (){
 		return accionCobro;
 	}
  
}