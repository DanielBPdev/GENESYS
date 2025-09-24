package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.RegistroRemisionAportantesDTO;
import com.asopagos.dto.modelo.SolicitudGestionCobroFisicoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/guardarResultadosEdictos
 */
public class GuardarResultadosEdictos extends ServiceClient { 
    	private RegistroRemisionAportantesDTO registroDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudGestionCobroFisicoModeloDTO result;
  
 	public GuardarResultadosEdictos (RegistroRemisionAportantesDTO registroDTO){
 		super();
		this.registroDTO=registroDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(registroDTO == null ? null : Entity.json(registroDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudGestionCobroFisicoModeloDTO) response.readEntity(SolicitudGestionCobroFisicoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudGestionCobroFisicoModeloDTO getResult() {
		return result;
	}

 
  
  	public void setRegistroDTO (RegistroRemisionAportantesDTO registroDTO){
 		this.registroDTO=registroDTO;
 	}
 	
 	public RegistroRemisionAportantesDTO getRegistroDTO (){
 		return registroDTO;
 	}
  
}