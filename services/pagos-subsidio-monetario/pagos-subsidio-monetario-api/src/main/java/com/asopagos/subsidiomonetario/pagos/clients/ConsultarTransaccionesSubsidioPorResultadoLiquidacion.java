package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.VistaRetirosSubsidioDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarTransaccionesSubsidioPorResultadoLiquidacion
 */
public class ConsultarTransaccionesSubsidioPorResultadoLiquidacion extends ServiceClient {
 
  
  	private Long idResultadoValidacionLiquidacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private VistaRetirosSubsidioDTO result;
  
 	public ConsultarTransaccionesSubsidioPorResultadoLiquidacion (Long idResultadoValidacionLiquidacion){
 		super();
		this.idResultadoValidacionLiquidacion=idResultadoValidacionLiquidacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idResultadoValidacionLiquidacion", idResultadoValidacionLiquidacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (VistaRetirosSubsidioDTO) response.readEntity(VistaRetirosSubsidioDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public VistaRetirosSubsidioDTO getResult() {
		return result;
	}

 
  	public void setIdResultadoValidacionLiquidacion (Long idResultadoValidacionLiquidacion){
 		this.idResultadoValidacionLiquidacion=idResultadoValidacionLiquidacion;
 	}
 	
 	public Long getIdResultadoValidacionLiquidacion (){
 		return idResultadoValidacionLiquidacion;
 	}
  
}