package com.asopagos.empleadores.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.personas.SocioEmpleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/empleadores/{idEmpleador}/sociosEmpleador/{idSocioEmpleador}
 */
public class ActualizarSocioEmpleador extends ServiceClient { 
  	private Long idEmpleador;
  	private Long idSocioEmpleador;
    	private SocioEmpleador socioEmpleador;
  
  
 	public ActualizarSocioEmpleador (Long idEmpleador,Long idSocioEmpleador,SocioEmpleador socioEmpleador){
 		super();
		this.idEmpleador=idEmpleador;
		this.idSocioEmpleador=idSocioEmpleador;
		this.socioEmpleador=socioEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.resolveTemplate("idSocioEmpleador", idSocioEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.put(socioEmpleador == null ? null : Entity.json(socioEmpleador));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  	public void setIdSocioEmpleador (Long idSocioEmpleador){
 		this.idSocioEmpleador=idSocioEmpleador;
 	}
 	
 	public Long getIdSocioEmpleador (){
 		return idSocioEmpleador;
 	}
  
  
  	public void setSocioEmpleador (SocioEmpleador socioEmpleador){
 		this.socioEmpleador=socioEmpleador;
 	}
 	
 	public SocioEmpleador getSocioEmpleador (){
 		return socioEmpleador;
 	}
  
}