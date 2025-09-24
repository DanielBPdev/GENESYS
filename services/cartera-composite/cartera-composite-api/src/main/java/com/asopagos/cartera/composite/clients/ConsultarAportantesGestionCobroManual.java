package com.asopagos.cartera.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.cartera.FiltrosGestionCobroManualDTO;
import com.asopagos.dto.cartera.AportanteGestionManualDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/consultarAportantesGestionCobroManual
 */
public class ConsultarAportantesGestionCobroManual extends ServiceClient { 
    	private FiltrosGestionCobroManualDTO filtros;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AportanteGestionManualDTO> result;
  
 	public ConsultarAportantesGestionCobroManual (FiltrosGestionCobroManualDTO filtros){
 		super();
		this.filtros=filtros;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(filtros == null ? null : Entity.json(filtros));
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

 
  
  	public void setFiltros (FiltrosGestionCobroManualDTO filtros){
 		this.filtros=filtros;
 	}
 	
 	public FiltrosGestionCobroManualDTO getFiltros (){
 		return filtros;
 	}
  
}