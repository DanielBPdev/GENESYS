package com.asopagos.empleadores.clients;

import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.entidades.ccf.personas.Persona;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/empleadores/{idEmpleador}/representantesLegales
 */
public class ActualizarRepresentanteLegalEmpleador extends ServiceClient { 
  	private Long idEmpleador;
   	private Boolean titular;
   	private Persona representante;
  
  
 	public ActualizarRepresentanteLegalEmpleador (Long idEmpleador,Boolean titular,Persona representante){
 		super();
		this.idEmpleador=idEmpleador;
		this.titular=titular;
		this.representante=representante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.queryParam("titular", titular)
			.request(MediaType.APPLICATION_JSON)
			.put(representante == null ? null : Entity.json(representante));
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
  
  	public void setTitular (Boolean titular){
 		this.titular=titular;
 	}
 	
 	public Boolean getTitular (){
 		return titular;
 	}
  
  	public void setRepresentante (Persona representante){
 		this.representante=representante;
 	}
 	
 	public Persona getRepresentante (){
 		return representante;
 	}
  
}