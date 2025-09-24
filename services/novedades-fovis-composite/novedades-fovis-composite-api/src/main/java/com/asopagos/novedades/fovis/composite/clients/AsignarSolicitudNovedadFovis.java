package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.novedades.fovis.composite.dto.AsignarSolicitudNovedadFovisDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovisComposite/asignarSolicitudNovedadFovis
 */
public class AsignarSolicitudNovedadFovis extends ServiceClient { 
    	private AsignarSolicitudNovedadFovisDTO entrada;
  
  
 	public AsignarSolicitudNovedadFovis (AsignarSolicitudNovedadFovisDTO entrada){
 		super();
		this.entrada=entrada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entrada == null ? null : Entity.json(entrada));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEntrada (AsignarSolicitudNovedadFovisDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public AsignarSolicitudNovedadFovisDTO getEntrada (){
 		return entrada;
 	}
  
}