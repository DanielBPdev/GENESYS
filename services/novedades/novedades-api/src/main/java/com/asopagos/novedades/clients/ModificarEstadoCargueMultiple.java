package com.asopagos.novedades.clients;

import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/novedadesCargueMultiple/modificarEstadoCargueMultiple/{identificador}
 */
public class ModificarEstadoCargueMultiple extends ServiceClient { 
  	private Long identificador;
   	private Boolean empleadorCargue;
   	private EstadoCargaMultipleEnum estadoCargueMultiple;
  
  
 	public ModificarEstadoCargueMultiple (Long identificador,Boolean empleadorCargue,EstadoCargaMultipleEnum estadoCargueMultiple){
 		super();
		this.identificador=identificador;
		this.empleadorCargue=empleadorCargue;
		this.estadoCargueMultiple=estadoCargueMultiple;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("identificador", identificador)
			.queryParam("empleadorCargue", empleadorCargue)
			.request(MediaType.APPLICATION_JSON)
			.put(estadoCargueMultiple == null ? null : Entity.json(estadoCargueMultiple));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdentificador (Long identificador){
 		this.identificador=identificador;
 	}
 	
 	public Long getIdentificador (){
 		return identificador;
 	}
  
  	public void setEmpleadorCargue (Boolean empleadorCargue){
 		this.empleadorCargue=empleadorCargue;
 	}
 	
 	public Boolean getEmpleadorCargue (){
 		return empleadorCargue;
 	}
  
  	public void setEstadoCargueMultiple (EstadoCargaMultipleEnum estadoCargueMultiple){
 		this.estadoCargueMultiple=estadoCargueMultiple;
 	}
 	
 	public EstadoCargaMultipleEnum getEstadoCargueMultiple (){
 		return estadoCargueMultiple;
 	}
  
}