package com.asopagos.novedades.clients;

import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/crearSolicitudNovedad
 */
public class CrearSolicitudNovedad extends ServiceClient { 
    	private SolicitudNovedadModeloDTO novedad;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadModeloDTO result;
  
 	public CrearSolicitudNovedad (SolicitudNovedadModeloDTO novedad){
 		super();
		this.novedad=novedad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(novedad == null ? null : Entity.json(novedad));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadModeloDTO) response.readEntity(SolicitudNovedadModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadModeloDTO getResult() {
		return result;
	}

 
  
  	public void setNovedad (SolicitudNovedadModeloDTO novedad){
 		this.novedad=novedad;
 	}
 	
 	public SolicitudNovedadModeloDTO getNovedad (){
 		return novedad;
 	}
  
}