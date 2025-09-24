package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarSolicitudPostulacion
 */
public class CrearActualizarSolicitudPostulacion extends ServiceClient { 
    	private SolicitudPostulacionModeloDTO solicitudPostulacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPostulacionModeloDTO result;
  
 	public CrearActualizarSolicitudPostulacion (SolicitudPostulacionModeloDTO solicitudPostulacionDTO){
 		super();
		this.solicitudPostulacionDTO=solicitudPostulacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudPostulacionDTO == null ? null : Entity.json(solicitudPostulacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudPostulacionModeloDTO) response.readEntity(SolicitudPostulacionModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudPostulacionModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudPostulacionDTO (SolicitudPostulacionModeloDTO solicitudPostulacionDTO){
 		this.solicitudPostulacionDTO=solicitudPostulacionDTO;
 	}
 	
 	public SolicitudPostulacionModeloDTO getSolicitudPostulacionDTO (){
 		return solicitudPostulacionDTO;
 	}
  
}