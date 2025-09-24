package com.asopagos.pila.composite.clients;

import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import com.asopagos.pila.dto.ResultadoAprobacionCorreccionAporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/registrarAporteSimuladoCorreccion
 */
public class RegistrarAporteSimuladoCorreccion extends ServiceClient { 
    	private RegistrarCorreccionAdicionDTO criteriosSimulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoAprobacionCorreccionAporteDTO result;
  
 	public RegistrarAporteSimuladoCorreccion (RegistrarCorreccionAdicionDTO criteriosSimulacion){
 		super();
		this.criteriosSimulacion=criteriosSimulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(criteriosSimulacion == null ? null : Entity.json(criteriosSimulacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoAprobacionCorreccionAporteDTO) response.readEntity(ResultadoAprobacionCorreccionAporteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoAprobacionCorreccionAporteDTO getResult() {
		return result;
	}

 
  
  	public void setCriteriosSimulacion (RegistrarCorreccionAdicionDTO criteriosSimulacion){
 		this.criteriosSimulacion=criteriosSimulacion;
 	}
 	
 	public RegistrarCorreccionAdicionDTO getCriteriosSimulacion (){
 		return criteriosSimulacion;
 	}
  
}