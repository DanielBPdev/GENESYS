package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.legalizacionfovis.composite.dto.AsignarSolicitudLegalizacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/asignarSolicitudLegalizacion
 */
public class AsignarSolicitudLegalizacion extends ServiceClient { 
    	private AsignarSolicitudLegalizacionDTO entrada;
  
  
 	public AsignarSolicitudLegalizacion (AsignarSolicitudLegalizacionDTO entrada){
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
	

 
  
  	public void setEntrada (AsignarSolicitudLegalizacionDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public AsignarSolicitudLegalizacionDTO getEntrada (){
 		return entrada;
 	}
  
}