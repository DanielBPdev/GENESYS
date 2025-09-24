package com.asopagos.afiliaciones.personas.web.composite.clients;

import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/finalizarAfiliacionTrabajadorCandidato/{idEmpleador}
 */
public class FinalizarAfiliacionTrabajadorCandidato extends ServiceClient { 
  	private Long idEmpleador;
    	private List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO;
  
  
 	public FinalizarAfiliacionTrabajadorCandidato (Long idEmpleador,List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO){
 		super();
		this.idEmpleador=idEmpleador;
		this.afiliarTrabajadorCandidatoDTO=afiliarTrabajadorCandidatoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(afiliarTrabajadorCandidatoDTO == null ? null : Entity.json(afiliarTrabajadorCandidatoDTO));
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
  
  
  	public void setAfiliarTrabajadorCandidatoDTO (List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO){
 		this.afiliarTrabajadorCandidatoDTO=afiliarTrabajadorCandidatoDTO;
 	}
 	
 	public List<AfiliarTrabajadorCandidatoDTO> getAfiliarTrabajadorCandidatoDTO (){
 		return afiliarTrabajadorCandidatoDTO;
 	}
  
}