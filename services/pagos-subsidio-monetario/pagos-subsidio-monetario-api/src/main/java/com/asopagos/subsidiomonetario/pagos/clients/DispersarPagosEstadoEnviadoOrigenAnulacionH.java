package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/dispersarPagos/estadoEnviadoH/origenAnulacionH
 */
public class DispersarPagosEstadoEnviadoOrigenAnulacionH extends ServiceClient { 
    	private List<Long> identificadoresCuentas;
  
  
 	public DispersarPagosEstadoEnviadoOrigenAnulacionH (List<Long> identificadoresCuentas){
 		super();
		this.identificadoresCuentas=identificadoresCuentas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(identificadoresCuentas == null ? null : Entity.json(identificadoresCuentas));
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