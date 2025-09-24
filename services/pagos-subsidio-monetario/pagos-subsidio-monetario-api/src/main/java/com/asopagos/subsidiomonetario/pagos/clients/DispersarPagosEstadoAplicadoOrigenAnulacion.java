package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/dispersarPagos/estadoAplicado/origenAnulacion
 */
public class DispersarPagosEstadoAplicadoOrigenAnulacion extends ServiceClient {
 
  
  	private List<Long> identificadoresCuentas;
  
  
 	public DispersarPagosEstadoAplicadoOrigenAnulacion (List<Long> identificadoresCuentas){
 		super();
		this.identificadoresCuentas=identificadoresCuentas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("identificadoresCuentas", identificadoresCuentas.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdentificadoresCuentas (List<Long> identificadoresCuentas){
 		this.identificadoresCuentas=identificadoresCuentas;
 	}
 	
 	public List<Long> getIdentificadoresCuentas (){
 		return identificadoresCuentas;
 	}
  
}