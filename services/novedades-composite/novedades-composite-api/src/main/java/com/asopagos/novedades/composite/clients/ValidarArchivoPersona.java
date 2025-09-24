package com.asopagos.novedades.composite.clients;

import com.asopagos.dto.CargueArchivoActualizacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/validarArchivoPersona
 */
public class ValidarArchivoPersona extends ServiceClient { 
    	
    private CargueArchivoActualizacionDTO cargue;
  
  
 	public ValidarArchivoPersona (CargueArchivoActualizacionDTO cargue){
 		super();
		this.cargue=cargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cargue == null ? null : Entity.json(cargue));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setCargue (CargueArchivoActualizacionDTO cargue){
 		this.cargue=cargue;
 	}
 	
 	public CargueArchivoActualizacionDTO getCargue (){
 		return cargue;
 	}
  
}