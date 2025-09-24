package com.asopagos.pila.clients;

import java.lang.Long;
import com.asopagos.pila.dto.ResultadoValidacionRegistrosAdicionCorrecionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarResultadoFase1SimuladaCorrecion
 */
public class ConsultarResultadoFase1SimuladaCorrecion extends ServiceClient {
 
  
  	private Long idPlanillaOriginal;
  	private Long idPlanillaCorrecion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionRegistrosAdicionCorrecionDTO result;
  
 	public ConsultarResultadoFase1SimuladaCorrecion (Long idPlanillaOriginal,Long idPlanillaCorrecion){
 		super();
		this.idPlanillaOriginal=idPlanillaOriginal;
		this.idPlanillaCorrecion=idPlanillaCorrecion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPlanillaOriginal", idPlanillaOriginal)
						.queryParam("idPlanillaCorrecion", idPlanillaCorrecion)
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

 
  	public void setIdPlanillaOriginal (Long idPlanillaOriginal){
 		this.idPlanillaOriginal=idPlanillaOriginal;
 	}
 	
 	public Long getIdPlanillaOriginal (){
 		return idPlanillaOriginal;
 	}
  	public void setIdPlanillaCorrecion (Long idPlanillaCorrecion){
 		this.idPlanillaCorrecion=idPlanillaCorrecion;
 	}
 	
 	public Long getIdPlanillaCorrecion (){
 		return idPlanillaCorrecion;
 	}
  
}