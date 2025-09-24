package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/obtenerValidacionesSolicitudNovedad
 */
public class ObtenerValidacionesSolicitudNovedad extends ServiceClient { 
    	private SolicitudNovedadDTO solicitudNovedadDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadDTO result;
  
 	public ObtenerValidacionesSolicitudNovedad (SolicitudNovedadDTO solicitudNovedadDTO){
 		super();
		this.solicitudNovedadDTO=solicitudNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudNovedadDTO == null ? null : Entity.json(solicitudNovedadDTO));
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

 
  
  	public void setSolicitudNovedadDTO (SolicitudNovedadDTO solicitudNovedadDTO){
 		this.solicitudNovedadDTO=solicitudNovedadDTO;
 	}
 	
 	public SolicitudNovedadDTO getSolicitudNovedadDTO (){
 		return solicitudNovedadDTO;
 	}
  
}