package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/cerrarSolicitudLegalizacionDesembolso
 */
public class CerrarSolicitudLegalizacionDesembolso extends ServiceClient { 
    	private SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso;
  
  
 	public CerrarSolicitudLegalizacionDesembolso (SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso){
 		super();
		this.solicitudLegalizacionDesembolso=solicitudLegalizacionDesembolso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudLegalizacionDesembolso == null ? null : Entity.json(solicitudLegalizacionDesembolso));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSolicitudLegalizacionDesembolso (SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso){
 		this.solicitudLegalizacionDesembolso=solicitudLegalizacionDesembolso;
 	}
 	
 	public SolicitudLegalizacionDesembolsoDTO getSolicitudLegalizacionDesembolso (){
 		return solicitudLegalizacionDesembolso;
 	}
  
}