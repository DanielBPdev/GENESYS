package com.asopagos.pila.clients;

import java.lang.Long;
import com.asopagos.pila.dto.ResultadoValidacionRegistrosAdicionCorrecionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarResultadoProcesamientoPlanillaAdicion
 */
public class ConsultarResultadoProcesamientoPlanillaAdicion extends ServiceClient {
 
  
  	private Long idIndicePlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionRegistrosAdicionCorrecionDTO result;
  
 	public ConsultarResultadoProcesamientoPlanillaAdicion (Long idIndicePlanilla){
 		super();
		this.idIndicePlanilla=idIndicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idIndicePlanilla", idIndicePlanilla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ResultadoValidacionRegistrosAdicionCorrecionDTO) response.readEntity(ResultadoValidacionRegistrosAdicionCorrecionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResultadoValidacionRegistrosAdicionCorrecionDTO getResult() {
		return result;
	}

 
  	public void setIdIndicePlanilla (Long idIndicePlanilla){
 		this.idIndicePlanilla=idIndicePlanilla;
 	}
 	
 	public Long getIdIndicePlanilla (){
 		return idIndicePlanilla;
 	}
  
}