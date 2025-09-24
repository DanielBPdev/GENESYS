package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.aportes.dto.ConsultarCotizanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarCotizante
 */
public class ConsultarCotizante extends ServiceClient { 
    	private ConsultarCotizanteDTO consultarCotizante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CotizanteDTO> result;
  
 	public ConsultarCotizante (ConsultarCotizanteDTO consultarCotizante){
 		super();
		this.consultarCotizante=consultarCotizante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consultarCotizante == null ? null : Entity.json(consultarCotizante));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<CotizanteDTO>) response.readEntity(new GenericType<List<CotizanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CotizanteDTO> getResult() {
		return result;
	}

 
  
  	public void setConsultarCotizante (ConsultarCotizanteDTO consultarCotizante){
 		this.consultarCotizante=consultarCotizante;
 	}
 	
 	public ConsultarCotizanteDTO getConsultarCotizante (){
 		return consultarCotizante;
 	}
  
}