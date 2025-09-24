package com.asopagos.subsidiomonetario.clients;

import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarResultadoLiquidacionFallecimientoConfirmados/{numeroSolicitud}
 */
public class ConsultarResultadoLiquidacionFallecimientoConfirmados extends ServiceClient {
 
  	private String numeroSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoLiquidacionFallecimientoDTO result;
  
 	public ConsultarResultadoLiquidacionFallecimientoConfirmados (String numeroSolicitud){
 		super();
		this.numeroSolicitud=numeroSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroSolicitud", numeroSolicitud)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ResultadoLiquidacionFallecimientoDTO) response.readEntity(ResultadoLiquidacionFallecimientoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResultadoLiquidacionFallecimientoDTO getResult() {
		return result;
	}

 	public void setNumeroSolicitud (String numeroSolicitud){
 		this.numeroSolicitud=numeroSolicitud;
 	}
 	
 	public String getNumeroSolicitud (){
 		return numeroSolicitud;
 	}
  
  
}