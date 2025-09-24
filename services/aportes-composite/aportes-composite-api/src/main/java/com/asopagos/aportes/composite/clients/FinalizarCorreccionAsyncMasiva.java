package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.InformacionSolicitudDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/FinalizarCorreccionAsyncMasiva
 */
public class FinalizarCorreccionAsyncMasiva extends ServiceClient { 
    	private InformacionSolicitudDTO infoSolicitud;
  
  
 	public FinalizarCorreccionAsyncMasiva (InformacionSolicitudDTO infoSolicitud){
 		super();
		this.infoSolicitud=infoSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(infoSolicitud == null ? null : Entity.json(infoSolicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInfoSolicitud (InformacionSolicitudDTO infoSolicitud){
 		this.infoSolicitud=infoSolicitud;
 	}
 	
 	public InformacionSolicitudDTO getInfoSolicitud (){
 		return infoSolicitud;
 	}
  
}