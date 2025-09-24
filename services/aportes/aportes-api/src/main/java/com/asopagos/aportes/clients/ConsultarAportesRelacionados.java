package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.ConsultaAporteRelacionadoDTO;
import com.asopagos.aportes.dto.MovimientoIngresosDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarAportesRelacionados
 */
public class ConsultarAportesRelacionados extends ServiceClient { 
    	private ConsultaAporteRelacionadoDTO consultaAportesRelacionados;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<MovimientoIngresosDTO> result;
  
 	public ConsultarAportesRelacionados (ConsultaAporteRelacionadoDTO consultaAportesRelacionados){
 		super();
		this.consultaAportesRelacionados=consultaAportesRelacionados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consultaAportesRelacionados == null ? null : Entity.json(consultaAportesRelacionados));
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

 
  
  	public void setConsultaAportesRelacionados (ConsultaAporteRelacionadoDTO consultaAportesRelacionados){
 		this.consultaAportesRelacionados=consultaAportesRelacionados;
 	}
 	
 	public ConsultaAporteRelacionadoDTO getConsultaAportesRelacionados (){
 		return consultaAportesRelacionados;
 	}
  
}