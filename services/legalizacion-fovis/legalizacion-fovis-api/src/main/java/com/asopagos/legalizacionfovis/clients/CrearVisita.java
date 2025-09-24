package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.modelo.VisitaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/crearVisita
 */
public class CrearVisita extends ServiceClient { 
    	private VisitaModeloDTO visitaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private VisitaModeloDTO result;
  
 	public CrearVisita (VisitaModeloDTO visitaDTO){
 		super();
		this.visitaDTO=visitaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(visitaDTO == null ? null : Entity.json(visitaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (VisitaModeloDTO) response.readEntity(VisitaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public VisitaModeloDTO getResult() {
		return result;
	}

 
  
  	public void setVisitaDTO (VisitaModeloDTO visitaDTO){
 		this.visitaDTO=visitaDTO;
 	}
 	
 	public VisitaModeloDTO getVisitaDTO (){
 		return visitaDTO;
 	}
  
}