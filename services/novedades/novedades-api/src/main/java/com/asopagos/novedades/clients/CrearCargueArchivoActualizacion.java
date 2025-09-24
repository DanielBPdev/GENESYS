package com.asopagos.novedades.clients;

import java.lang.Long;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesCargueMultiple/crearCargueArchivoActualizacion
 */
public class CrearCargueArchivoActualizacion extends ServiceClient { 
    	private CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearCargueArchivoActualizacion (CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO){
 		super();
		this.cargueArchivoActualizacionDTO=cargueArchivoActualizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cargueArchivoActualizacionDTO == null ? null : Entity.json(cargueArchivoActualizacionDTO));
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

 
  
  	public void setCargueArchivoActualizacionDTO (CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO){
 		this.cargueArchivoActualizacionDTO=cargueArchivoActualizacionDTO;
 	}
 	
 	public CargueArchivoActualizacionDTO getCargueArchivoActualizacionDTO (){
 		return cargueArchivoActualizacionDTO;
 	}
  
}