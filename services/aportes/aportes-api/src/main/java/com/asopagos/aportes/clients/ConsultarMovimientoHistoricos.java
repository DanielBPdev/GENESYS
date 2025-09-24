package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.ConsultaMovimientoIngresosDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarMovimientoHistoricos
 */
public class ConsultarMovimientoHistoricos extends ServiceClient { 
    	private ConsultaMovimientoIngresosDTO consultaMovimientosIngresos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<MovimientoIngresosDTO> result;
  
 	public ConsultarMovimientoHistoricos (ConsultaMovimientoIngresosDTO consultaMovimientosIngresos){
 		super();
		this.consultaMovimientosIngresos=consultaMovimientosIngresos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consultaMovimientosIngresos == null ? null : Entity.json(consultaMovimientosIngresos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<MovimientoIngresosDTO>) response.readEntity(new GenericType<List<MovimientoIngresosDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<MovimientoIngresosDTO> getResult() {
		return result;
	}

 
  
  	public void setConsultaMovimientosIngresos (ConsultaMovimientoIngresosDTO consultaMovimientosIngresos){
 		this.consultaMovimientosIngresos=consultaMovimientosIngresos;
 	}
 	
 	public ConsultaMovimientoIngresosDTO getConsultaMovimientosIngresos (){
 		return consultaMovimientosIngresos;
 	}
  
}