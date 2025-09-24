package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.VerificarPersonasSinCondicionesDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/verificarPersonasSinCondiciones/aprobarResultados
 */
public class VerificarPersonasSinCondicionesAprobarResultados extends ServiceClient { 
    	private String numeroRadicado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private VerificarPersonasSinCondicionesDTO result;
  
 	public VerificarPersonasSinCondicionesAprobarResultados (String numeroRadicado){
 		super();
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(numeroRadicado == null ? null : Entity.json(numeroRadicado));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (VerificarPersonasSinCondicionesDTO) response.readEntity(VerificarPersonasSinCondicionesDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public VerificarPersonasSinCondicionesDTO getResult() {
		return result;
	}

 
  
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
}