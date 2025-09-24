package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/radicarSolicitudNovedadCascada
 */
public class RadicarSolicitudNovedadCascada extends ServiceClient { 
    	private DatosNovedadCascadaDTO datosNovedadConsecutiva;
  
  
 	public RadicarSolicitudNovedadCascada (DatosNovedadCascadaDTO datosNovedadConsecutiva){
 		super();
		this.datosNovedadConsecutiva=datosNovedadConsecutiva;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosNovedadConsecutiva == null ? null : Entity.json(datosNovedadConsecutiva));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosNovedadConsecutiva (DatosNovedadCascadaDTO datosNovedadConsecutiva){
 		this.datosNovedadConsecutiva=datosNovedadConsecutiva;
 	}
 	
 	public DatosNovedadCascadaDTO getDatosNovedadConsecutiva (){
 		return datosNovedadConsecutiva;
 	}
  
}