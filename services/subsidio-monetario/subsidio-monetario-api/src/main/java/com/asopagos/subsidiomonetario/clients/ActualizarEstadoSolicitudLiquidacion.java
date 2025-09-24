package com.asopagos.subsidiomonetario.clients;

import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/subsidioMonetario/solicitudLiquidacion/estado/{idSolicitudLiquidacion}
 */
public class ActualizarEstadoSolicitudLiquidacion extends ServiceClient { 
  	private Long idSolicitudLiquidacion;
    	private EstadoProcesoLiquidacionEnum estado;
  
  
 	public ActualizarEstadoSolicitudLiquidacion (Long idSolicitudLiquidacion,EstadoProcesoLiquidacionEnum estado){
 		super();
		this.idSolicitudLiquidacion=idSolicitudLiquidacion;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudLiquidacion", idSolicitudLiquidacion)
			.request(MediaType.APPLICATION_JSON)
			.put(estado == null ? null : Entity.json(estado));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitudLiquidacion (Long idSolicitudLiquidacion){
 		this.idSolicitudLiquidacion=idSolicitudLiquidacion;
 	}
 	
 	public Long getIdSolicitudLiquidacion (){
 		return idSolicitudLiquidacion;
 	}
  
  
  	public void setEstado (EstadoProcesoLiquidacionEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoProcesoLiquidacionEnum getEstado (){
 		return estado;
 	}
  
}