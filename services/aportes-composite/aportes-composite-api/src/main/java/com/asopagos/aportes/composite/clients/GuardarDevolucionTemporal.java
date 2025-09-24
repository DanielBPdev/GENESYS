package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.aportes.composite.dto.DevolucionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/guardarDevolucionTemporal
 */
public class GuardarDevolucionTemporal extends ServiceClient { 
   	private Long idSolicitud;
   	private DevolucionDTO devolucionDTO;
  
  
 	public GuardarDevolucionTemporal (Long idSolicitud,DevolucionDTO devolucionDTO){
 		super();
		this.idSolicitud=idSolicitud;
		this.devolucionDTO=devolucionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(devolucionDTO == null ? null : Entity.json(devolucionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setDevolucionDTO (DevolucionDTO devolucionDTO){
 		this.devolucionDTO=devolucionDTO;
 	}
 	
 	public DevolucionDTO getDevolucionDTO (){
 		return devolucionDTO;
 	}
  
}