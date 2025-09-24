package com.asopagos.entidades.pagadoras.composite.clients;

import java.lang.Long;
import com.asopagos.entidades.pagadoras.dto.EntidadPagadoraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadesPagadorasComposite/registrar
 */
public class RegistrarEntidadPagadora extends ServiceClient { 
    	private EntidadPagadoraDTO entidadPagadoraDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarEntidadPagadora (EntidadPagadoraDTO entidadPagadoraDTO){
 		super();
		this.entidadPagadoraDTO=entidadPagadoraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entidadPagadoraDTO == null ? null : Entity.json(entidadPagadoraDTO));
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

 
  
  	public void setEntidadPagadoraDTO (EntidadPagadoraDTO entidadPagadoraDTO){
 		this.entidadPagadoraDTO=entidadPagadoraDTO;
 	}
 	
 	public EntidadPagadoraDTO getEntidadPagadoraDTO (){
 		return entidadPagadoraDTO;
 	}
  
}