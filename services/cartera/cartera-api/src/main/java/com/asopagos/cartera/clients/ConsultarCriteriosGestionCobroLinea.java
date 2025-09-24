package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarCriteriosGestionCobroLinea
 */
public class ConsultarCriteriosGestionCobroLinea extends ServiceClient { 
    	private List<TipoLineaCobroEnum> lineasCobro;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ParametrizacionCriteriosGestionCobroModeloDTO> result;
  
 	public ConsultarCriteriosGestionCobroLinea (List<TipoLineaCobroEnum> lineasCobro){
 		super();
		this.lineasCobro=lineasCobro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(lineasCobro == null ? null : Entity.json(lineasCobro));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ParametrizacionCriteriosGestionCobroModeloDTO>) response.readEntity(new GenericType<List<ParametrizacionCriteriosGestionCobroModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ParametrizacionCriteriosGestionCobroModeloDTO> getResult() {
		return result;
	}

 
  
  	public void setLineasCobro (List<TipoLineaCobroEnum> lineasCobro){
 		this.lineasCobro=lineasCobro;
 	}
 	
 	public List<TipoLineaCobroEnum> getLineasCobro (){
 		return lineasCobro;
 	}
  
}