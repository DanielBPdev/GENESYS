package com.asopagos.fovis.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.ActaAsignacionFOVISModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarActaAsignacionPorIdSolicitudAsignacion
 */
public class ConsultarActaAsignacionPorIdSolicitudAsignacion extends ServiceClient {
 
  
  	private Long idSolicitudAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ActaAsignacionFOVISModeloDTO result;
  
 	public ConsultarActaAsignacionPorIdSolicitudAsignacion (Long idSolicitudAsignacion){
 		super();
		this.idSolicitudAsignacion=idSolicitudAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudAsignacion", idSolicitudAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ActaAsignacionFOVISModeloDTO) response.readEntity(ActaAsignacionFOVISModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ActaAsignacionFOVISModeloDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitudAsignacion (Long idSolicitudAsignacion){
 		this.idSolicitudAsignacion=idSolicitudAsignacion;
 	}
 	
 	public Long getIdSolicitudAsignacion (){
 		return idSolicitudAsignacion;
 	}
  
}