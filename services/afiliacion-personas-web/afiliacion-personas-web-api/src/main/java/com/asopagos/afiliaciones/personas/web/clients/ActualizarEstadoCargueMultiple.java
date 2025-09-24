package com.asopagos.afiliaciones.personas.web.clients;

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
 * /rest/afiliacionesPersonasWebMultiple/actualizarEstadoCargueMultiple/{identificador}
 */
public class ActualizarEstadoCargueMultiple extends ServiceClient { 
  	private Long identificador;
   	private Boolean empleadorCargue;
   	private EstadoCargaMultipleEnum estadoCargueMultiple;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ActualizarEstadoCargueMultiple (Long identificador,Boolean empleadorCargue,EstadoCargaMultipleEnum estadoCargueMultiple){
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
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Long getResult() {
		return result;
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