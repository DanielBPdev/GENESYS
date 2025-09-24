package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/reversion/retiros
 */
public class RealizarReversionRetiros extends ServiceClient { 
    	private List<CuentaAdministradorSubsidioDTO> retirosReversion;
  
  
 	public RealizarReversionRetiros (List<CuentaAdministradorSubsidioDTO> retirosReversion){
 		super();
		this.retirosReversion=retirosReversion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(retirosReversion == null ? null : Entity.json(retirosReversion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRetirosReversion (List<CuentaAdministradorSubsidioDTO> retirosReversion){
 		this.retirosReversion=retirosReversion;
 	}
 	
 	public List<CuentaAdministradorSubsidioDTO> getRetirosReversion (){
 		return retirosReversion;
 	}
  
}