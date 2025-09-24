package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.composite.dto.EmpleadorAfiliadosDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesEspecialesComposite/ejecutarSustitucionTrabajadores
 */
public class EjecutarSustitucionTrabajadores extends ServiceClient { 
    	private EmpleadorAfiliadosDTO sustitucion;
  
  
 	public EjecutarSustitucionTrabajadores (EmpleadorAfiliadosDTO sustitucion){
 		super();
		this.sustitucion=sustitucion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(sustitucion == null ? null : Entity.json(sustitucion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setSustitucion (EmpleadorAfiliadosDTO sustitucion){
 		this.sustitucion=sustitucion;
 	}
 	
 	public EmpleadorAfiliadosDTO getSustitucion (){
 		return sustitucion;
 	}
  
}