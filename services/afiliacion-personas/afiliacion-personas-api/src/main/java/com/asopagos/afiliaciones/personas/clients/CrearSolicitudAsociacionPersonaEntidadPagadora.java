package com.asopagos.afiliaciones.personas.clients;

import java.lang.Long;
import com.asopagos.dto.AfiliadoInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/solicitudAsociacionPersona
 */
public class CrearSolicitudAsociacionPersonaEntidadPagadora extends ServiceClient { 
    	private AfiliadoInDTO inAfiliadoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearSolicitudAsociacionPersonaEntidadPagadora (AfiliadoInDTO inAfiliadoDTO){
 		super();
		this.inAfiliadoDTO=inAfiliadoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inAfiliadoDTO == null ? null : Entity.json(inAfiliadoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setInAfiliadoDTO (AfiliadoInDTO inAfiliadoDTO){
 		this.inAfiliadoDTO=inAfiliadoDTO;
 	}
 	
 	public AfiliadoInDTO getInAfiliadoDTO (){
 		return inAfiliadoDTO;
 	}
  
}