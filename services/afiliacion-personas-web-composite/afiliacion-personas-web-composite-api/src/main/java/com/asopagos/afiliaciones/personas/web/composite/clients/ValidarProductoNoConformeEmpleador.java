package com.asopagos.afiliaciones.personas.web.composite.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/validarProduectoNoConformeEmpleador/{idEmpleador}
 */
public class ValidarProductoNoConformeEmpleador extends ServiceClient { 
  	private Long idEmpleador;
    	private Long idIstanciaProceso;
  
  
 	public ValidarProductoNoConformeEmpleador (Long idEmpleador,Long idIstanciaProceso){
 		super();
		this.idEmpleador=idEmpleador;
		this.idIstanciaProceso=idIstanciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(idIstanciaProceso == null ? null : Entity.json(idIstanciaProceso));
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
  
  
  	public void setIdIstanciaProceso (Long idIstanciaProceso){
 		this.idIstanciaProceso=idIstanciaProceso;
 	}
 	
 	public Long getIdIstanciaProceso (){
 		return idIstanciaProceso;
 	}
  
}