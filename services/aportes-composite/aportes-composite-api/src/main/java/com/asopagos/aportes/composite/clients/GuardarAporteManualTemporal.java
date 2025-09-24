package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.aportes.composite.dto.AporteManualDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/guardarAporteManualTemporal
 */
public class GuardarAporteManualTemporal extends ServiceClient { 
   	private Long idSolicitud;
   	private AporteManualDTO aporteManualDTO;
  
  
 	public GuardarAporteManualTemporal (Long idSolicitud,AporteManualDTO aporteManualDTO){
 		super();
		this.idSolicitud=idSolicitud;
		this.aporteManualDTO=aporteManualDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(aporteManualDTO == null ? null : Entity.json(aporteManualDTO));
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
  
  	public void setAporteManualDTO (AporteManualDTO aporteManualDTO){
 		this.aporteManualDTO=aporteManualDTO;
 	}
 	
 	public AporteManualDTO getAporteManualDTO (){
 		return aporteManualDTO;
 	}
  
}