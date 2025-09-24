package com.asopagos.pila.clients;

import com.asopagos.pila.dto.ResultadoSimulacionAporteDetalladoDTO;
import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/consultarDatosFase2SimuladaCorreccion
 */
public class ConsultarDatosFase2SimuladaCorreccion extends ServiceClient { 
    	private RegistrarCorreccionAdicionDTO criteriosSimulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoSimulacionAporteDetalladoDTO result;
  
 	public ConsultarDatosFase2SimuladaCorreccion (RegistrarCorreccionAdicionDTO criteriosSimulacion){
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
		result = (ResultadoSimulacionAporteDetalladoDTO) response.readEntity(ResultadoSimulacionAporteDetalladoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoSimulacionAporteDetalladoDTO getResult() {
		return result;
	}

 
  
  	public void setCriteriosSimulacion (RegistrarCorreccionAdicionDTO criteriosSimulacion){
 		this.criteriosSimulacion=criteriosSimulacion;
 	}
 	
 	public RegistrarCorreccionAdicionDTO getCriteriosSimulacion (){
 		return criteriosSimulacion;
 	}
  
}