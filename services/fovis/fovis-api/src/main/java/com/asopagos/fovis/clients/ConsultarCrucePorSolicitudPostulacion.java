package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.CruceDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarCrucePorSolicitudPostulacion
 */
public class ConsultarCrucePorSolicitudPostulacion extends ServiceClient {
 
  
  	private Long idSolicitudPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CruceDTO> result;
  
 	public ConsultarCrucePorSolicitudPostulacion (Long idSolicitudPostulacion){
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
		this.result = (List<CruceDTO>) response.readEntity(new GenericType<List<CruceDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CruceDTO> getResult() {
		return result;
	}

 
  	public void setIdSolicitudPostulacion (Long idSolicitudPostulacion){
 		this.idSolicitudPostulacion=idSolicitudPostulacion;
 	}
 	
 	public Long getIdSolicitudPostulacion (){
 		return idSolicitudPostulacion;
 	}
  
}