package com.asopagos.pila.composite.clients;

import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/enviarSolicitudCambioIden
 */
public class EnviarSolicitudCambioIden extends ServiceClient { 
   	private Long numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
   	private InconsistenciaDTO inconsistencias;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InconsistenciaDTO result;
  
 	public EnviarSolicitudCambioIden (Long numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,InconsistenciaDTO inconsistencias){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.inconsistencias=inconsistencias;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(inconsistencias == null ? null : Entity.json(inconsistencias));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (InconsistenciaDTO) response.readEntity(InconsistenciaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public InconsistenciaDTO getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacion (Long numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public Long getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
  	public void setInconsistencias (InconsistenciaDTO inconsistencias){
 		this.inconsistencias=inconsistencias;
 	}
 	
 	public InconsistenciaDTO getInconsistencias (){
 		return inconsistencias;
 	}
  
}