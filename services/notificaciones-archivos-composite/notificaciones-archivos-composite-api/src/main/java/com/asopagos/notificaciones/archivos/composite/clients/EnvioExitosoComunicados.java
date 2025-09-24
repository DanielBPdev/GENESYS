package com.asopagos.notificaciones.archivos.composite.clients;

import java.util.List;
import java.lang.Boolean;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/notificaciones/envioExitosoComunicados
 */
public class EnvioExitosoComunicados extends ServiceClient { 
    	private List<NotificacionParametrizadaDTO> notificaciones;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public EnvioExitosoComunicados (List<NotificacionParametrizadaDTO> notificaciones){
 		super();
		this.notificaciones=notificaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(notificaciones == null ? null : Entity.json(notificaciones));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setNotificaciones (List<NotificacionParametrizadaDTO> notificaciones){
 		this.notificaciones=notificaciones;
 	}
 	
 	public List<NotificacionParametrizadaDTO> getNotificaciones (){
 		return notificaciones;
 	}
  
}