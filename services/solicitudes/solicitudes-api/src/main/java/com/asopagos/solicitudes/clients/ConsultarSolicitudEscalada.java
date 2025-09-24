package com.asopagos.solicitudes.clients;

import com.asopagos.dto.EscalamientoSolicitudDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudes/{idSolicitud}/escalamientoSolicitud
 */
public class ConsultarSolicitudEscalada extends ServiceClient {
 
  	private Long idSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EscalamientoSolicitudDTO result;
  
 	public ConsultarSolicitudEscalada (Long idSolicitud){
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
		this.result = (EscalamientoSolicitudDTO) response.readEntity(EscalamientoSolicitudDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EscalamientoSolicitudDTO getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
}