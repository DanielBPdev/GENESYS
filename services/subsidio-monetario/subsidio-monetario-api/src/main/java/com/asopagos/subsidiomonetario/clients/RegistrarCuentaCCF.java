package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.modelo.dto.CuentaCCFModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/registrar/cuentaCCF
 */
public class RegistrarCuentaCCF extends ServiceClient { 
    	private CuentaCCFModeloDTO cuentaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarCuentaCCF (CuentaCCFModeloDTO cuentaDTO){
 		super();
		this.cuentaDTO=cuentaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cuentaDTO == null ? null : Entity.json(cuentaDTO));
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

 
  
  	public void setCuentaDTO (CuentaCCFModeloDTO cuentaDTO){
 		this.cuentaDTO=cuentaDTO;
 	}
 	
 	public CuentaCCFModeloDTO getCuentaDTO (){
 		return cuentaDTO;
 	}
  
}