package com.asopagos.fovis.clients;

import com.asopagos.enumeraciones.fovis.ResultadoAsignacionEnum;
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
 * /rest/fovis/consultarPostulacionesSolicitudPorResultado
 */
public class ConsultarPostulacionesSolicitudPorResultado extends ServiceClient {
 
  
  	private Long idSolicitudAsignacion;
  	private ResultadoAsignacionEnum resultadoAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PostulacionFOVISModeloDTO> result;
  
 	public ConsultarPostulacionesSolicitudPorResultado (Long idSolicitudAsignacion,ResultadoAsignacionEnum resultadoAsignacion){
 		super();
		this.idSolicitudAsignacion=idSolicitudAsignacion;
		this.resultadoAsignacion=resultadoAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudAsignacion", idSolicitudAsignacion)
						.queryParam("resultadoAsignacion", resultadoAsignacion)
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

 
  	public void setIdSolicitudAsignacion (Long idSolicitudAsignacion){
 		this.idSolicitudAsignacion=idSolicitudAsignacion;
 	}
 	
 	public Long getIdSolicitudAsignacion (){
 		return idSolicitudAsignacion;
 	}
  	public void setResultadoAsignacion (ResultadoAsignacionEnum resultadoAsignacion){
 		this.resultadoAsignacion=resultadoAsignacion;
 	}
 	
 	public ResultadoAsignacionEnum getResultadoAsignacion (){
 		return resultadoAsignacion;
 	}
  
}