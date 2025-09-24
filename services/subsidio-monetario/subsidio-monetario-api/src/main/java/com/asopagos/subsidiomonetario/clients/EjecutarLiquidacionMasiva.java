package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.dto.RespuestaGenericaDTO;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/ejecutarLiquidacionMasiva
 */
public class EjecutarLiquidacionMasiva extends ServiceClient { 
   	private Long periodo;
   	private SolicitudLiquidacionSubsidioModeloDTO liquidacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaGenericaDTO result;
  
 	public EjecutarLiquidacionMasiva (Long periodo,SolicitudLiquidacionSubsidioModeloDTO liquidacion){
 		super();
		this.periodo=periodo;
		this.liquidacion=liquidacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("periodo", periodo)
			.request(MediaType.APPLICATION_JSON)
			.post(liquidacion == null ? null : Entity.json(liquidacion));
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

 
  	public void setPeriodo (Long periodo){
 		this.periodo=periodo;
 	}
 	
 	public Long getPeriodo (){
 		return periodo;
 	}
  
  	public void setLiquidacion (SolicitudLiquidacionSubsidioModeloDTO liquidacion){
 		this.liquidacion=liquidacion;
 	}
 	
 	public SolicitudLiquidacionSubsidioModeloDTO getLiquidacion (){
 		return liquidacion;
 	}
  
}