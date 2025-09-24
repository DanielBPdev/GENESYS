package com.asopagos.afiliaciones.personas.web.clients;

import java.lang.Long;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.AfiliacionPersonaWebMasivaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWebMultiple/procesarAfiliacionPersonasWebMasiva
 */
public class ProcesarAfiliacionPersonasWebMasiva extends ServiceClient { 
   	private Long idEmpleador;
   	private AfiliacionPersonaWebMasivaDTO afiliacionPersonaWeb;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AfiliadoInDTO result;
  
 	public ProcesarAfiliacionPersonasWebMasiva (Long idEmpleador,AfiliacionPersonaWebMasivaDTO afiliacionPersonaWeb){
 		super();
		this.idEmpleador=idEmpleador;
		this.afiliacionPersonaWeb=afiliacionPersonaWeb;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(afiliacionPersonaWeb == null ? null : Entity.json(afiliacionPersonaWeb));
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
  
  	public void setAfiliacionPersonaWeb (AfiliacionPersonaWebMasivaDTO afiliacionPersonaWeb){
 		this.afiliacionPersonaWeb=afiliacionPersonaWeb;
 	}
 	
 	public AfiliacionPersonaWebMasivaDTO getAfiliacionPersonaWeb (){
 		return afiliacionPersonaWeb;
 	}
  
}