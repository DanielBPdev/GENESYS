package com.asopagos.novedades.clients;

import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/crearSolicitudNovedadPersona
 */
public class CrearSolicitudNovedadPersona extends ServiceClient { 
    	private SolicitudNovedadPersona solicitudNovedadPersona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadPersona result;
  
 	public CrearSolicitudNovedadPersona (SolicitudNovedadPersona solicitudNovedadPersona){
 		super();
		this.solicitudNovedadPersona=solicitudNovedadPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudNovedadPersona == null ? null : Entity.json(solicitudNovedadPersona));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadPersona) response.readEntity(SolicitudNovedadPersona.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadPersona getResult() {
		return result;
	}

 
  
  	public void setSolicitudNovedadPersona (SolicitudNovedadPersona solicitudNovedadPersona){
 		this.solicitudNovedadPersona=solicitudNovedadPersona;
 	}
 	
 	public SolicitudNovedadPersona getSolicitudNovedadPersona (){
 		return solicitudNovedadPersona;
 	}
  
}