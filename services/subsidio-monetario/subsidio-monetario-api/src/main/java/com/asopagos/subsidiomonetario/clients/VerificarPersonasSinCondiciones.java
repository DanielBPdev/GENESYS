package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.RespuestaVerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.dto.VerificarPersonasSinCondicionesDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/verificarPersonasSinCondiciones
 */
public class VerificarPersonasSinCondiciones extends ServiceClient { 
    	private VerificarPersonasSinCondicionesDTO verificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaVerificarPersonasSinCondicionesDTO result;
  
 	public VerificarPersonasSinCondiciones (VerificarPersonasSinCondicionesDTO verificacion){
 		super();
		this.verificacion=verificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(verificacion == null ? null : Entity.json(verificacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (RespuestaVerificarPersonasSinCondicionesDTO) response.readEntity(RespuestaVerificarPersonasSinCondicionesDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RespuestaVerificarPersonasSinCondicionesDTO getResult() {
		return result;
	}

 
  
  	public void setVerificacion (VerificarPersonasSinCondicionesDTO verificacion){
 		this.verificacion=verificacion;
 	}
 	
 	public VerificarPersonasSinCondicionesDTO getVerificacion (){
 		return verificacion;
 	}
  
}