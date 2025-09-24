package com.asopagos.fovis.clients;

import com.asopagos.dto.CargueArchivoCruceFovisDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisCargue/crearCargueArchivoCruce
 */
public class CrearCargueArchivoCruce extends ServiceClient { 
    	private CargueArchivoCruceFovisDTO cargueArchivoCruceFovisDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearCargueArchivoCruce (CargueArchivoCruceFovisDTO cargueArchivoCruceFovisDTO){
 		super();
		this.cargueArchivoCruceFovisDTO=cargueArchivoCruceFovisDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cargueArchivoCruceFovisDTO == null ? null : Entity.json(cargueArchivoCruceFovisDTO));
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

 
  
  	public void setCargueArchivoCruceFovisDTO (CargueArchivoCruceFovisDTO cargueArchivoCruceFovisDTO){
 		this.cargueArchivoCruceFovisDTO=cargueArchivoCruceFovisDTO;
 	}
 	
 	public CargueArchivoCruceFovisDTO getCargueArchivoCruceFovisDTO (){
 		return cargueArchivoCruceFovisDTO;
 	}
  
}