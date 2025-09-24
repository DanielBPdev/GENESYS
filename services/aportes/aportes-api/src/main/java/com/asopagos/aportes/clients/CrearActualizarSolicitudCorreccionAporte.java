package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearActualizarSolicitudCorreccionAporte
 */
public class CrearActualizarSolicitudCorreccionAporte extends ServiceClient { 
    	private SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearActualizarSolicitudCorreccionAporte (SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO){
 		super();
		this.solicitudCorreccionAporteDTO=solicitudCorreccionAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudCorreccionAporteDTO == null ? null : Entity.json(solicitudCorreccionAporteDTO));
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

 
  
  	public void setSolicitudCorreccionAporteDTO (SolicitudCorreccionAporteModeloDTO solicitudCorreccionAporteDTO){
 		this.solicitudCorreccionAporteDTO=solicitudCorreccionAporteDTO;
 	}
 	
 	public SolicitudCorreccionAporteModeloDTO getSolicitudCorreccionAporteDTO (){
 		return solicitudCorreccionAporteDTO;
 	}
  
}