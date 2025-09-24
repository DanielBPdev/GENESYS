package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Boolean;
import com.asopagos.dto.modelo.ParametrizacionPreventivaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/consultarAportantesGestionPreventiva
 */
public class ConsultarAportantesGestionPreventiva extends ServiceClient { 
   	private Boolean automatico;
   	private ParametrizacionPreventivaModeloDTO parametrizacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SimulacionDTO> result;
  
 	public ConsultarAportantesGestionPreventiva (Boolean automatico,ParametrizacionPreventivaModeloDTO parametrizacionDTO){
 		super();
		this.automatico=automatico;
		this.parametrizacionDTO=parametrizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("automatico", automatico)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionDTO == null ? null : Entity.json(parametrizacionDTO));
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

 
  	public void setAutomatico (Boolean automatico){
 		this.automatico=automatico;
 	}
 	
 	public Boolean getAutomatico (){
 		return automatico;
 	}
  
  	public void setParametrizacionDTO (ParametrizacionPreventivaModeloDTO parametrizacionDTO){
 		this.parametrizacionDTO=parametrizacionDTO;
 	}
 	
 	public ParametrizacionPreventivaModeloDTO getParametrizacionDTO (){
 		return parametrizacionDTO;
 	}
  
}