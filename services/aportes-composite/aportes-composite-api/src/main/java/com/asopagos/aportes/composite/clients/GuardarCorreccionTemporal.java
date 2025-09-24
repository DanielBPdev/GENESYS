package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.aportes.composite.dto.CorreccionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/guardarCorreccionTemporal
 */
public class GuardarCorreccionTemporal extends ServiceClient { 
   	private Long idSolicitud;
   	private CorreccionDTO correccionDTO;
  
  
 	public GuardarCorreccionTemporal (Long idSolicitud,CorreccionDTO correccionDTO){
 		super();
		this.idSolicitud=idSolicitud;
		this.correccionDTO=correccionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(correccionDTO == null ? null : Entity.json(correccionDTO));
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
  
  	public void setCorreccionDTO (CorreccionDTO correccionDTO){
 		this.correccionDTO=correccionDTO;
 	}
 	
 	public CorreccionDTO getCorreccionDTO (){
 		return correccionDTO;
 	}
  
}