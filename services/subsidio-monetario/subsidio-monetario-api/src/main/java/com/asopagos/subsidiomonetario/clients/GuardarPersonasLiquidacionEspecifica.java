package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/guardarPersonasLiquidacionEspecifica
 */
public class GuardarPersonasLiquidacionEspecifica extends ServiceClient { 
   	private Long idSolicitudLiquidacion;
   	private LiquidacionEspecificaDTO liquidacionEspecifica;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public GuardarPersonasLiquidacionEspecifica (Long idSolicitudLiquidacion,LiquidacionEspecificaDTO liquidacionEspecifica){
 		super();
		this.idSolicitudLiquidacion=idSolicitudLiquidacion;
		this.liquidacionEspecifica=liquidacionEspecifica;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudLiquidacion", idSolicitudLiquidacion)
			.request(MediaType.APPLICATION_JSON)
			.post(liquidacionEspecifica == null ? null : Entity.json(liquidacionEspecifica));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  	public void setIdSolicitudLiquidacion (Long idSolicitudLiquidacion){
 		this.idSolicitudLiquidacion=idSolicitudLiquidacion;
 	}
 	
 	public Long getIdSolicitudLiquidacion (){
 		return idSolicitudLiquidacion;
 	}
  
  	public void setLiquidacionEspecifica (LiquidacionEspecificaDTO liquidacionEspecifica){
 		this.liquidacionEspecifica=liquidacionEspecifica;
 	}
 	
 	public LiquidacionEspecificaDTO getLiquidacionEspecifica (){
 		return liquidacionEspecifica;
 	}
  
}