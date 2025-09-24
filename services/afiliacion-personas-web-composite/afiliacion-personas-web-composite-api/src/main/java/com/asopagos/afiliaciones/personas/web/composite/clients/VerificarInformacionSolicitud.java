package com.asopagos.afiliaciones.personas.web.composite.clients;

import java.lang.Long;
import com.asopagos.dto.VerificarSolicitudAfiliacionPersonaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/verificarInformacionSolicutud/{idEmpleador}
 */
public class VerificarInformacionSolicitud extends ServiceClient { 
  	private Long idEmpleador;
    	private VerificarSolicitudAfiliacionPersonaDTO inDTO;
  
  
 	public VerificarInformacionSolicitud (Long idEmpleador,VerificarSolicitudAfiliacionPersonaDTO inDTO){
 		super();
		this.idEmpleador=idEmpleador;
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  
  	public void setInDTO (VerificarSolicitudAfiliacionPersonaDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public VerificarSolicitudAfiliacionPersonaDTO getInDTO (){
 		return inDTO;
 	}
  
}