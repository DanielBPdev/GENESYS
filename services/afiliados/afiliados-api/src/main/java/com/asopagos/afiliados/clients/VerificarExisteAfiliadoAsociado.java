package com.asopagos.afiliados.clients;

import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.dto.PersonaEmpresaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/verificarExisteAfiliadoAsociado
 */
public class VerificarExisteAfiliadoAsociado extends ServiceClient { 
    	private PersonaEmpresaDTO personaEmpresaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RolAfiliado result;
  
 	public VerificarExisteAfiliadoAsociado (PersonaEmpresaDTO personaEmpresaDTO){
 		super();
		this.personaEmpresaDTO=personaEmpresaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(personaEmpresaDTO == null ? null : Entity.json(personaEmpresaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (RolAfiliado) response.readEntity(RolAfiliado.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RolAfiliado getResult() {
		return result;
	}

 
  
  	public void setPersonaEmpresaDTO (PersonaEmpresaDTO personaEmpresaDTO){
 		this.personaEmpresaDTO=personaEmpresaDTO;
 	}
 	
 	public PersonaEmpresaDTO getPersonaEmpresaDTO (){
 		return personaEmpresaDTO;
 	}
  
}