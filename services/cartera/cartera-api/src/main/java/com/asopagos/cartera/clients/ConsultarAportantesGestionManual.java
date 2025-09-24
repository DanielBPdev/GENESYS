package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.dto.cartera.ParametrosGestionCobroManualDTO;
import java.util.List;
import com.asopagos.dto.cartera.AportanteGestionManualDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarAportantesGestionManual
 */
public class ConsultarAportantesGestionManual extends ServiceClient { 
    	private ParametrosGestionCobroManualDTO parametros;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AportanteGestionManualDTO> result;
  
 	public ConsultarAportantesGestionManual (ParametrosGestionCobroManualDTO parametros){
 		super();
		this.parametros=parametros;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametros == null ? null : Entity.json(parametros));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<AportanteGestionManualDTO>) response.readEntity(new GenericType<List<AportanteGestionManualDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<AportanteGestionManualDTO> getResult() {
		return result;
	}

 
  
  	public void setParametros (ParametrosGestionCobroManualDTO parametros){
 		this.parametros=parametros;
 	}
 	
 	public ParametrosGestionCobroManualDTO getParametros (){
 		return parametros;
 	}
  
}