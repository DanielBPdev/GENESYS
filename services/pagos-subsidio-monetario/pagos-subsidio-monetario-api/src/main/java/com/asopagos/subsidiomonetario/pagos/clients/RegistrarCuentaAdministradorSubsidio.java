package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/registrarCuentaAdministradorSubsidio
 */
public class RegistrarCuentaAdministradorSubsidio extends ServiceClient { 
    	private CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarCuentaAdministradorSubsidio (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO){
 		super();
		this.cuentaAdministradorSubsidioDTO=cuentaAdministradorSubsidioDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cuentaAdministradorSubsidioDTO == null ? null : Entity.json(cuentaAdministradorSubsidioDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setCuentaAdministradorSubsidioDTO (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO){
 		this.cuentaAdministradorSubsidioDTO=cuentaAdministradorSubsidioDTO;
 	}
 	
 	public CuentaAdministradorSubsidioDTO getCuentaAdministradorSubsidioDTO (){
 		return cuentaAdministradorSubsidioDTO;
 	}
  
}