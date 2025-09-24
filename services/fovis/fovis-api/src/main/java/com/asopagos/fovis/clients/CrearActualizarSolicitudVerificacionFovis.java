package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.SolicitudVerificacionFovisModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarSolicitudVerificacionFovis
 */
public class CrearActualizarSolicitudVerificacionFovis extends ServiceClient { 
    	private SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudVerificacionFovisModeloDTO result;
  
 	public CrearActualizarSolicitudVerificacionFovis (SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisDTO){
 		super();
		this.solicitudVerificacionFovisDTO=solicitudVerificacionFovisDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudVerificacionFovisDTO == null ? null : Entity.json(solicitudVerificacionFovisDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudVerificacionFovisModeloDTO) response.readEntity(SolicitudVerificacionFovisModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudVerificacionFovisModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudVerificacionFovisDTO (SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisDTO){
 		this.solicitudVerificacionFovisDTO=solicitudVerificacionFovisDTO;
 	}
 	
 	public SolicitudVerificacionFovisModeloDTO getSolicitudVerificacionFovisDTO (){
 		return solicitudVerificacionFovisDTO;
 	}
  
}