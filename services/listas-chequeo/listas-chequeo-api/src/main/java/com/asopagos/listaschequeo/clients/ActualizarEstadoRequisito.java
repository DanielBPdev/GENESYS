package com.asopagos.listaschequeo.clients;

import java.lang.Short;
import com.asopagos.enumeraciones.core.HabilitadoInhabilitadoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/requisitos/{idRequisito}/estado/{estado}
 */
public class ActualizarEstadoRequisito extends ServiceClient { 
  	private Short idRequisito;
  	private HabilitadoInhabilitadoEnum estado;
    
  
 	public ActualizarEstadoRequisito (Short idRequisito,HabilitadoInhabilitadoEnum estado){
 		super();
		this.idRequisito=idRequisito;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idRequisito", idRequisito)
			.resolveTemplate("estado", estado)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdRequisito (Short idRequisito){
 		this.idRequisito=idRequisito;
 	}
 	
 	public Short getIdRequisito (){
 		return idRequisito;
 	}
  	public void setEstado (HabilitadoInhabilitadoEnum estado){
 		this.estado=estado;
 	}
 	
 	public HabilitadoInhabilitadoEnum getEstado (){
 		return estado;
 	}
  
  
  
}