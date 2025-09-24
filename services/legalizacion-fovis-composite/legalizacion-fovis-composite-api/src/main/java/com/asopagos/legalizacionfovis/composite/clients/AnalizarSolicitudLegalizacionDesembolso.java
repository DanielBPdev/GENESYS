package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.legalizacionfovis.composite.dto.AnalisisSolicitudLegalizacionDesembolsoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/analizarSolicitudLegalizacionDesembolso
 */
public class AnalizarSolicitudLegalizacionDesembolso extends ServiceClient { 
    	private AnalisisSolicitudLegalizacionDesembolsoDTO analisisSolicitud;
  
  
 	public AnalizarSolicitudLegalizacionDesembolso (AnalisisSolicitudLegalizacionDesembolsoDTO analisisSolicitud){
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
	

 
  
  	public void setAnalisisSolicitud (AnalisisSolicitudLegalizacionDesembolsoDTO analisisSolicitud){
 		this.analisisSolicitud=analisisSolicitud;
 	}
 	
 	public AnalisisSolicitudLegalizacionDesembolsoDTO getAnalisisSolicitud (){
 		return analisisSolicitud;
 	}
  
}