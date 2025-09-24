package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovisComposite/radicarSolicitudNovedadAutomaticaFovis
 */
public class RadicarSolicitudNovedadAutomaticaFovis extends ServiceClient { 
    	private SolicitudNovedadFovisDTO solNovedadFovisDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadFovisDTO result;
  
 	public RadicarSolicitudNovedadAutomaticaFovis (SolicitudNovedadFovisDTO solNovedadFovisDTO){
 		super();
		this.solNovedadFovisDTO=solNovedadFovisDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solNovedadFovisDTO == null ? null : Entity.json(solNovedadFovisDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadFovisDTO) response.readEntity(SolicitudNovedadFovisDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadFovisDTO getResult() {
		return result;
	}

 
  
  	public void setSolNovedadFovisDTO (SolicitudNovedadFovisDTO solNovedadFovisDTO){
 		this.solNovedadFovisDTO=solNovedadFovisDTO;
 	}
 	
 	public SolicitudNovedadFovisDTO getSolNovedadFovisDTO (){
 		return solNovedadFovisDTO;
 	}
  
}