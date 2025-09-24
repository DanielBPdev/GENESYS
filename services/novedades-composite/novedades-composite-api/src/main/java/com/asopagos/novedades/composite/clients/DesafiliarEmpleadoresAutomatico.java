package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesEspecialesComposite/desafiliarEmpleadoresAutomatico
 */
public class DesafiliarEmpleadoresAutomatico extends ServiceClient { 
    	private DatosNovedadAutomaticaDTO datosEmpleadorNovedad;
  
  
 	public DesafiliarEmpleadoresAutomatico (DatosNovedadAutomaticaDTO datosEmpleadorNovedad){
 		super();
		this.datosEmpleadorNovedad=datosEmpleadorNovedad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosEmpleadorNovedad == null ? null : Entity.json(datosEmpleadorNovedad));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosEmpleadorNovedad (DatosNovedadAutomaticaDTO datosEmpleadorNovedad){
 		this.datosEmpleadorNovedad=datosEmpleadorNovedad;
 	}
 	
 	public DatosNovedadAutomaticaDTO getDatosEmpleadorNovedad (){
 		return datosEmpleadorNovedad;
 	}
  
}