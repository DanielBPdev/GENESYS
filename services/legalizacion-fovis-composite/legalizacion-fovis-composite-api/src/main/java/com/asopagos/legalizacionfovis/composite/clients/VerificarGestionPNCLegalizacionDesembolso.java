package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.legalizacionfovis.composite.dto.VerificacionGestionPNCLegalizacionDesembolsoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/verificarGestionPNCLegalizacionDesembolso
 */
public class VerificarGestionPNCLegalizacionDesembolso extends ServiceClient { 
    	private VerificacionGestionPNCLegalizacionDesembolsoDTO datosVerificacion;
  
  
 	public VerificarGestionPNCLegalizacionDesembolso (VerificacionGestionPNCLegalizacionDesembolsoDTO datosVerificacion){
 		super();
		this.datosVerificacion=datosVerificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosVerificacion == null ? null : Entity.json(datosVerificacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosVerificacion (VerificacionGestionPNCLegalizacionDesembolsoDTO datosVerificacion){
 		this.datosVerificacion=datosVerificacion;
 	}
 	
 	public VerificacionGestionPNCLegalizacionDesembolsoDTO getDatosVerificacion (){
 		return datosVerificacion;
 	}
  
}