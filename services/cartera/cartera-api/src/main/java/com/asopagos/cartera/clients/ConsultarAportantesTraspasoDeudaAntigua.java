package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.AportantesTraspasoDeudaDTO;
import com.asopagos.cartera.dto.FiltrosTrasladoDeudaAntiguaPersonaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarAportantesTraspasoDeudaAntigua
 */
public class ConsultarAportantesTraspasoDeudaAntigua extends ServiceClient { 
    	private FiltrosTrasladoDeudaAntiguaPersonaDTO filtros;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AportantesTraspasoDeudaDTO result;
  
 	public ConsultarAportantesTraspasoDeudaAntigua (FiltrosTrasladoDeudaAntiguaPersonaDTO filtros){
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
		result = (AportantesTraspasoDeudaDTO) response.readEntity(AportantesTraspasoDeudaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public AportantesTraspasoDeudaDTO getResult() {
		return result;
	}

 
  
  	public void setFiltros (FiltrosTrasladoDeudaAntiguaPersonaDTO filtros){
 		this.filtros=filtros;
 	}
 	
 	public FiltrosTrasladoDeudaAntiguaPersonaDTO getFiltros (){
 		return filtros;
 	}
  
}