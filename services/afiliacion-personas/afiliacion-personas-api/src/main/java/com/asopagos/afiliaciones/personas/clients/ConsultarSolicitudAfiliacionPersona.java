package com.asopagos.afiliaciones.personas.clients;

import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/solicitudAfiliacionPersona/{idSolicitudGlobal}
 */
public class ConsultarSolicitudAfiliacionPersona extends ServiceClient {
 
  	private Long idSolicitudGlobal;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAfiliacionPersonaDTO result;
  
 	public ConsultarSolicitudAfiliacionPersona (Long idSolicitudGlobal){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudGlobal", idSolicitudGlobal)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudAfiliacionPersonaDTO) response.readEntity(SolicitudAfiliacionPersonaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudAfiliacionPersonaDTO getResult() {
		return result;
	}

 	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  
}