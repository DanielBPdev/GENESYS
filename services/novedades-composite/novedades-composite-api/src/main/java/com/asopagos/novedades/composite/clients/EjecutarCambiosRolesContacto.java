package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.dto.DatosNovedadRolContactoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesEspecialesComposite/ejecutarCambiosRolesContacto
 */
public class EjecutarCambiosRolesContacto extends ServiceClient { 
    	private DatosNovedadRolContactoDTO datosNovedadRolContactoDTO;
  
  
 	public EjecutarCambiosRolesContacto (DatosNovedadRolContactoDTO datosNovedadRolContactoDTO){
 		super();
		this.datosNovedadRolContactoDTO=datosNovedadRolContactoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosNovedadRolContactoDTO == null ? null : Entity.json(datosNovedadRolContactoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosNovedadRolContactoDTO (DatosNovedadRolContactoDTO datosNovedadRolContactoDTO){
 		this.datosNovedadRolContactoDTO=datosNovedadRolContactoDTO;
 	}
 	
 	public DatosNovedadRolContactoDTO getDatosNovedadRolContactoDTO (){
 		return datosNovedadRolContactoDTO;
 	}
  
}