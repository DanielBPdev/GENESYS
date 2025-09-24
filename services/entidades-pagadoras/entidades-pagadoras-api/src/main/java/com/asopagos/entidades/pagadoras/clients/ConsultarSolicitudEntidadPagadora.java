package com.asopagos.entidades.pagadoras.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudAsociacionPersonaEntidadPagadoraModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadesPagadoras/consultarSolicitudEntidadPagadora
 */
public class ConsultarSolicitudEntidadPagadora extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAsociacionPersonaEntidadPagadoraModeloDTO result;
  
 	public ConsultarSolicitudEntidadPagadora (Long idSolicitudGlobal){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudAsociacionPersonaEntidadPagadoraModeloDTO) response.readEntity(SolicitudAsociacionPersonaEntidadPagadoraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudAsociacionPersonaEntidadPagadoraModeloDTO getResult() {
		return result;
	}

 
  
}