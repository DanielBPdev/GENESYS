package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudFiscalizacionModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarSolicitudFiscalizacion
 */
public class GuardarSolicitudFiscalizacion extends ServiceClient { 
    	private SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public GuardarSolicitudFiscalizacion (SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO){
 		super();
		this.solicitudFiscalizacionDTO=solicitudFiscalizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudFiscalizacionDTO == null ? null : Entity.json(solicitudFiscalizacionDTO));
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

 
  
  	public void setSolicitudFiscalizacionDTO (SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO){
 		this.solicitudFiscalizacionDTO=solicitudFiscalizacionDTO;
 	}
 	
 	public SolicitudFiscalizacionModeloDTO getSolicitudFiscalizacionDTO (){
 		return solicitudFiscalizacionDTO;
 	}
  
}