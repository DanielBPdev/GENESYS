package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarSolicitudPostulacionById
 */
public class ConsultarSolicitudPostulacionById extends ServiceClient {
 
  
  	private Long idSolicitudPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPostulacionModeloDTO result;
  
 	public ConsultarSolicitudPostulacionById (Long idSolicitudPostulacion){
 		super();
		this.idSolicitudPostulacion=idSolicitudPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudPostulacion", idSolicitudPostulacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudPostulacionModeloDTO) response.readEntity(SolicitudPostulacionModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudPostulacionModeloDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitudPostulacion (Long idSolicitudPostulacion){
 		this.idSolicitudPostulacion=idSolicitudPostulacion;
 	}
 	
 	public Long getIdSolicitudPostulacion (){
 		return idSolicitudPostulacion;
 	}
  
}