package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/rolAfiliado/{idRolAfiliado}/estado
 */
public class ActualizarEstadoRolAfiliado extends ServiceClient { 
  	private Long idRolAfiliado;
    	private EstadoAfiliadoEnum estado;
  
  
 	public ActualizarEstadoRolAfiliado (Long idRolAfiliado,EstadoAfiliadoEnum estado){
 		super();
		this.idRolAfiliado=idRolAfiliado;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idRolAfiliado", idRolAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.put(estado == null ? null : Entity.json(estado));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdRolAfiliado (Long idRolAfiliado){
 		this.idRolAfiliado=idRolAfiliado;
 	}
 	
 	public Long getIdRolAfiliado (){
 		return idRolAfiliado;
 	}
  
  
  	public void setEstado (EstadoAfiliadoEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoAfiliadoEnum getEstado (){
 		return estado;
 	}
  
}