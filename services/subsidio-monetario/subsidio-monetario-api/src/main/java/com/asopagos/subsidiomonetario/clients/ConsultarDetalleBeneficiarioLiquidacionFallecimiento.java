package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import java.lang.String;
import com.asopagos.dto.subsidiomonetario.liquidacion.DetalleLiquidacionBeneficiarioFallecimientoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/liquidacionFallecimiento/consultar/detalleBeneficiario/{numeroRadicacion}
 */
public class ConsultarDetalleBeneficiarioLiquidacionFallecimiento extends ServiceClient {
 
  	private String numeroRadicacion;
  
  	private Long idCondicionBeneficiario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DetalleLiquidacionBeneficiarioFallecimientoDTO result;
  
 	public ConsultarDetalleBeneficiarioLiquidacionFallecimiento (String numeroRadicacion,Long idCondicionBeneficiario){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idCondicionBeneficiario=idCondicionBeneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.queryParam("idCondicionBeneficiario", idCondicionBeneficiario)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DetalleLiquidacionBeneficiarioFallecimientoDTO) response.readEntity(DetalleLiquidacionBeneficiarioFallecimientoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DetalleLiquidacionBeneficiarioFallecimientoDTO getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setIdCondicionBeneficiario (Long idCondicionBeneficiario){
 		this.idCondicionBeneficiario=idCondicionBeneficiario;
 	}
 	
 	public Long getIdCondicionBeneficiario (){
 		return idCondicionBeneficiario;
 	}
  
}