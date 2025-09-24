package com.asopagos.clienteanibol.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.clienteanibol.dto.ResultadoProcesamientoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/anibol/consultaEstadoProcesamientoAnulacion
 */
public class ConsultarEstadoProcesamientoAnulacion extends ServiceClient {
 
  
  	private String idProceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoProcesamientoDTO> result;
  
 	public ConsultarEstadoProcesamientoAnulacion (String idProceso){
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
		this.result = (List<ResultadoProcesamientoDTO>) response.readEntity(new GenericType<List<ResultadoProcesamientoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ResultadoProcesamientoDTO> getResult() {
		return result;
	}

 
  	public void setIdProceso (String idProceso){
 		this.idProceso=idProceso;
 	}
 	
 	public String getIdProceso (){
 		return idProceso;
 	}
  
}