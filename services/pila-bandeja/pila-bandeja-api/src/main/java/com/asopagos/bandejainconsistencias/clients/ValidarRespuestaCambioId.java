package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/validarRespuestaCambioId
 */
public class ValidarRespuestaCambioId extends ServiceClient {
 
  
  	private Long idError;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InconsistenciaDTO result;
  
 	public ValidarRespuestaCambioId (Long idError){
 		super();
		this.idError=idError;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idError", idError)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InconsistenciaDTO) response.readEntity(InconsistenciaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InconsistenciaDTO getResult() {
		return result;
	}

 
  	public void setIdError (Long idError){
 		this.idError=idError;
 	}
 	
 	public Long getIdError (){
 		return idError;
 	}
  
}