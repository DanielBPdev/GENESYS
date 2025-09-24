package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.dto.DatosNovedadRepresentanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesEspecialesComposite/ejecutarCambiosRepresentantes
 */
public class EjecutarCambiosRepresentantes extends ServiceClient { 
    	private DatosNovedadRepresentanteDTO datosNovedadRepresentanteDTO;
  
  
 	public EjecutarCambiosRepresentantes (DatosNovedadRepresentanteDTO datosNovedadRepresentanteDTO){
 		super();
		this.datosNovedadRepresentanteDTO=datosNovedadRepresentanteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosNovedadRepresentanteDTO == null ? null : Entity.json(datosNovedadRepresentanteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosNovedadRepresentanteDTO (DatosNovedadRepresentanteDTO datosNovedadRepresentanteDTO){
 		this.datosNovedadRepresentanteDTO=datosNovedadRepresentanteDTO;
 	}
 	
 	public DatosNovedadRepresentanteDTO getDatosNovedadRepresentanteDTO (){
 		return datosNovedadRepresentanteDTO;
 	}
  
}