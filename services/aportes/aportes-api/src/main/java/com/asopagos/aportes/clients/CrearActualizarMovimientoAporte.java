package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearActualizarMovimientoAporte
 */
public class CrearActualizarMovimientoAporte extends ServiceClient { 
    	private MovimientoAporteModeloDTO movimientoAporteModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearActualizarMovimientoAporte (MovimientoAporteModeloDTO movimientoAporteModeloDTO){
 		super();
		this.movimientoAporteModeloDTO=movimientoAporteModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(movimientoAporteModeloDTO == null ? null : Entity.json(movimientoAporteModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setMovimientoAporteModeloDTO (MovimientoAporteModeloDTO movimientoAporteModeloDTO){
 		this.movimientoAporteModeloDTO=movimientoAporteModeloDTO;
 	}
 	
 	public MovimientoAporteModeloDTO getMovimientoAporteModeloDTO (){
 		return movimientoAporteModeloDTO;
 	}
  
}