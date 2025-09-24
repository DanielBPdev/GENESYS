package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearSolicitudAporte
 */
public class CrearSolicitudAporte extends ServiceClient { 
    	private SolicitudAporteModeloDTO solicitudAporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAporteModeloDTO result;
  
 	public CrearSolicitudAporte (SolicitudAporteModeloDTO solicitudAporteDTO){
 		super();
		this.solicitudAporteDTO=solicitudAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAporteDTO == null ? null : Entity.json(solicitudAporteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudAporteModeloDTO) response.readEntity(SolicitudAporteModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudAporteModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudAporteDTO (SolicitudAporteModeloDTO solicitudAporteDTO){
 		this.solicitudAporteDTO=solicitudAporteDTO;
 	}
 	
 	public SolicitudAporteModeloDTO getSolicitudAporteDTO (){
 		return solicitudAporteDTO;
 	}
  
}