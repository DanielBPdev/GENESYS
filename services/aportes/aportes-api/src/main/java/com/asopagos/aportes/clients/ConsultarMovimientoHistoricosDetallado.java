package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.MovimientoIngresosDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDetalladoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/obtenerMovimientoHistoricosDetallado
 */
public class ConsultarMovimientoHistoricosDetallado extends ServiceClient { 
    	private MovimientoIngresosDTO movimientoIngresosDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<MovimientoIngresosDetalladoDTO> result;
  
 	public ConsultarMovimientoHistoricosDetallado (MovimientoIngresosDTO movimientoIngresosDTO){
 		super();
		this.movimientoIngresosDTO=movimientoIngresosDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(movimientoIngresosDTO == null ? null : Entity.json(movimientoIngresosDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<MovimientoIngresosDetalladoDTO>) response.readEntity(new GenericType<List<MovimientoIngresosDetalladoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<MovimientoIngresosDetalladoDTO> getResult() {
		return result;
	}

 
  
  	public void setMovimientoIngresosDTO (MovimientoIngresosDTO movimientoIngresosDTO){
 		this.movimientoIngresosDTO=movimientoIngresosDTO;
 	}
 	
 	public MovimientoIngresosDTO getMovimientoIngresosDTO (){
 		return movimientoIngresosDTO;
 	}
  
}