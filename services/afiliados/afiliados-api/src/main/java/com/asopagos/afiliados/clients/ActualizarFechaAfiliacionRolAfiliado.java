package com.asopagos.afiliados.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/rolAfiliado/{idRolAfiliado}/fecha
 */
public class ActualizarFechaAfiliacionRolAfiliado extends ServiceClient { 
  	private Long idRolAfiliado;
    	private Long fecha;
  
  
 	public ActualizarFechaAfiliacionRolAfiliado (Long idRolAfiliado,Long fecha){
 		super();
		this.idRolAfiliado=idRolAfiliado;
		this.fecha=fecha;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idRolAfiliado", idRolAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.put(fecha == null ? null : Entity.json(fecha));
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
  
  
  	public void setFecha (Long fecha){
 		this.fecha=fecha;
 	}
 	
 	public Long getFecha (){
 		return fecha;
 	}
  
}