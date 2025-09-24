package com.asopagos.solicitudes.clients;

import com.asopagos.enumeraciones.aportes.ActividadEnum;
import java.lang.Long;
import com.asopagos.dto.modelo.RegistroEstadoAporteModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudes/{idSolicitud}/consultarTrazabilidadPorActividad
 */
public class ConsultarTrazabilidadPorActividad extends ServiceClient {
 
  	private Long idSolicitud;
  
  	private ActividadEnum actividad;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RegistroEstadoAporteModeloDTO result;
  
 	public ConsultarTrazabilidadPorActividad (Long idSolicitud,ActividadEnum actividad){
 		super();
		this.idSolicitud=idSolicitud;
		this.actividad=actividad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitud", idSolicitud)
									.queryParam("actividad", actividad)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RegistroEstadoAporteModeloDTO) response.readEntity(RegistroEstadoAporteModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RegistroEstadoAporteModeloDTO getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setActividad (ActividadEnum actividad){
 		this.actividad=actividad;
 	}
 	
 	public ActividadEnum getActividad (){
 		return actividad;
 	}
  
}