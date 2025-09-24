package com.asopagos.afiliaciones.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliaciones/{idSolicitudGlobal}/instanciaProceso
 */
public class GuardarInstanciaProceso extends ServiceClient { 
  	private Long idSolicitudGlobal;
    	private String idInstanciaProceso;
  
  
 	public GuardarInstanciaProceso (Long idSolicitudGlobal,String idInstanciaProceso){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.idInstanciaProceso=idInstanciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.put(idInstanciaProceso == null ? null : Entity.json(idInstanciaProceso));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  
  	public void setIdInstanciaProceso (String idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public String getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  
}