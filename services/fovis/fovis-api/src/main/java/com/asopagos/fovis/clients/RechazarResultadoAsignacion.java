package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.SolicitudAsignacionFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/rechazarResultadoAsignacion
 */
public class RechazarResultadoAsignacion extends ServiceClient { 
    	private SolicitudAsignacionFOVISModeloDTO solicitudAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAsignacionFOVISModeloDTO result;
  
 	public RechazarResultadoAsignacion (SolicitudAsignacionFOVISModeloDTO solicitudAsignacion){
 		super();
		this.solicitudAsignacion=solicitudAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAsignacion == null ? null : Entity.json(solicitudAsignacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudAsignacionFOVISModeloDTO) response.readEntity(SolicitudAsignacionFOVISModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudAsignacionFOVISModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudAsignacion (SolicitudAsignacionFOVISModeloDTO solicitudAsignacion){
 		this.solicitudAsignacion=solicitudAsignacion;
 	}
 	
 	public SolicitudAsignacionFOVISModeloDTO getSolicitudAsignacion (){
 		return solicitudAsignacion;
 	}
  
}