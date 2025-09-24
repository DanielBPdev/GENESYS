package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.cartera.dto.FiltrosParametrizacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarAportantesParametrizacionFiscalizacion
 */
public class ConsultarAportantesParametrizacionFiscalizacion extends ServiceClient { 
    	private FiltrosParametrizacionDTO filtrosParametrizacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SimulacionDTO> result;
  
 	public ConsultarAportantesParametrizacionFiscalizacion (FiltrosParametrizacionDTO filtrosParametrizacion){
 		super();
		this.filtrosParametrizacion=filtrosParametrizacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(filtrosParametrizacion == null ? null : Entity.json(filtrosParametrizacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SimulacionDTO>) response.readEntity(new GenericType<List<SimulacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<SimulacionDTO> getResult() {
		return result;
	}

 
  
  	public void setFiltrosParametrizacion (FiltrosParametrizacionDTO filtrosParametrizacion){
 		this.filtrosParametrizacion=filtrosParametrizacion;
 	}
 	
 	public FiltrosParametrizacionDTO getFiltrosParametrizacion (){
 		return filtrosParametrizacion;
 	}
  
}