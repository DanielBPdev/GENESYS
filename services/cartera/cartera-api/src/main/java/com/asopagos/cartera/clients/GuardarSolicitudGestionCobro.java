package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.SolicitudGestionCobroFisicoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarSolicitudGestionCobro
 */
public class GuardarSolicitudGestionCobro extends ServiceClient { 
    	private SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudGestionCobroFisicoModeloDTO result;
  
 	public GuardarSolicitudGestionCobro (SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroDTO){
 		super();
		this.solicitudGestionCobroDTO=solicitudGestionCobroDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudGestionCobroDTO == null ? null : Entity.json(solicitudGestionCobroDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudGestionCobroFisicoModeloDTO) response.readEntity(SolicitudGestionCobroFisicoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudGestionCobroFisicoModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudGestionCobroDTO (SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroDTO){
 		this.solicitudGestionCobroDTO=solicitudGestionCobroDTO;
 	}
 	
 	public SolicitudGestionCobroFisicoModeloDTO getSolicitudGestionCobroDTO (){
 		return solicitudGestionCobroDTO;
 	}
  
}