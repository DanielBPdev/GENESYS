package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.String;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionMontoLiquidadoFallecimientoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultar/dispersionFallecimiento/montoLiquidacion/{numeroRadicacion}
 */
public class ConsultarDispersionMontoLiquidacionFallecimiento extends ServiceClient {
 
  	private String numeroRadicacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DispersionMontoLiquidadoFallecimientoDTO result;
  
 	public ConsultarDispersionMontoLiquidacionFallecimiento (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DispersionMontoLiquidadoFallecimientoDTO) response.readEntity(DispersionMontoLiquidadoFallecimientoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DispersionMontoLiquidadoFallecimientoDTO getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
}