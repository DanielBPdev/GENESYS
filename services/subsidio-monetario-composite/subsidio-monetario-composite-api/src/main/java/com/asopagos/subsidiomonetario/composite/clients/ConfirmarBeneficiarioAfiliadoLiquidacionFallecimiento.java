package com.asopagos.subsidiomonetario.composite.clients;

import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/subsidioMonetarioComposite/liquidacionFallecimiento/resultados/confirmarBeneficiarioAfiliado/{numeroRadicacion}
 */
public class ConfirmarBeneficiarioAfiliadoLiquidacionFallecimiento extends ServiceClient { 
  	private String numeroRadicacion;
   	private Long identificadorCondicion;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoLiquidacionFallecimientoDTO result;
  
 	public ConfirmarBeneficiarioAfiliadoLiquidacionFallecimiento (String numeroRadicacion,Long identificadorCondicion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.identificadorCondicion=identificadorCondicion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("identificadorCondicion", identificadorCondicion)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (ResultadoLiquidacionFallecimientoDTO) response.readEntity(ResultadoLiquidacionFallecimientoDTO.class);
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
  
  	public void setIdentificadorCondicion (Long identificadorCondicion){
 		this.identificadorCondicion=identificadorCondicion;
 	}
 	
 	public Long getIdentificadorCondicion (){
 		return identificadorCondicion;
 	}
  
  
}