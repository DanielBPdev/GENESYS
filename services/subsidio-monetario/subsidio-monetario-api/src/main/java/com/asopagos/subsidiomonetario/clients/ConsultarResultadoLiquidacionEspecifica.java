package com.asopagos.subsidiomonetario.clients;

import java.lang.String;
import com.asopagos.subsidiomonetario.dto.ResultadoLiquidacionMasivaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarResultadoLiquidacionEspecifica/{numeroSolicitud}
 */
public class ConsultarResultadoLiquidacionEspecifica extends ServiceClient {
 
  	private String numeroSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoLiquidacionMasivaDTO result;
  
 	public ConsultarResultadoLiquidacionEspecifica (String numeroSolicitud){
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
		this.result = (ResultadoLiquidacionMasivaDTO) response.readEntity(ResultadoLiquidacionMasivaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResultadoLiquidacionMasivaDTO getResult() {
		return result;
	}

 	public void setNumeroSolicitud (String numeroSolicitud){
 		this.numeroSolicitud=numeroSolicitud;
 	}
 	
 	public String getNumeroSolicitud (){
 		return numeroSolicitud;
 	}
  
  
}