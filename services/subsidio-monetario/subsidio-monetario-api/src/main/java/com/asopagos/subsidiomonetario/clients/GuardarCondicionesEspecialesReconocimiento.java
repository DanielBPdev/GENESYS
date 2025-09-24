package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.dto.CondicionesEspecialesLiquidacionEspecificaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/guardarCondicionesEspecialesReconocimiento
 */
public class GuardarCondicionesEspecialesReconocimiento extends ServiceClient { 
   	private Long idSolicitudLiquidacion;
   	private CondicionesEspecialesLiquidacionEspecificaDTO condiciones;
  
  
 	public GuardarCondicionesEspecialesReconocimiento (Long idSolicitudLiquidacion,CondicionesEspecialesLiquidacionEspecificaDTO condiciones){
 		super();
		this.idSolicitudLiquidacion=idSolicitudLiquidacion;
		this.condiciones=condiciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudLiquidacion", idSolicitudLiquidacion)
			.request(MediaType.APPLICATION_JSON)
			.post(condiciones == null ? null : Entity.json(condiciones));
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
  
  	public void setCondiciones (CondicionesEspecialesLiquidacionEspecificaDTO condiciones){
 		this.condiciones=condiciones;
 	}
 	
 	public CondicionesEspecialesLiquidacionEspecificaDTO getCondiciones (){
 		return condiciones;
 	}
  
}