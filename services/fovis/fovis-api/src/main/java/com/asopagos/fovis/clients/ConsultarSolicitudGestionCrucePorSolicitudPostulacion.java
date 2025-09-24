package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.SolicitudGestionCruceDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarSolicitudGestionCrucePorSolicitudPostulacion
 */
public class ConsultarSolicitudGestionCrucePorSolicitudPostulacion extends ServiceClient {
 
  
  	private Long idSolicitudPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudGestionCruceDTO> result;
  
 	public ConsultarSolicitudGestionCrucePorSolicitudPostulacion (Long idSolicitudPostulacion){
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
		this.result = (List<SolicitudGestionCruceDTO>) response.readEntity(new GenericType<List<SolicitudGestionCruceDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudGestionCruceDTO> getResult() {
		return result;
	}

 
  	public void setIdSolicitudPostulacion (Long idSolicitudPostulacion){
 		this.idSolicitudPostulacion=idSolicitudPostulacion;
 	}
 	
 	public Long getIdSolicitudPostulacion (){
 		return idSolicitudPostulacion;
 	}
  
}