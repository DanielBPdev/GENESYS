package com.asopagos.afiliaciones.clients;

import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/consultarUltimaAfiliacionPersona
 */
public class ConsultarUltimaAfiliacionPersona extends ServiceClient {
 
  
  	private Long idRolAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAfiliacionPersonaDTO result;
  
 	public ConsultarUltimaAfiliacionPersona (Long idRolAfiliado){
 		super();
		this.idRolAfiliado=idRolAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idRolAfiliado", idRolAfiliado)
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

 
  	public void setIdRolAfiliado (Long idRolAfiliado){
 		this.idRolAfiliado=idRolAfiliado;
 	}
 	
 	public Long getIdRolAfiliado (){
 		return idRolAfiliado;
 	}
  
}