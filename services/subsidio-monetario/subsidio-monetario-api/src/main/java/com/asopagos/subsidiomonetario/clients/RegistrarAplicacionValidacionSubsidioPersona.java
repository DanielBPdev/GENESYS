package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/registrar/aplicacionValidacionSubsidioPersona
 */
public class RegistrarAplicacionValidacionSubsidioPersona extends ServiceClient { 
    	private AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionPersonaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarAplicacionValidacionSubsidioPersona (AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionPersonaDTO){
 		super();
		this.aplicacionValidacionPersonaDTO=aplicacionValidacionPersonaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aplicacionValidacionPersonaDTO == null ? null : Entity.json(aplicacionValidacionPersonaDTO));
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

 
  
  	public void setAplicacionValidacionPersonaDTO (AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionPersonaDTO){
 		this.aplicacionValidacionPersonaDTO=aplicacionValidacionPersonaDTO;
 	}
 	
 	public AplicacionValidacionSubsidioPersonaModeloDTO getAplicacionValidacionPersonaDTO (){
 		return aplicacionValidacionPersonaDTO;
 	}
  
}