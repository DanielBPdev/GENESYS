package com.asopagos.novedades.clients;

import java.lang.Long;
import com.asopagos.dto.DiferenciasCargueActualizacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesCargueMultiple/crearDiferenciaArchivo
 */
public class CrearDiferenciaArchivoActualizacion extends ServiceClient { 
    	private DiferenciasCargueActualizacionDTO diferenciasCargueActualizacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearDiferenciaArchivoActualizacion (DiferenciasCargueActualizacionDTO diferenciasCargueActualizacionDTO){
 		super();
		this.diferenciasCargueActualizacionDTO=diferenciasCargueActualizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(diferenciasCargueActualizacionDTO == null ? null : Entity.json(diferenciasCargueActualizacionDTO));
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

 
  
  	public void setDiferenciasCargueActualizacionDTO (DiferenciasCargueActualizacionDTO diferenciasCargueActualizacionDTO){
 		this.diferenciasCargueActualizacionDTO=diferenciasCargueActualizacionDTO;
 	}
 	
 	public DiferenciasCargueActualizacionDTO getDiferenciasCargueActualizacionDTO (){
 		return diferenciasCargueActualizacionDTO;
 	}
  
}