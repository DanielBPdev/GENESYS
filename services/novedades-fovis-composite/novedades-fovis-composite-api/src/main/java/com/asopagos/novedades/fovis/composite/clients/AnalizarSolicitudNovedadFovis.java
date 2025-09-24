package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.novedades.fovis.composite.dto.AnalisisSolicitudNovedadFovisDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovisComposite/analizarSolicitudNovedadFovis
 */
public class AnalizarSolicitudNovedadFovis extends ServiceClient { 
    	private AnalisisSolicitudNovedadFovisDTO analisisSolicitud;
  
  
 	public AnalizarSolicitudNovedadFovis (AnalisisSolicitudNovedadFovisDTO analisisSolicitud){
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
	

 
  
  	public void setAnalisisSolicitud (AnalisisSolicitudNovedadFovisDTO analisisSolicitud){
 		this.analisisSolicitud=analisisSolicitud;
 	}
 	
 	public AnalisisSolicitudNovedadFovisDTO getAnalisisSolicitud (){
 		return analisisSolicitud;
 	}
  
}