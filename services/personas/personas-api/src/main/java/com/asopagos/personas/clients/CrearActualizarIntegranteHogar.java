package com.asopagos.personas.clients;

import com.asopagos.dto.modelo.IntegranteHogarModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/crearActualizarIntegranteHogar
 */
public class CrearActualizarIntegranteHogar extends ServiceClient { 
    	private IntegranteHogarModeloDTO integranteHogarModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private IntegranteHogarModeloDTO result;
  
 	public CrearActualizarIntegranteHogar (IntegranteHogarModeloDTO integranteHogarModeloDTO){
 		super();
		this.integranteHogarModeloDTO=integranteHogarModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(integranteHogarModeloDTO == null ? null : Entity.json(integranteHogarModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (IntegranteHogarModeloDTO) response.readEntity(IntegranteHogarModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public IntegranteHogarModeloDTO getResult() {
		return result;
	}

 
  
  	public void setIntegranteHogarModeloDTO (IntegranteHogarModeloDTO integranteHogarModeloDTO){
 		this.integranteHogarModeloDTO=integranteHogarModeloDTO;
 	}
 	
 	public IntegranteHogarModeloDTO getIntegranteHogarModeloDTO (){
 		return integranteHogarModeloDTO;
 	}
  
}