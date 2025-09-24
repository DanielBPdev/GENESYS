package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/guardarSolicitudCierreRecaudo
 */
public class GuardarSolicitudCierreRecaudo extends ServiceClient { 
    	private SolicitudCierreRecaudoModeloDTO solicitudCierreDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public GuardarSolicitudCierreRecaudo (SolicitudCierreRecaudoModeloDTO solicitudCierreDTO){
 		super();
		this.solicitudCierreDTO=solicitudCierreDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		System.out.println(" Path GuardarSolicitudCierreRecaudo  "+path);
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudCierreDTO == null ? null : Entity.json(solicitudCierreDTO));
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

 
  
  	public void setSolicitudCierreDTO (SolicitudCierreRecaudoModeloDTO solicitudCierreDTO){
 		this.solicitudCierreDTO=solicitudCierreDTO;
 	}
 	
 	public SolicitudCierreRecaudoModeloDTO getSolicitudCierreDTO (){
 		return solicitudCierreDTO;
 	}
  
}