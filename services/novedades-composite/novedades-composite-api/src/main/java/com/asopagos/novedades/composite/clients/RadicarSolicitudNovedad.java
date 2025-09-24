package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/radicarSolicitudNovedad
 */
public class RadicarSolicitudNovedad extends ServiceClient { 
    	private SolicitudNovedadDTO solNovedadDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadDTO result;
  
 	public RadicarSolicitudNovedad (SolicitudNovedadDTO solNovedadDTO){
 		super();
		this.solNovedadDTO=solNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solNovedadDTO == null ? null : Entity.json(solNovedadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadDTO) response.readEntity(SolicitudNovedadDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadDTO getResult() {
		return result;
	}

 
  
  	public void setSolNovedadDTO (SolicitudNovedadDTO solNovedadDTO){
 		this.solNovedadDTO=solNovedadDTO;
 	}
 	
 	public SolicitudNovedadDTO getSolNovedadDTO (){
 		return solNovedadDTO;
 	}
  
}