package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudGestionCobroManualModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarSolicitudGestionCobroManual
 */
public class GuardarSolicitudGestionCobroManual extends ServiceClient { 
    	private SolicitudGestionCobroManualModeloDTO solicitudGestionCobroDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public GuardarSolicitudGestionCobroManual (SolicitudGestionCobroManualModeloDTO solicitudGestionCobroDTO){
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
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setSolicitudGestionCobroDTO (SolicitudGestionCobroManualModeloDTO solicitudGestionCobroDTO){
 		this.solicitudGestionCobroDTO=solicitudGestionCobroDTO;
 	}
 	
 	public SolicitudGestionCobroManualModeloDTO getSolicitudGestionCobroDTO (){
 		return solicitudGestionCobroDTO;
 	}
  
}