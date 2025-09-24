package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/actualizarEstadoSolicitudAnibol/{idRegistroSolicitudAnibol}/{estadoSolicitudAnibol}
 */
public class ActualizarEstadoSolicitudAnibol extends ServiceClient { 
  	private EstadoSolicitudAnibolEnum estadoSolicitudAnibol;
  	private Long idRegistroSolicitudAnibol;
    
  
 	public ActualizarEstadoSolicitudAnibol (EstadoSolicitudAnibolEnum estadoSolicitudAnibol,Long idRegistroSolicitudAnibol){
 		super();
		this.estadoSolicitudAnibol=estadoSolicitudAnibol;
		this.idRegistroSolicitudAnibol=idRegistroSolicitudAnibol;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("estadoSolicitudAnibol", estadoSolicitudAnibol)
			.resolveTemplate("idRegistroSolicitudAnibol", idRegistroSolicitudAnibol)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setEstadoSolicitudAnibol (EstadoSolicitudAnibolEnum estadoSolicitudAnibol){
 		this.estadoSolicitudAnibol=estadoSolicitudAnibol;
 	}
 	
 	public EstadoSolicitudAnibolEnum getEstadoSolicitudAnibol (){
 		return estadoSolicitudAnibol;
 	}
  	public void setIdRegistroSolicitudAnibol (Long idRegistroSolicitudAnibol){
 		this.idRegistroSolicitudAnibol=idRegistroSolicitudAnibol;
 	}
 	
 	public Long getIdRegistroSolicitudAnibol (){
 		return idRegistroSolicitudAnibol;
 	}
  
  
  
}