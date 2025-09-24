package com.asopagos.novedades.fovis.clients;

import com.asopagos.dto.modelo.SolicitudAnalisisNovedadFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/crearActualizarSolicitudAnalisisNovedadFOVIS
 */
public class CrearActualizarSolicitudAnalisisNovedadFOVIS extends ServiceClient { 
    	private SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAnalisisNovedadFOVISModeloDTO result;
  
 	public CrearActualizarSolicitudAnalisisNovedadFOVIS (SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO){
 		super();
		this.solicitudAnalisisNovedadFOVISModeloDTO=solicitudAnalisisNovedadFOVISModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAnalisisNovedadFOVISModeloDTO == null ? null : Entity.json(solicitudAnalisisNovedadFOVISModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudAnalisisNovedadFOVISModeloDTO) response.readEntity(SolicitudAnalisisNovedadFOVISModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudAnalisisNovedadFOVISModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudAnalisisNovedadFOVISModeloDTO (SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO){
 		this.solicitudAnalisisNovedadFOVISModeloDTO=solicitudAnalisisNovedadFOVISModeloDTO;
 	}
 	
 	public SolicitudAnalisisNovedadFOVISModeloDTO getSolicitudAnalisisNovedadFOVISModeloDTO (){
 		return solicitudAnalisisNovedadFOVISModeloDTO;
 	}
  
}