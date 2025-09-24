package com.asopagos.afiliaciones.personas.web.composite.clients;

import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/iniciarVerificarInformacionSolicitud/{idEmpleador}
 */
public class IniciarVerificarInformacionSolicitud extends ServiceClient { 
  	private Long idEmpleador;
   	private Long idInstanciaProceso;
   	private AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public IniciarVerificarInformacionSolicitud (Long idEmpleador,Long idInstanciaProceso,AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO){
 		super();
		this.idEmpleador=idEmpleador;
		this.idInstanciaProceso=idInstanciaProceso;
		this.afiliarTrabajadorCandidatoDTO=afiliarTrabajadorCandidatoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.queryParam("idInstanciaProceso", idInstanciaProceso)
			.request(MediaType.APPLICATION_JSON)
			.post(afiliarTrabajadorCandidatoDTO == null ? null : Entity.json(afiliarTrabajadorCandidatoDTO));
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

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  	public void setIdInstanciaProceso (Long idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public Long getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  
  	public void setAfiliarTrabajadorCandidatoDTO (AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO){
 		this.afiliarTrabajadorCandidatoDTO=afiliarTrabajadorCandidatoDTO;
 	}
 	
 	public AfiliarTrabajadorCandidatoDTO getAfiliarTrabajadorCandidatoDTO (){
 		return afiliarTrabajadorCandidatoDTO;
 	}
  
}