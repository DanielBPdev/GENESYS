package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.ParametrizacionFiscalizacionModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/consultarAportantesFiscalizacion
 */
public class ConsultarAportantesFiscalizacion extends ServiceClient { 
    	private ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SimulacionDTO> result;
  
 	public ConsultarAportantesFiscalizacion (ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO){
 		super();
		this.parametrizacionFiscalizacionModeloDTO=parametrizacionFiscalizacionModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionFiscalizacionModeloDTO == null ? null : Entity.json(parametrizacionFiscalizacionModeloDTO));
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

 
  
  	public void setParametrizacionFiscalizacionModeloDTO (ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO){
 		this.parametrizacionFiscalizacionModeloDTO=parametrizacionFiscalizacionModeloDTO;
 	}
 	
 	public ParametrizacionFiscalizacionModeloDTO getParametrizacionFiscalizacionModeloDTO (){
 		return parametrizacionFiscalizacionModeloDTO;
 	}
  
}