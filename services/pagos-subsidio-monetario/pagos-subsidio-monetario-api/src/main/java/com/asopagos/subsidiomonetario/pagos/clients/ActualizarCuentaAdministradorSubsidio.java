package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/actualizarCuentaAdministradorSubsidio
 */
public class ActualizarCuentaAdministradorSubsidio extends ServiceClient { 
    	private CuentaAdministradorSubsidioDTO cuenta;
  
  
 	public ActualizarCuentaAdministradorSubsidio (CuentaAdministradorSubsidioDTO cuenta){
 		super();
		this.cuenta=cuenta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cuenta == null ? null : Entity.json(cuenta));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCuenta (CuentaAdministradorSubsidioDTO cuenta){
 		this.cuenta=cuenta;
 	}
 	
 	public CuentaAdministradorSubsidioDTO getCuenta (){
 		return cuenta;
 	}
  
}