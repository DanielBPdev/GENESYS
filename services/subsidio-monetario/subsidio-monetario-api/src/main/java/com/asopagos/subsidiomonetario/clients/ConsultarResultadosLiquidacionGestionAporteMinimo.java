package com.asopagos.subsidiomonetario.clients;

import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarResultadosLiquidacionGestionAporteMinimo
 */
public class ConsultarResultadosLiquidacionGestionAporteMinimo extends ServiceClient {
 
  
  	private String numeroRadicacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoLiquidacionFallecimientoDTO result;
  
 	public ConsultarResultadosLiquidacionGestionAporteMinimo (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicacion", numeroRadicacion)
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

 
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
}