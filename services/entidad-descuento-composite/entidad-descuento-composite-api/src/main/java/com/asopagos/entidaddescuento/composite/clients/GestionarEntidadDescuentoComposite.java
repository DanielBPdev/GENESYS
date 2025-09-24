package com.asopagos.entidaddescuento.composite.clients;

import java.lang.String;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/EntidadDescuentoComposite/gestionarEntidadDescuentoComposite
 */
public class GestionarEntidadDescuentoComposite extends ServiceClient { 
    	private EntidadDescuentoModeloDTO entidadDescuentoModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public GestionarEntidadDescuentoComposite (EntidadDescuentoModeloDTO entidadDescuentoModeloDTO){
 		super();
		this.entidadDescuentoModeloDTO=entidadDescuentoModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entidadDescuentoModeloDTO == null ? null : Entity.json(entidadDescuentoModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 
  
  	public void setEntidadDescuentoModeloDTO (EntidadDescuentoModeloDTO entidadDescuentoModeloDTO){
 		this.entidadDescuentoModeloDTO=entidadDescuentoModeloDTO;
 	}
 	
 	public EntidadDescuentoModeloDTO getEntidadDescuentoModeloDTO (){
 		return entidadDescuentoModeloDTO;
 	}
  
}