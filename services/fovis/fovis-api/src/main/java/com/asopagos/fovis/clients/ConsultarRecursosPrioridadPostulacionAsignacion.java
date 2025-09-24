package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarRecursosPrioridadPostulacionAsignacion
 */
public class ConsultarRecursosPrioridadPostulacionAsignacion extends ServiceClient {
 
  
  	private Long idPostulacion;
  	private Long idSolicitudAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ConsultarRecursosPrioridadPostulacionAsignacion (Long idPostulacion, Long idSolicitudAsignacion){
 		super();
		this.idPostulacion=idPostulacion;
		this.idSolicitudAsignacion=idSolicitudAsignacion;

 	}
 
  	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.queryParam("idPostulacion", idPostulacion)
						.queryParam("idSolicitudAsignacion", idSolicitudAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
  	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}

 
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion(){
 		return idPostulacion;
 	}
 	
 	public void setIdSolicitudAsignacion (Long idSolicitudAsignacion){
 		this.idSolicitudAsignacion=idSolicitudAsignacion;
 	}
 	
 	public Long getIdSolicitudAsignacion(){
 		return idSolicitudAsignacion;
 	}

  
}