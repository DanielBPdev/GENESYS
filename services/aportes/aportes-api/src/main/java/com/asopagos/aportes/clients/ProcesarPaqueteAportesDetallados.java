package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.JuegoAporteMovimientoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/procesarPaqueteAportesDetallados
 */
public class ProcesarPaqueteAportesDetallados extends ServiceClient { 
    	private List<JuegoAporteMovimientoDTO> aportesDetallados;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public ProcesarPaqueteAportesDetallados (List<JuegoAporteMovimientoDTO> aportesDetallados){
 		super();
		this.aportesDetallados=aportesDetallados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aportesDetallados == null ? null : Entity.json(aportesDetallados));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 
  
  	public void setAportesDetallados (List<JuegoAporteMovimientoDTO> aportesDetallados){
 		this.aportesDetallados=aportesDetallados;
 	}
 	
 	public List<JuegoAporteMovimientoDTO> getAportesDetallados (){
 		return aportesDetallados;
 	}
  
}