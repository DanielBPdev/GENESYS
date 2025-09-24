package com.asopagos.afiliaciones.personas.web.clients;

import java.lang.Long;
import com.asopagos.dto.AfiliacionArchivoPlanoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWebMultiple/solicitarAfiliacionMasiva
 */
public class SolicitarAfiliacionMasiva extends ServiceClient { 
   	private Long idEmpleador;
   	private AfiliacionArchivoPlanoDTO candidatosAfiliacion;
  
  
 	public SolicitarAfiliacionMasiva (Long idEmpleador,AfiliacionArchivoPlanoDTO candidatosAfiliacion){
 		super();
		this.idEmpleador=idEmpleador;
		this.candidatosAfiliacion=candidatosAfiliacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(candidatosAfiliacion == null ? null : Entity.json(candidatosAfiliacion));
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
  
  	public void setCandidatosAfiliacion (AfiliacionArchivoPlanoDTO candidatosAfiliacion){
 		this.candidatosAfiliacion=candidatosAfiliacion;
 	}
 	
 	public AfiliacionArchivoPlanoDTO getCandidatosAfiliacion (){
 		return candidatosAfiliacion;
 	}
  
}