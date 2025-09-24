package com.asopagos.personas.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/registrarCondicionesEspecialesFOVIS/{idPersona}
 */
public class RegistrarCondicionesEspecialesFOVIS extends ServiceClient { 
  	private Long idPersona;
    	private List<NombreCondicionEspecialEnum> condicionesEspeciales;
  
  
 	public RegistrarCondicionesEspecialesFOVIS (Long idPersona,List<NombreCondicionEspecialEnum> condicionesEspeciales){
 		super();
		this.idPersona=idPersona;
		this.condicionesEspeciales=condicionesEspeciales;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idPersona", idPersona)
			.request(MediaType.APPLICATION_JSON)
			.post(condicionesEspeciales == null ? null : Entity.json(condicionesEspeciales));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
  
  	public void setCondicionesEspeciales (List<NombreCondicionEspecialEnum> condicionesEspeciales){
 		this.condicionesEspeciales=condicionesEspeciales;
 	}
 	
 	public List<NombreCondicionEspecialEnum> getCondicionesEspeciales (){
 		return condicionesEspeciales;
 	}
  
}