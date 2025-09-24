package com.asopagos.aportes.clients;

import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/{estado}/{numeroRadicacion}/consultarSolicitudCierreRecaudo
 */
public class ActualizarEstadoSolicitudCierre extends ServiceClient { 
  	private EstadoSolicitudCierreRecaudoEnum estado;
  	private String numeroRadicacion;
    
  
 	public ActualizarEstadoSolicitudCierre (EstadoSolicitudCierreRecaudoEnum estado,String numeroRadicacion){
 		super();
		this.estado=estado;
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("estado", estado)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setEstado (EstadoSolicitudCierreRecaudoEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoSolicitudCierreRecaudoEnum getEstado (){
 		return estado;
 	}
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
  
}