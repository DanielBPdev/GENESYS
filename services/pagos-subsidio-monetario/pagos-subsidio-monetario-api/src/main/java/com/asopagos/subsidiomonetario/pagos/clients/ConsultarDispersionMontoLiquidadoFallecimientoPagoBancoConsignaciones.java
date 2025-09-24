package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import java.lang.String;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultar/dispersionFallecimiento/detalleAdministrador/medioBancoConsignaciones/{numeroRadicacion}/{identificadorCondicion}
 */
public class ConsultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones extends ServiceClient {
 
  	private String numeroRadicacion;
  	private Long identificadorCondicion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DispersionResultadoMedioPagoFallecimientoDTO result;
  
 	public ConsultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones (String numeroRadicacion,Long identificadorCondicion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.identificadorCondicion=identificadorCondicion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
						.resolveTemplate("identificadorCondicion", identificadorCondicion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DispersionResultadoMedioPagoFallecimientoDTO) response.readEntity(DispersionResultadoMedioPagoFallecimientoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DispersionResultadoMedioPagoFallecimientoDTO getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setIdentificadorCondicion (Long identificadorCondicion){
 		this.identificadorCondicion=identificadorCondicion;
 	}
 	
 	public Long getIdentificadorCondicion (){
 		return identificadorCondicion;
 	}
  
  
}