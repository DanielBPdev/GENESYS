package com.asopagos.afiliaciones.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.IntentoAfiliacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/consultarIntentoAfiliacion
 */
public class ConsultarIntentoAfiliacion extends ServiceClient {
 
  
  	private Long idSolicitud;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private IntentoAfiliacionDTO result;
  
 	public ConsultarIntentoAfiliacion (Long idSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitud", idSolicitud)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (IntentoAfiliacionDTO) response.readEntity(IntentoAfiliacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public IntentoAfiliacionDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
}