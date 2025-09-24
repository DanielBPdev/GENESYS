package com.asopagos.subsidiomonetario.clients;

import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/registrar/aplicacionValidacionSubsidio
 */
public class RegistrarAplicacionValidacionSubsidio extends ServiceClient { 
    	private AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarAplicacionValidacionSubsidio (AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO){
 		super();
		this.aplicacionValidacionDTO=aplicacionValidacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aplicacionValidacionDTO == null ? null : Entity.json(aplicacionValidacionDTO));
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

 
  
  	public void setAplicacionValidacionDTO (AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO){
 		this.aplicacionValidacionDTO=aplicacionValidacionDTO;
 	}
 	
 	public AplicacionValidacionSubsidioModeloDTO getAplicacionValidacionDTO (){
 		return aplicacionValidacionDTO;
 	}
  
}