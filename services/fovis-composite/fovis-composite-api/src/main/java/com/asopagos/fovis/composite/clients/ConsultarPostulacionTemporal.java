package com.asopagos.fovis.composite.clients;

import java.lang.Long;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisComposite/consultarPostulacionTemporal/{idSolicitudGlobal}
 */
public class ConsultarPostulacionTemporal extends ServiceClient {
 
  	private Long idSolicitudGlobal;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPostulacionFOVISDTO result;
  
 	public ConsultarPostulacionTemporal (Long idSolicitudGlobal){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudGlobal", idSolicitudGlobal)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudPostulacionFOVISDTO) response.readEntity(SolicitudPostulacionFOVISDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudPostulacionFOVISDTO getResult() {
		return result;
	}

 	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  
}