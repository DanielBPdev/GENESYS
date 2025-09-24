package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/{parametro}/consultarAportantesGestionCobro
 */
public class ConsultarAportantesGestionCobro extends ServiceClient { 
  	private Boolean parametro;
    	private ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SimulacionDTO> result;
  
 	public ConsultarAportantesGestionCobro (Boolean parametro,ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion){
 		super();
		this.parametro=parametro;
		this.parametrizacion=parametrizacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("parametro", parametro)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacion == null ? null : Entity.json(parametrizacion));
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

 	public void setParametro (Boolean parametro){
 		this.parametro=parametro;
 	}
 	
 	public Boolean getParametro (){
 		return parametro;
 	}
  
  
  	public void setParametrizacion (ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion){
 		this.parametrizacion=parametrizacion;
 	}
 	
 	public ParametrizacionCriteriosGestionCobroModeloDTO getParametrizacion (){
 		return parametrizacion;
 	}
  
}