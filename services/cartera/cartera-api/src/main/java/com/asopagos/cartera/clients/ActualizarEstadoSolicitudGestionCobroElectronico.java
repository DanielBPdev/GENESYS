package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.EstadoSolicitudGestionCobroEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{numeroRadicacion}/actualizarEstadoSolicitudGestionCobroElectronico
 */
public class ActualizarEstadoSolicitudGestionCobroElectronico extends ServiceClient {
 
  	private String numeroRadicacion;
  
  	private EstadoSolicitudGestionCobroEnum estadoSolicitudGestionCobro;
  
  
 	public ActualizarEstadoSolicitudGestionCobroElectronico (String numeroRadicacion,EstadoSolicitudGestionCobroEnum estadoSolicitudGestionCobro){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.estadoSolicitudGestionCobro=estadoSolicitudGestionCobro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.queryParam("estadoSolicitudGestionCobro", estadoSolicitudGestionCobro)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setEstadoSolicitudGestionCobro (EstadoSolicitudGestionCobroEnum estadoSolicitudGestionCobro){
 		this.estadoSolicitudGestionCobro=estadoSolicitudGestionCobro;
 	}
 	
 	public EstadoSolicitudGestionCobroEnum getEstadoSolicitudGestionCobro (){
 		return estadoSolicitudGestionCobro;
 	}
  
}