package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.AnalisisSolicitudPostulacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/analizarSolicitudPostulacion
 */
public class AnalizarSolicitudPostulacion extends ServiceClient { 
    	private AnalisisSolicitudPostulacionDTO analisisSolicitud;
  
  
 	public AnalizarSolicitudPostulacion (AnalisisSolicitudPostulacionDTO analisisSolicitud){
 		super();
		this.analisisSolicitud=analisisSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(analisisSolicitud == null ? null : Entity.json(analisisSolicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setAnalisisSolicitud (AnalisisSolicitudPostulacionDTO analisisSolicitud){
 		this.analisisSolicitud=analisisSolicitud;
 	}
 	
 	public AnalisisSolicitudPostulacionDTO getAnalisisSolicitud (){
 		return analisisSolicitud;
 	}
  
}