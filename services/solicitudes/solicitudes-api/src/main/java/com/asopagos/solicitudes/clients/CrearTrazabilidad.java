package com.asopagos.solicitudes.clients;

import com.asopagos.dto.modelo.RegistroEstadoAporteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/crearTrazabilidad
 */
public class CrearTrazabilidad extends ServiceClient { 
    	private RegistroEstadoAporteModeloDTO trazabilidadDTO;
  
  
 	public CrearTrazabilidad (RegistroEstadoAporteModeloDTO trazabilidadDTO){
 		super();
		this.trazabilidadDTO=trazabilidadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(trazabilidadDTO == null ? null : Entity.json(trazabilidadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setTrazabilidadDTO (RegistroEstadoAporteModeloDTO trazabilidadDTO){
 		this.trazabilidadDTO=trazabilidadDTO;
 	}
 	
 	public RegistroEstadoAporteModeloDTO getTrazabilidadDTO (){
 		return trazabilidadDTO;
 	}
  
}