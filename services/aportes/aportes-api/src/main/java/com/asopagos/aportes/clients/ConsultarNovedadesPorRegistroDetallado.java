package com.asopagos.aportes.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.ResultadoConsultaNovedadesExistentesDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarNovedadesPorRegistroDetallado
 */
public class ConsultarNovedadesPorRegistroDetallado extends ServiceClient { 
    	private List<Long> registrosDetallados;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoConsultaNovedadesExistentesDTO result;
  
 	public ConsultarNovedadesPorRegistroDetallado (List<Long> registrosDetallados){
 		super();
		this.registrosDetallados=registrosDetallados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registrosDetallados == null ? null : Entity.json(registrosDetallados));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoConsultaNovedadesExistentesDTO) response.readEntity(ResultadoConsultaNovedadesExistentesDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoConsultaNovedadesExistentesDTO getResult() {
		return result;
	}

 
  
  	public void setRegistrosDetallados (List<Long> registrosDetallados){
 		this.registrosDetallados=registrosDetallados;
 	}
 	
 	public List<Long> getRegistrosDetallados (){
 		return registrosDetallados;
 	}
  
}