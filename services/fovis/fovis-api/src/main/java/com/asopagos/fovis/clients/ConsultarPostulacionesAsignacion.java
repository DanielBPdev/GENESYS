package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarPostulacionesAsignacion
 */
public class ConsultarPostulacionesAsignacion extends ServiceClient {
 
  
  	private Long idSolicitudGlobal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PostulacionFOVISModeloDTO> result;
  
 	public ConsultarPostulacionesAsignacion (Long idSolicitudGlobal){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudGlobal", idSolicitudGlobal)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PostulacionFOVISModeloDTO>) response.readEntity(new GenericType<List<PostulacionFOVISModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PostulacionFOVISModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
}