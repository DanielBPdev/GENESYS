package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.PostulacionAsignacionDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarListaPostulacionAsignacion
 */
public class ConsultarListaPostulacionAsignacion extends ServiceClient {
 
  
  	private Long idSolicitudAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PostulacionAsignacionDTO> result;
  
 	public ConsultarListaPostulacionAsignacion (Long idSolicitudAsignacion){
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
		this.result = (List<PostulacionAsignacionDTO>) response.readEntity(new GenericType<List<PostulacionAsignacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PostulacionAsignacionDTO> getResult() {
		return result;
	}

 
  	public void setIdSolicitudAsignacion (Long idSolicitudAsignacion){
 		this.idSolicitudAsignacion=idSolicitudAsignacion;
 	}
 	
 	public Long getIdSolicitudAsignacion (){
 		return idSolicitudAsignacion;
 	}
  
}