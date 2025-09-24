package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.dto.modelo.NotificacionPersonalModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/crearNotificacionPersonal
 */
public class CrearNotificacionPersonal extends ServiceClient { 
    	private NotificacionPersonalModeloDTO notificacionPersonalDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DocumentoSoporteModeloDTO> result;
  
 	public CrearNotificacionPersonal (NotificacionPersonalModeloDTO notificacionPersonalDTO){
 		super();
		this.notificacionPersonalDTO=notificacionPersonalDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(notificacionPersonalDTO == null ? null : Entity.json(notificacionPersonalDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<DocumentoSoporteModeloDTO>) response.readEntity(new GenericType<List<DocumentoSoporteModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<DocumentoSoporteModeloDTO> getResult() {
		return result;
	}

 
  
  	public void setNotificacionPersonalDTO (NotificacionPersonalModeloDTO notificacionPersonalDTO){
 		this.notificacionPersonalDTO=notificacionPersonalDTO;
 	}
 	
 	public NotificacionPersonalModeloDTO getNotificacionPersonalDTO (){
 		return notificacionPersonalDTO;
 	}
  
}