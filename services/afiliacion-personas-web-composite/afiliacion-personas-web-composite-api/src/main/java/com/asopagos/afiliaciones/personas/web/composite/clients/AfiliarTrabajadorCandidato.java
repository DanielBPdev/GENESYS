package com.asopagos.afiliaciones.personas.web.composite.clients;

import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import java.lang.Long;
import com.asopagos.dto.AfiliadoInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/afiliarTrabajadorCandidato/{idEmpleador}
 */
public class AfiliarTrabajadorCandidato extends ServiceClient { 
  	private Long idEmpleador;
    	private AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AfiliadoInDTO result;
  
 	public AfiliarTrabajadorCandidato (Long idEmpleador,AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO){
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
		result = (AfiliadoInDTO) response.readEntity(AfiliadoInDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public AfiliadoInDTO getResult() {
		return result;
	}

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  
  	public void setAfiliarTrabajadorCandidatoDTO (AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO){
 		this.afiliarTrabajadorCandidatoDTO=afiliarTrabajadorCandidatoDTO;
 	}
 	
 	public AfiliarTrabajadorCandidatoDTO getAfiliarTrabajadorCandidatoDTO (){
 		return afiliarTrabajadorCandidatoDTO;
 	}
  
}