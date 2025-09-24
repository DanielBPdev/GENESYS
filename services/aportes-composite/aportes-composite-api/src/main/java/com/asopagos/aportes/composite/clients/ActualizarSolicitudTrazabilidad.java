package com.asopagos.aportes.composite.clients;

import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/{idSolicitud}/actualizarSolicitudTrazabilidad
 */
public class ActualizarSolicitudTrazabilidad extends ServiceClient { 
  	private Long idSolicitud;
   	private ProcesoEnum proceso;
  	private EstadoSolicitudAporteEnum estadoSolicitud;
  	private Long idComunicado;
   
  
 	public ActualizarSolicitudTrazabilidad (Long idSolicitud,ProcesoEnum proceso,EstadoSolicitudAporteEnum estadoSolicitud,Long idComunicado){
 		super();
		this.idSolicitud=idSolicitud;
		this.proceso=proceso;
		this.estadoSolicitud=estadoSolicitud;
		this.idComunicado=idComunicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.queryParam("proceso", proceso)
			.queryParam("estadoSolicitud", estadoSolicitud)
			.queryParam("idComunicado", idComunicado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  	public void setEstadoSolicitud (EstadoSolicitudAporteEnum estadoSolicitud){
 		this.estadoSolicitud=estadoSolicitud;
 	}
 	
 	public EstadoSolicitudAporteEnum getEstadoSolicitud (){
 		return estadoSolicitud;
 	}
  	public void setIdComunicado (Long idComunicado){
 		this.idComunicado=idComunicado;
 	}
 	
 	public Long getIdComunicado (){
 		return idComunicado;
 	}
  
  
}