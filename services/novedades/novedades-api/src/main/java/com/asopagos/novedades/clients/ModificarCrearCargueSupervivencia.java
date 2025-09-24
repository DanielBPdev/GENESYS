package com.asopagos.novedades.clients;

import java.lang.Long;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesCargueMultiple/modificarCrearCargueSupervivencia
 */
public class ModificarCrearCargueSupervivencia extends ServiceClient { 
    	private ArchivoSupervivenciaDTO archivoSupervivenciaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ModificarCrearCargueSupervivencia (ArchivoSupervivenciaDTO archivoSupervivenciaDTO){
 		super();
		this.archivoSupervivenciaDTO=archivoSupervivenciaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoSupervivenciaDTO == null ? null : Entity.json(archivoSupervivenciaDTO));
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

 
  
  	public void setArchivoSupervivenciaDTO (ArchivoSupervivenciaDTO archivoSupervivenciaDTO){
 		this.archivoSupervivenciaDTO=archivoSupervivenciaDTO;
 	}
 	
 	public ArchivoSupervivenciaDTO getArchivoSupervivenciaDTO (){
 		return archivoSupervivenciaDTO;
 	}
  
}