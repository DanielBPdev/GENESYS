package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearActualizarSolicitudDevolucionAporte
 */
public class CrearActualizarSolicitudDevolucionAporte extends ServiceClient { 
    	private SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearActualizarSolicitudDevolucionAporte (SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO){
 		super();
		this.solicitudDevolucionAporteDTO=solicitudDevolucionAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudDevolucionAporteDTO == null ? null : Entity.json(solicitudDevolucionAporteDTO));
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

 
  
  	public void setSolicitudDevolucionAporteDTO (SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO){
 		this.solicitudDevolucionAporteDTO=solicitudDevolucionAporteDTO;
 	}
 	
 	public SolicitudDevolucionAporteModeloDTO getSolicitudDevolucionAporteDTO (){
 		return solicitudDevolucionAporteDTO;
 	}
  
}