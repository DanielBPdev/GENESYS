package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.Map;
import com.asopagos.subsidiomonetario.pagos.dto.SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/solicitarRetiroPersonaAutorizada/confirmarValorEntregado
 */
public class SolicitarRetiroPersonaAutorizadaConfirmarValorEntregado extends ServiceClient { 
    	private SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO confirmacionEntregaPersonaAutorizadaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public SolicitarRetiroPersonaAutorizadaConfirmarValorEntregado (SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO confirmacionEntregaPersonaAutorizadaDTO){
 		super();
		this.confirmacionEntregaPersonaAutorizadaDTO=confirmacionEntregaPersonaAutorizadaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(confirmacionEntregaPersonaAutorizadaDTO == null ? null : Entity.json(confirmacionEntregaPersonaAutorizadaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,String> getResult() {
		return result;
	}

 
  
  	public void setConfirmacionEntregaPersonaAutorizadaDTO (SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO confirmacionEntregaPersonaAutorizadaDTO){
 		this.confirmacionEntregaPersonaAutorizadaDTO=confirmacionEntregaPersonaAutorizadaDTO;
 	}
 	
 	public SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO getConfirmacionEntregaPersonaAutorizadaDTO (){
 		return confirmacionEntregaPersonaAutorizadaDTO;
 	}
  
}