package com.asopagos.clienteanibol.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.clienteanibol.dto.ResultadoDispersionAnibolDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/anibol/consultaEstadoProcesamiento
 */
public class ConsultarEstadoProcesamientoV2 extends ServiceClient {
 
  
  	private String idProceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoDispersionAnibolDTO result;
  
 	public ConsultarEstadoProcesamientoV2(String idProceso){
 		super();
		this.idProceso=idProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idProceso", idProceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ResultadoDispersionAnibolDTO) response.readEntity(new GenericType<ResultadoDispersionAnibolDTO>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResultadoDispersionAnibolDTO getResult() {
		return result;
	}

 
  	public void setIdProceso (String idProceso){
 		this.idProceso=idProceso;
 	}
 	
 	public String getIdProceso (){
 		return idProceso;
 	}
  
}