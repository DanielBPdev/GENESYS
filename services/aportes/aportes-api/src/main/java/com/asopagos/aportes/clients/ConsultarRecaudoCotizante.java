package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import java.util.List;
import com.asopagos.dto.AnalisisDevolucionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarRecaudoCotizante
 */
public class ConsultarRecaudoCotizante extends ServiceClient { 
    	private ConsultarRecaudoDTO consultaRecaudo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AnalisisDevolucionDTO> result;
  
 	public ConsultarRecaudoCotizante (ConsultarRecaudoDTO consultaRecaudo){
 		super();
		this.consultaRecaudo=consultaRecaudo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consultaRecaudo == null ? null : Entity.json(consultaRecaudo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<AnalisisDevolucionDTO>) response.readEntity(new GenericType<List<AnalisisDevolucionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<AnalisisDevolucionDTO> getResult() {
		return result;
	}

 
  
  	public void setConsultaRecaudo (ConsultarRecaudoDTO consultaRecaudo){
 		this.consultaRecaudo=consultaRecaudo;
 	}
 	
 	public ConsultarRecaudoDTO getConsultaRecaudo (){
 		return consultaRecaudo;
 	}
  
}