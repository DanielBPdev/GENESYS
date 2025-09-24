package com.asopagos.aportes.composite.clients;

import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/validarTotalAportesCorreccion
 */
public class ValidarTotalAportesCorreccion extends ServiceClient { 
    	private CorreccionAportanteDTO correccion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarTotalAportesCorreccion (CorreccionAportanteDTO correccion){
 		super();
		this.correccion=correccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(correccion == null ? null : Entity.json(correccion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setCorreccion (CorreccionAportanteDTO correccion){
 		this.correccion=correccion;
 	}
 	
 	public CorreccionAportanteDTO getCorreccion (){
 		return correccion;
 	}
  
}