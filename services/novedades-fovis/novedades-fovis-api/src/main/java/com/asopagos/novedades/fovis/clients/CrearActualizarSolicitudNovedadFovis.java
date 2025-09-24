package com.asopagos.novedades.fovis.clients;

import com.asopagos.dto.modelo.SolicitudNovedadFovisModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/crearActualizarSolicitudNovedadFovis
 */
public class CrearActualizarSolicitudNovedadFovis extends ServiceClient { 
    	private SolicitudNovedadFovisModeloDTO solicitudNovedadFovisModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadFovisModeloDTO result;
  
 	public CrearActualizarSolicitudNovedadFovis (SolicitudNovedadFovisModeloDTO solicitudNovedadFovisModeloDTO){
 		super();
		this.solicitudNovedadFovisModeloDTO=solicitudNovedadFovisModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudNovedadFovisModeloDTO == null ? null : Entity.json(solicitudNovedadFovisModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadFovisModeloDTO) response.readEntity(SolicitudNovedadFovisModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadFovisModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudNovedadFovisModeloDTO (SolicitudNovedadFovisModeloDTO solicitudNovedadFovisModeloDTO){
 		this.solicitudNovedadFovisModeloDTO=solicitudNovedadFovisModeloDTO;
 	}
 	
 	public SolicitudNovedadFovisModeloDTO getSolicitudNovedadFovisModeloDTO (){
 		return solicitudNovedadFovisModeloDTO;
 	}
  
}