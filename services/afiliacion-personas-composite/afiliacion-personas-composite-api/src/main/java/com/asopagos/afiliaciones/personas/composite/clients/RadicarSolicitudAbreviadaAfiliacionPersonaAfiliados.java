
package com.asopagos.afiliaciones.personas.composite.clients;
import com.asopagos.dto.RadicarSolicitudAbreviadaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/radicarSolicitudAbreviadaAfiliacionPersonaAfiliados
 */
public class RadicarSolicitudAbreviadaAfiliacionPersonaAfiliados extends ServiceClient { 
    	private RadicarSolicitudAbreviadaDTO radicarSolicitudAbreviada;
  
 	public RadicarSolicitudAbreviadaAfiliacionPersonaAfiliados (RadicarSolicitudAbreviadaDTO datosActivacion){
 		super();
		this.radicarSolicitudAbreviada=datosActivacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(radicarSolicitudAbreviada == null ? null : Entity.json(radicarSolicitudAbreviada));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRadicarSolicitudAbreviada (RadicarSolicitudAbreviadaDTO radicarSolicitudAbreviada){
 		this.radicarSolicitudAbreviada=radicarSolicitudAbreviada;
 	}
 	
 	public RadicarSolicitudAbreviadaDTO getRadicarSolicitudAbreviada (){
 		return radicarSolicitudAbreviada;
 	}
  
}