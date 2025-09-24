package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/cargarArchivoConsumoTarjetaAnibol
 */
public class CargarArchivoConsumoTarjetaAnibol extends ServiceClient {
 
  
  
  
 	public CargarArchivoConsumoTarjetaAnibol (){
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
	}
	

 
  
}