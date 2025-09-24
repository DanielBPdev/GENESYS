package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.aportes.AportePilaDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearNotificacionesParametrizadas
 */
public class CrearNotificacionesParametrizadas extends ServiceClient { 
    	private List<AportePilaDTO> planilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<NotificacionParametrizadaDTO> result;
  
 	public CrearNotificacionesParametrizadas (List<AportePilaDTO> planilla){
 		super();
		this.planilla=planilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(planilla == null ? null : Entity.json(planilla));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<NotificacionParametrizadaDTO>) response.readEntity(new GenericType<List<NotificacionParametrizadaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<NotificacionParametrizadaDTO> getResult() {
		return result;
	}

 
  
  	public void setPlanilla (List<AportePilaDTO> planilla){
 		this.planilla=planilla;
 	}
 	
 	public List<AportePilaDTO> getPlanilla (){
 		return planilla;
 	}
  
}