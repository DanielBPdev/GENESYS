package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaGenericaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/guardarLiquidacionEspecifica
 */
public class GuardarLiquidacionEspecifica extends ServiceClient { 
    	private LiquidacionEspecificaDTO liquidacionEspecifica;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaGenericaDTO result;
  
 	public GuardarLiquidacionEspecifica (LiquidacionEspecificaDTO liquidacionEspecifica){
 		super();
		this.liquidacionEspecifica=liquidacionEspecifica;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(liquidacionEspecifica == null ? null : Entity.json(liquidacionEspecifica));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (RespuestaGenericaDTO) response.readEntity(RespuestaGenericaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RespuestaGenericaDTO getResult() {
		return result;
	}

 
  
  	public void setLiquidacionEspecifica (LiquidacionEspecificaDTO liquidacionEspecifica){
 		this.liquidacionEspecifica=liquidacionEspecifica;
 	}
 	
 	public LiquidacionEspecificaDTO getLiquidacionEspecifica (){
 		return liquidacionEspecifica;
 	}
  
}