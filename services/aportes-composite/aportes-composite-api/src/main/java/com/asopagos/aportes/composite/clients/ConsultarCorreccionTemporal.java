package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.aportes.composite.dto.CorreccionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aporteManual/{idSolicitud}/consultarCorreccionTemporal
 */
public class ConsultarCorreccionTemporal extends ServiceClient {
 
  	private Long idSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CorreccionDTO result;
  
 	public ConsultarCorreccionTemporal (Long idSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitud", idSolicitud)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CorreccionDTO) response.readEntity(CorreccionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CorreccionDTO getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
}