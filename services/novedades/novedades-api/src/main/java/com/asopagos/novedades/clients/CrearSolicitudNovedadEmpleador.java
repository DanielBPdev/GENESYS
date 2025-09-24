package com.asopagos.novedades.clients;

import com.asopagos.entidades.ccf.novedades.SolicitudNovedadEmpleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/crearSolicitudNovedadEmpleador
 */
public class CrearSolicitudNovedadEmpleador extends ServiceClient { 
    	private SolicitudNovedadEmpleador solicitudNovedadEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadEmpleador result;
  
 	public CrearSolicitudNovedadEmpleador (SolicitudNovedadEmpleador solicitudNovedadEmpleador){
 		super();
		this.solicitudNovedadEmpleador=solicitudNovedadEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudNovedadEmpleador == null ? null : Entity.json(solicitudNovedadEmpleador));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadEmpleador) response.readEntity(SolicitudNovedadEmpleador.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadEmpleador getResult() {
		return result;
	}

 
  
  	public void setSolicitudNovedadEmpleador (SolicitudNovedadEmpleador solicitudNovedadEmpleador){
 		this.solicitudNovedadEmpleador=solicitudNovedadEmpleador;
 	}
 	
 	public SolicitudNovedadEmpleador getSolicitudNovedadEmpleador (){
 		return solicitudNovedadEmpleador;
 	}
  
}