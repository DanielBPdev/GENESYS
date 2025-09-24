package com.asopagos.novedades.fovis.clients;

import com.asopagos.entidades.ccf.fovis.SolicitudNovedadPersonaFovis;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/crearActualizarSolicitudNovedadPersonaFovis
 */
public class CrearActualizarSolicitudNovedadPersonaFovis extends ServiceClient { 
    	private SolicitudNovedadPersonaFovis solicitudNovedadPersonaFovis;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadPersonaFovis result;
  
 	public CrearActualizarSolicitudNovedadPersonaFovis (SolicitudNovedadPersonaFovis solicitudNovedadPersonaFovis){
 		super();
		this.solicitudNovedadPersonaFovis=solicitudNovedadPersonaFovis;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudNovedadPersonaFovis == null ? null : Entity.json(solicitudNovedadPersonaFovis));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadPersonaFovis) response.readEntity(SolicitudNovedadPersonaFovis.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadPersonaFovis getResult() {
		return result;
	}

 
  
  	public void setSolicitudNovedadPersonaFovis (SolicitudNovedadPersonaFovis solicitudNovedadPersonaFovis){
 		this.solicitudNovedadPersonaFovis=solicitudNovedadPersonaFovis;
 	}
 	
 	public SolicitudNovedadPersonaFovis getSolicitudNovedadPersonaFovis (){
 		return solicitudNovedadPersonaFovis;
 	}
  
}