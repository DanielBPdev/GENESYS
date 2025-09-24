package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.aportes.dto.SolicitanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/obtenerSolicitantesCuentaAportes
 */
public class ConsultarSolicitanteCorreccionCuentaAportes extends ServiceClient { 
    	private List<PersonaDTO> personas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitanteDTO> result;
  
 	public ConsultarSolicitanteCorreccionCuentaAportes (List<PersonaDTO> personas){
 		super();
		this.personas=personas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(personas == null ? null : Entity.json(personas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SolicitanteDTO>) response.readEntity(new GenericType<List<SolicitanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<SolicitanteDTO> getResult() {
		return result;
	}

 
  
  	public void setPersonas (List<PersonaDTO> personas){
 		this.personas=personas;
 	}
 	
 	public List<PersonaDTO> getPersonas (){
 		return personas;
 	}
  
}