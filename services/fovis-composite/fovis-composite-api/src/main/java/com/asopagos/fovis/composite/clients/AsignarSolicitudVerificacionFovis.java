package com.asopagos.fovis.composite.clients;

import com.asopagos.fovis.composite.dto.AsignarSolicitudPostulacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/asignarSolicitudVerificacionFovis
 */
public class AsignarSolicitudVerificacionFovis extends ServiceClient { 
    	private AsignarSolicitudPostulacionDTO entrada;
  
  
 	public AsignarSolicitudVerificacionFovis (AsignarSolicitudPostulacionDTO entrada){
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
	

 
  
  	public void setEntrada (AsignarSolicitudPostulacionDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public AsignarSolicitudPostulacionDTO getEntrada (){
 		return entrada;
 	}
  
}